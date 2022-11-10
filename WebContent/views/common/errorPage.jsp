<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String errorMsg = (String)request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나는 문제가 생겼을 때 오는 곳이야</title>
</head>
<body>
    <h1 align="center" style="color:red; margin-top:100px;"><%= errorMsg %></h1>
</body>
</html>