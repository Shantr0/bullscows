<%@ page import="main.java.*" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.User" %>
<%@ page import="java.util.Date" %>
<%@ page import="main.java.Try" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 10.08.2017
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход выполнен</title>
</head>
<body>
<h1>Вы вошли в аккаунт</h1>
<jsp:useBean id="user" class="main.java.User" scope="application"/>
Добро пожаловать <%= user.getName()%><br>
Ваш рейтинг: <%= user.getRating()%><br>

<form method="post">
    <br>
    <%session.setAttribute("user",user);
    boolean complete=false;
    boolean newgame=true;
    String sql="SELECT ID,CLOSED FROM sessions WHERE userName=? ORDER BY DATE DESC LIMIT 1";// получаем последнюю сессию
    String url = "jdbc:mysql://localhost:3306/BullsCowsDB";
        try (Connection con = DriverManager.getConnection(url, "root", "root"); ) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,user.getName());
            ResultSet set= st.executeQuery();
            if (set.next()) {// проверка наличия игр
                int id = set.getInt(1);
                complete=set.getBoolean(2);
                newgame = complete;// 4 быка- игра завершена
                session.setAttribute("id",id);
                sql="select * from userstries where  game_id=? ORDER BY DATE DESC ";// последняя игра пользователя
                st=con.prepareStatement(sql);
                st.setString(1,user.getName());
                st.setInt(1,id);
                set = st.executeQuery();
                if (set.next()) {// последняя попытка
                    user.getTries().clear();
                    String res = set.getString("result");
                    do {
                        res = set.getString("result");
                        String numb = set.getString("number");
                        Date date = set.getDate("date");
                        user.addTry(numb, res, date);
                    } while (set.next());
                }
            } else {
                newgame=true;
            }
            st.close();
            //session.setAttribute("complete",complete);
            if (newgame){// если последняя игра завершена или игр нет можем начать новую
    %>
    <input type="submit" name="newGame" formaction="/NewGame" value="новая игра">
    <% }else{ // продолжаем предыдущую %>
    <input type="submit" name="continue" formaction="continueGame.jsp" value="продолжить">
    <%
                //id=set.getInt("game_id");
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
        if(!newgame || complete){
            ArrayList<Try> list=(ArrayList<Try>) user.getTries();
            session.setAttribute("list",list);
            try {
                if(!user.getTries().isEmpty()){
                    //Try tr=list.get(0);
        %>
    <br>
    <br>
    Предыдущий результат:
    <table border="1">
        <tr>
            <th>введенное число</th>
            <th>результат</th>
            <th>время</th>
        </tr>
        <% for (Try tr:user.getTries()) {%>
            <tr >
                <td><%=tr.getUserTry()%></td>
                <td><%=tr.getResult()%></td>
                <td><%=tr.getDate()%></td>
            </tr>
            <% } %>
    </table>
    <% }
            }catch (Exception e){
                System.out.print(e.getMessage());
    }
        } %>
</form>
</body>
</html>
