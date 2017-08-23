package main.java;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

/**
 * Created by Anton on 15.08.2017.
 */
public class NewGame extends BullsCowsHttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        Computer c=new Computer();
        //c.generateNumber();
        User user=(User) request.getSession().getAttribute("user");
        String sql="insert into sessions (number,userName,date) VALUES (?,?,NOW())";
        String url = "jdbc:mysql://localhost:3306/BullsCowsDB";
        try (Connection con = DriverManager.getConnection(url, "root", "root"); PreparedStatement st = con.prepareStatement(sql)) {
            String address="/continueGame.jsp";
            st.setString(1,c.getNumber());
            st.setString(2,user.getName());
            st.execute();// создается новая сессия
            sql="SELECT ID FROM sessions ORDER BY ID DESC LIMIT 1";// получаем id новой сессии
            ResultSet set= st.executeQuery(sql);
            if(set.next()){
                int id=set.getInt(1);
                HttpSession session=request.getSession();
                session.setAttribute("id",id);
                session.setAttribute("game",c);
                forward(address,request,response);
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
