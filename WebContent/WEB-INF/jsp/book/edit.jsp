<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wadeyuan.training.entity.Book" %>
<%@ page import="com.wadeyuan.training.entity.User" %>
<%@ page import="com.wadeyuan.training.common.Constants" %>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="../common/init-head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%Book book = (Book) session.getAttribute(Constants.BOOK); %>
    <%String pageTitle; %>
    <%if(book == null) { %>
    <%    book = new Book(); %>
    <%    pageTitle = "新增图书"; %>
    <%} else {%>
    <%    pageTitle = "编辑图书"; %>
    <%} %>
    <title><%=pageTitle %></title>
    
    <!-- Custom styles for this template -->
    <link href="<%=contextPath%>/static/css/main.css" rel="stylesheet">
    <script>
    $(document).ready(function(){
        $("#saveBookBtn").on("click", function(e) {
            e.preventDefault();
            var editBookForm = $("#editBookForm").get(0);
            var startSave = false;
            var formTip = "";
            if($("#bookName").val().trim() == "") {
                formTip = "Book name is empty";
            } else if($("#author").val().trim() == "") {
                formTip = "Author is empty";
            } else {
                startSave = true;
            }
            if(startSave) {
                editBookForm.submit();
            } else {
                if($("div.form-tip").length > 0) {
                    $("div.form-tip").text(formTip);
                } else {
                    $("#pictureUploader").parent().after('<div class="form-tip alert alert-danger" role="alert">' + formTip + '</div>');
                }
            }
        });
        
        $("#cancelEditBookBtn").on("click", function(e) {
            e.preventDefault();
            // history.back();
            window.location.href="<%=contextPath%>/book/list";
        });
    })
    </script>
  </head>
  <body>
    <%@ include file="../common/navigation.jsp" %>
    <ol class="breadcrumb">
      <li><a href="<%=contextPath%>/book/list">我的图书</a></li>
      <li class="active"><%=pageTitle %></li>
    </ol>
    <%User user = (User)session.getAttribute(Constants.USER); %>
    <%boolean editEnabled = book.getId() < 0 || book.getOwnerId() == user.getId(); %>
    <div class="container">
      <form class="form-horizontal form-edit-book" id="editBookForm" method="post" action="<%=contextPath %>/book/save">
        <fieldset <%=editEnabled ? "" : "disabled" %>>
        <h3>1. 填写书籍信息</h3>
        <div class="form-group">
          <input type="hidden" class="form-control" id="bookId" name="bookId" value="<%=book.getId()%>">
          <input type="hidden" class="form-control" id="ownerId" name="ownerId" value="<%=book.getOwnerId()%>">
          <input type="hidden" class="form-control" id="currentUserId" name="currentUserId" value="<%=book.getCurrentUserId()%>">
          <label for="bookName" class="col-sm-2 control-label">书名</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="bookName" name="bookName" placeholder="Book Name" value="<%=book.getName() %>">
          </div>
        </div>
        <div class="form-group">
          <label for="author" class="col-sm-2 control-label">作者</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="author" name="author" placeholder="Author" value="<%=book.getAuthor() %>">
          </div>
        </div>
        <div class="form-group">
          <label for="description" class="col-sm-2 control-label">简介</label>
          <div class="col-sm-10">
            <textarea class="form-control" id="description" name="description"><%=book.getDescription() %></textarea>
          </div>
        </div>
        <h3>2. 上传书籍封面照片</h3>
        <%
        String previewBgImage = ""; 
        if(book.getPicture() != null && !book.getPicture().isEmpty()) {
            previewBgImage = "background-image:url(" + book.getPicture() +")";
        }
        %>
        <div class="form-group">
          <div class="picture-preview" style="<%=previewBgImage %>"></div>
          <input type="hidden" id="picture" name="picture" value="<%=book.getPicture() %>">
          <input type="file" id="pictureUploader" accept="image/png">
          <p class="help-block">上传100x100的png格式的图片</p>
        </div>
        <%String errorMessage = request.getAttribute(Constants.ERROR_MESSAGE) == null ? "" : (String) request.getAttribute(Constants.ERROR_MESSAGE); %>
        <%if(!errorMessage.isEmpty()) {%>
        <div class="form-tip alert alert-danger" role="alert"><%=errorMessage %></div>
        <%} %>
        </fieldset>
        <div class="form-group">
          <div class="col-sm-12">
          <%if(editEnabled) { %>
            <button type="submit" id="saveBookBtn" class="btn btn-primary">保存图书</button>
          <%} %>
            <button id="cancelEditBookBtn" class="btn">返回</button>
          </div>
        </div>
      </form>
    </div>
  </body>
</html>