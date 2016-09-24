<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wadeyuan.training.common.Constants" %>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="common/init-head.jsp" %>
    <!-- Custom styles for this template -->
    <link href="<%=contextPath%>/static/css/login.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <script>
    $(document).ready(function(){
        $("#submitBtn").on("click", function(e) {
            e.preventDefault();
            var loginForm = $("#loginForm").get(0);
            var startLogin = false;
            var formTip = "";
            if($("#username").val().trim() == "") {
                formTip = "Username is empty";
            } else if($("#password").val().trim() == "") {
                formTip = "Password is empty";
            } else {
                startLogin = true;
            }
            if(startLogin) {
                loginForm.submit();
            } else {
                if($("div.form-tip").length > 0) {
                    $("div.form-tip").text(formTip);
                } else {
                    $("#password").after('<div class="form-tip alert alert-danger" role="alert">' + formTip + '</div>');
                }
            }
        });
    })
    </script>
  </head>
  <body>

    <div class="container">
      <form class="form-signin" id="loginForm" method="post" action="<%=contextPath %>/user/login">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="hidden" name="<%=Constants.REDIRECT_TO %>" value="<%=request.getParameter(Constants.REDIRECT_TO) != null ? request.getParameter(Constants.REDIRECT_TO) : "" %>">
        <label for="inputUsername" class="sr-only">User Name</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="User Name" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        <%String errorMessage = request.getAttribute(Constants.ERROR_MESSAGE) == null ? "" : (String) request.getAttribute(Constants.ERROR_MESSAGE); %>
        <%if(!errorMessage.isEmpty()) {%>
        <div class="form-tip alert alert-danger" role="alert"><%=errorMessage %></div>
        <%} %>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" id="submitBtn">Sign in</button>
      </form>

    </div> <!-- /container -->

  </body>
</html>