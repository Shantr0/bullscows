<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 15.08.2017
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="Account.jsp">
    Вы угадали число:
    <%=session.getAttribute("number")%><br>
    Ваш рейтинг:
    <%=session.getAttribute("rating")%><br>
    <input type="submit" value="назад">
</form>
</body>
</html>
