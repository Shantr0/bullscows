<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 11.08.2017
  Time: 2:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация завершена.</title>
</head>
<body>
<h1>Регистрация посетителя успешно завершена</h1>
<jsp:useBean id="user" class="main.java.User" scope="application"/>
Пользователь: <%= user.getName()%><br>
Зарегистрирован.<br><br>
<a href="index.html">Войти в систему</a>
</body>
</html>
