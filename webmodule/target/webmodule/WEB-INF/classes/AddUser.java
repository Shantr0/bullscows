package main.java;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Created by Anton on 11.08.2017.
 */

public class AddUser extends BullsCowsHttpServlet{
    public String getServletInfo(){
        return "Add user servlet";
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        if (request.getParameter("accept")!=null){
            String name = request.getParameter("login");
            String password = request.getParameter("password");
            String email=request.getParameter("email");
            //int rating =Integer.getInteger(request.getParameter("rating")) ;
            User newUser = new User(name,password, email,0);
            //session.putValue("name",name);
            String url = "jdbc:mysql://localhost:3306/BullsCowsDB";
            String sql = "INSERT INTO users (name, email, password) VALUES (?,?,?)";
            try {
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch (ClassNotFoundException e){
                System.out.print("драйвер com.mysql.jdbc.Driver не найден, усановите соответствующий драйвер");
            }
            try (Connection con = DriverManager.getConnection(url, "root", "root"); PreparedStatement st = con.prepareStatement(sql)) {
                BullsCowsHttpServlet servlet = new BullsCowsHttpServlet();
                if (!servlet.hasUser(name, email)) {
                    st.setString(1, name);
                    st.setString(2, email);
                    st.setString(3, password);
                    st.execute();

                    ctx.setAttribute("user", newUser);
                    forward("/successRegistration.jsp", request, response);
                }else {
                    ctx.setAttribute("errorMessage","пользователь с этим именем или почтой существует");
                    this.forward("/ErrorReg.jsp", request, response);
                }

            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        } else if (request.getParameter("cancel")!=null){
            this.forward("/index.html", request, response);
        }
    }

}
