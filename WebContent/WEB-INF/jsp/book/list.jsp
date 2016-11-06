<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.wadeyuan.training.entity.Book" %>
<%@ page import="com.wadeyuan.training.entity.User" %>
<%@ page import="com.wadeyuan.training.common.Constants" %>
<%@ page import="com.wadeyuan.training.util.Pagination" %>

<%User user = (User) session.getAttribute(Constants.USER); %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="../common/init-head.jsp" %>
    <title>我的图书</title>

    <!-- Custom styles for this template -->
    <link href="<%=contextPath%>/static/css/main.css" rel="stylesheet">
    <script>
    $(document).ready(function(){
        /* register the "hidden.bs.modal" event on modal to unbind event for delete button */
        $('#deleteConfirmation').on('hidden.bs.modal', function () {
            console.log("Delete confirmation hidden");
            $('#deleteConfirmation').find("button.btn-delete").off("click");
        });
        $("table.table-book-list").find("a[href='#']").filter(function(){
            return $(this).text() == "删除";
        }).on("click", function(e) {
            var bookId = $(this).data("book-id");
            $("#deleteConfirmation").modal("show").find("button.btn-delete").on("click", function(e){
                console.log("Deleting book: " + bookId);
                window.location.href="<%=contextPath%>/book/delete/" + bookId;
                $("#deleteConfirmation").modal("hide");
            });
        });
    });
    
    /**
     * type: 'success', 'danger', 'warning'
     * message: tip message to show
     */
    function showTipMessage(type, message) {
        $("#tipMessage").removeClass("hidden").addClass("alert-" + type).addClass("in").text(message);
    }
    
    /**
     * timeout: hide message after {timeout} milliseconds
     */
    function hideTipMessage() {
        $("#tipMessage").removeClass("in").removeClass("alert-success").removeClass("alert-danger").removeClass("alert-warning").addClass("hidden").text("");
    }
    
    // show tip message, then fade out in {timeout} milliseconds
    function showAndFadeOutTipMessage(type, message, timeout) {
        showTipMessage(type, message);
        setTimeout(hideTipMessage, timeout);
    }
    </script>
  </head>

  <body>
    <%@ include file="../common/navigation.jsp" %>
    <ol class="breadcrumb">
      <li><a href="#">我的图书</a></li>
      <li class="active">全部</li>
    </ol>
    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar user-profile">
          <div class="user-avatar">
            <div class="default-avatar glyphicon glyphicon-user"></div>
            <div class="user-name"><%=user.getDisplayName() %></div>
          </div>
          <div class="basic-info">
            <div class="col-sm-4"><i class="glyphicon glyphicon-home"></i></div>
            <div class="col-sm-6 text-label">基本资料</div>
            <div class="col-sm-2"><i class="glyphicon glyphicon-edit"></i></div>
            <div class="col-sm-12"><!-- Blank line --></div>
            <!-- Department -->
            <div class="col-sm-4 text-label">部门：</div>
            <div class="col-sm-8 value"><%=user.getDepartment() %></div>
            <!-- Phone -->
            <div class="col-sm-4 text-label">电话：</div>
            <div class="col-sm-8 value"><%=user.getPhone() %></div>
            <!-- Email -->
            <div class="col-sm-4 text-label">邮箱：</div>
            <div class="col-sm-8 value"><%=user.getEmail() %></div>
            <!-- Address -->
            <div class="col-sm-4 text-label">地址：</div>
            <div class="col-sm-8 value"><%=user.getAddress() %></div>
          </div>
          <div class="user-message">
            <!-- <ul>
              <li><i class="glyphicon glyphicon-bell"></i> 我的消息</li>
              <li><i class="glyphicon glyphicon-file"></i> 借书请求</li>
            </ul> -->
            <!-- User messages -->
            <div class="col-sm-4 text-label"><i class="glyphicon glyphicon-bell"></i></div>
            <div class="col-sm-8 value">我的消息 <span class="badge">3</span></div>
            <!-- Borrow Application -->
            <div class="col-sm-4 text-label"><i class="glyphicon glyphicon-file"></i></div>
            <div class="col-sm-8 value">借书请求 <span class="badge">5</span></div>
          </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <ul class="nav nav-pills" role="tablist">
            <%String status = (String)request.getAttribute(Constants.STATUS); %>
            <li role="presentation" <%=status.equals("all")?"class=\"active\"":"" %>><a href="<%=contextPath%>/book/list?status=all">全部 <span class="badge"><%=request.getAttribute(Constants.COUNT_ALL) %></span></a></li>
            <li role="presentation" <%=status.equals("out")?"class=\"active\"":"" %>><a href="<%=contextPath%>/book/list?status=out">已借出 <span class="badge"><%=request.getAttribute(Constants.COUNT_OUT) %></span></a></li>
            <li role="presentation" <%=status.equals("remaining")?"class=\"active\"":"" %>><a href="<%=contextPath%>/book/list?status=remaining">未借出 <span class="badge"><%=request.getAttribute(Constants.COUNT_REMAINING) %></span></a></li>
            <li role="presentation" <%=status.equals("in")?"class=\"active\"":"" %>><a href="<%=contextPath%>/book/list?status=in">借入 <span class="badge"><%=request.getAttribute(Constants.COUNT_IN) %></span></a></li>
            <li class="pull-right"><a class="glyphicon glyphicon-plus" href="<%=contextPath %>/book/add"> 新增图书</a></li>
          </ul>
          <div id="tipMessage" class="alert fade hidden" role="alert">
          </div>
          <%if(session.getAttribute(Constants.SUCCESS_MESSAGE) != null) {%>
          <script>
              showAndFadeOutTipMessage("success", "<%=session.getAttribute(Constants.SUCCESS_MESSAGE) %>", 1500);
          </script>
          <%    session.removeAttribute(Constants.SUCCESS_MESSAGE); %>
          <%} %>
          <%if(session.getAttribute(Constants.ERROR_MESSAGE) != null) {%>
          <script>
              showAndFadeOutTipMessage("danger", "<%=session.getAttribute(Constants.ERROR_MESSAGE) %>", 1500);
          </script>
          <%    session.removeAttribute(Constants.ERROR_MESSAGE); %>
          <%} %>
          <div class="table-responsive">
            <table class="table table-striped table-book-list">
              <thead>
                <tr>
                  <th>编号</th>
                  <th>书籍</th>
                  <th>状态</th>
                  <th>操作</th>
                  <th>借阅历史</th>
                </tr>
              </thead>
              <tbody>
                <%List<Book> books = (List<Book>)request.getAttribute(Constants.BOOK_LIST); %>
                <%if(books.size() < 1) {%>
                  <tr><td colspan="5">No results found.</td></tr>
                <%} else {%>
                <%    for(Book book : books) {%>
                        <tr>
                          <td><%=book.getId() %></td>
                          <td>
                            <%=book.getName()%>
                            <br>
                            <label class="author"><%=book.getAuthor() %></label>
                          </td>
                          <%String bookStatus = "未借出"; %>
                          <%if(book.getOwnerId() != book.getCurrentUserId()) {%>
                          <%  if(book.getOwnerId() == user.getId()) {%>
                          <%    bookStatus = "借给[" + book.getCurrentUserName() + "]"; %>
                          <%  } else {%>
                          <%    bookStatus = "从[" + book.getOwnerName() + "]借入"; %>
                          <%  } %>
                          <%} %>
                          <td><%=bookStatus %></td>
                          <td>
                            <%if(book.getOwnerId() == user.getId()) {%>
                            <a href="<%=contextPath%>/book/edit/<%=book.getId() %>">更新</a>
                            <%  if(book.getOwnerId() == book.getCurrentUserId()) {%>
                            <br>
                            <a href="#" data-book-id=<%=book.getId() %>>删除</a>
                            <%  } %>
                            <%} %>
                          </td>
                          <td>TODO</td>
                        </tr>
                <%    } %>
                <%} %>
              </tbody>
            </table>
          </div>
          <%Pagination pagination = (Pagination)request.getAttribute(Constants.PAGINATION); %>
          <%String paginationUrl = contextPath + "/book/list?status=" + status; %>
          <%String prevPageUrl = paginationUrl + "&page=" + (pagination.getCurrentPage() - 1) ; %>
          <%String nextPageUrl = paginationUrl + "&page=" + (pagination.getCurrentPage() + 1) ; %>
          <nav>
            <ul class="pagination">
              <li <%=pagination.isFirstPage() ? "class=\"disabled\"" : "" %>>
                <a <%=pagination.isFirstPage() ? "" : "href=\"" + prevPageUrl + "\"" %> aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <%for(int i = 1 ; i <= pagination.getPageCount(); i++) {%>
              <%    if(i == pagination.getCurrentPage()) { %>
              <li class="active"><a><%=i %></a></li>
              <%    } else {%>
              <li><a <%="href=\"" + paginationUrl + "&page=" + i + "\"" %>><%=i %></a></li>
              <%    }%>
              <%} %>
              <li <%=pagination.isLastPage() ? "class=\"disabled\"" : "" %>>
                <a <%=pagination.isLastPage() ? "" : "href=\"" + nextPageUrl + "\"" %> aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
    <!-- Delete Book Confirmation Modal -->
    <div class="modal fade" id="deleteConfirmation" tabindex="-1" role="dialog" aria-labelledby="deleteConfirmationLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="deleteConfirmationLabel">Confirm</h4>
          </div>
          <div class="modal-body">
            Are you sure to delete this book ? This change cannot be undone.
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            <button type="button" class="btn btn-danger btn-delete">Delete</button>
          </div>
        </div>
      </div>
    </div>
    <!-- End of Delete Book Confirmation Modal -->
  </body>
</html>
