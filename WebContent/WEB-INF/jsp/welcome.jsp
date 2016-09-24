<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wadeyuan.training.entity.User" %>
<%@ page import="com.wadeyuan.training.common.Constants" %>

<!DOCTYPE html">
<html>
<head>
  <%@ include file="common/init-head.jsp" %>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Welcome</title>
</head>
<body>
  <%User user = (User) session.getAttribute(Constants.USER); %>
  <%if(user != null) { %>
  <span>Welcome <%=user.getUsername() %></span>
  <%} %>
</body>
</html>