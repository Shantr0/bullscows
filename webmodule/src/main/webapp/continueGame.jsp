<%@ page import="java.util.List" %>
<%@ page import="main.java.User" %>
<%@ page import="main.java.Try" %>
<%@ page import="main.java.Computer" %>
<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 15.08.2017
  Time: 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
</form>
<form method="post">
    <jsp:useBean id="user" class="main.java.User" scope="application"/>
    <%
        int id=(int)session.getAttribute("id");
        List<Try>list=user.getTries();
        session.setAttribute("list",list);
        String sql="select number from sessions WHERE ID=?";
        Computer computer=new Computer();
        try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BullsCowsDB","root","root"); ){
            PreparedStatement st=con.prepareStatement(sql);
            st.setInt(1,id);
            ResultSet set= st.executeQuery();
            set.next();
            String number= set.getString(1);
            computer.setNumber(number);
            session.setAttribute("game",computer);
            sql="select * from userstries WHERE game_id=?";//попытки в текущей сессии
            st=con.prepareStatement(sql);
            st.setInt(1,id);
            set=st.executeQuery();
            boolean empty=!set.next();
            if(!empty){
                list.clear();
                do {
                    String numb=set.getString("number");
                    String res=set.getString("result");
                    Date date=set.getDate("date");
                    list.add(new Try(numb,res,date));
                }while (set.next());
            }
    %>
    Угадайте число <br>
    <input type="text" name="number" pattern="[0-9]{4}" >
    <br>
    <input type="submit" name="accept" value="принять" formaction="/Number">
    <input type="submit" formaction="index.html" value="выход">
    <br>
    <%
            if (!list.isEmpty() && !empty) {
    %>
    <h4>предыдущие попытки</h4>

    <table border="1">
        <tr>
            <th>введенное число</th>
            <th>результат</th>
            <th>время</th>
        </tr>
        <% for (Try tr:user.getTries()) {%>
        <tr>
            <td><%=tr.getUserTry()%></td>
            <td><%=tr.getResult()%></td>
            <td><%=tr.getDate()%></td>
        </tr>
        <% } %>
    </table>
    <%}
        }catch (Exception e){
            System.out.print(e.getMessage());
            }%>
</form>
</body>
</html>
