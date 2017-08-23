package main.java;



import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Created by Anton on 15.08.2017.
 */
public class Number extends BullsCowsHttpServlet {
    String numberText;

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        numberText= request.getParameter("number");// число
        if(numberText.length()>4){
            numberText=numberText.substring(0,3);
            return;
        }
        if(numberText.length()<4){
            for (int i=0;i<4-numberText.length();i++)numberText="0"+numberText;
        }
        //char ch=request.getParameter("button").charAt(0);
        int sID=(int)request.getSession().getAttribute("id");
        Computer computer=(Computer) request.getSession().getAttribute("game");
        User user=(User) request.getSession().getAttribute("user");
        //Computer.Try tryy=computer.getLastTry();

        String sql="insert into userstries VALUES (?,?,?,?,?,?)";
        String url="jdbc:mysql://localhost:3306/BullsCowsDB";
        try (Connection con= DriverManager.getConnection(url,"root","root"); ){
            PreparedStatement st=con.prepareStatement(sql);
            computer.trying(user,numberText);
            st.setString(1,user.getName());
            st.setString(2,(numberText));
            st.setString(3,computer.getNumber());
            st.setString(4,computer.getLastTry().getResult());
            st.setDate(5,(new java.sql.Date(computer.getLastTry().getDate().getTime())));
            st.setInt(6,sID);
            st.execute();
            if(computer.isGuessed()){// число угадано
                closeSession(con,sID);// закрываем сессию
                int rating=recalculateRating(con,user.getName());// пересчитываем рейтинг
                updateRaring(con,user.getName(),rating);// обновляем рейтинг пользователя в БД
                user.setRating(rating);// устанавливаем новый рейтинг
                request.getSession().setAttribute("number",numberText);
                request.getSession().setAttribute("rating",rating);
                forward("/wingame.jsp",request,response);
            }
            else forward("/continueGame.jsp",request,response);
            //return resultSet.next();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
    private void updateRaring(Connection con,String name ,int rating) throws SQLException {
        String sql="UPDATE users SET rating=? WHERE name=?";// обновляем рейтинг
        PreparedStatement st=con.prepareStatement(sql);
        st.setInt(1,rating);
        st.setString(2,name);
        st.execute();
    }
    private void closeSession(Connection c,int sID) throws SQLException {
        String sql="UPDATE sessions SET CLOSED=TRUE WHERE ID=?";// сессия закрывается
        PreparedStatement st=c.prepareStatement(sql);
        st.setInt(1,sID);
        st.execute();// обновление рейтинга ползователя
    }
    private int recalculateRating(Connection con,String user) throws SQLException{
        String sql="SELECT  (SELECT count(ID) FROM sessions WHERE userName=? AND CLOSED=TRUE ),COUNT(*) FROM userstries " +
                "WHERE userName=?";
        PreparedStatement st=con.prepareStatement(sql);
        st.setString(1,user);
        st.setString(2,user);
        ResultSet set=st.executeQuery();
        set.next();
        int gamesCount=set.getInt(1);
        int triesCount=set.getInt(2);
        return triesCount/gamesCount;
    }
}
