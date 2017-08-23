<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 10.08.2017
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка регистрации</title>
</head>
<body>
<br>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="application"/>
Ошибка:<%=errorMessage%><br>
<form>
    <input type="submit" formaction="reg.html" value="назад">
</form>

</body>
</html>
