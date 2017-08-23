package main.java;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 10.08.2017.
 */
@WebServlet(name = "BullsCowsHttpServlet")
public class BullsCowsHttpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.print("драйвер com.mysql.jdbc.Driver не найден, усановите соответствующий драйвер");
        }
        String login=request.getParameter("login");

        if (login!=null){// заход в систему
            String name=request.getParameter("user");
            //session.putValue("name",name);
             String password=request.getParameter("password");
            //Driver driver=new FabricMySQLDriver();
            String url="jdbc:mysql://localhost:3306/BullsCowsDB";
            ResultSet resultSet;
            try (Connection con=DriverManager.getConnection(url,"root","root");Statement st=con.createStatement()){
                String sql="select * from users where name="+"'"+name+"'";
                resultSet=st.executeQuery(sql);
                if (password==null || password.isEmpty()) throw new IllegalArgumentException("введите пароль");
                if (resultSet.next()){
                    String pas=resultSet.getString("password");
                    if(password.equals(pas)){
                        String email=resultSet.getString("email");
                        int rating=resultSet.getInt("rating");
                        User user = new User(name, email,rating);
                        //System.out.print("Вы вошили в аккаунт "+name);
                        user.setTries(getUserTries(user.getName()));
                        ctx.setAttribute("user",user);
                        forward("/Account.jsp", request, response);
                    }else {
                        System.out.print("неверный логин или пароль");
                        forward("/errorlogin.html", request, response);
                    }
                }else throw new IllegalArgumentException();
            }
            catch (IllegalArgumentException e){
                System.out.print("неверный логин или пароль");
                forward("/errorlogin.html", request, response);
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }
            //forward("/index.jsp", request, response);
        } else if (request.getParameter("register")!=null) {
            this.forward("/reg.html", request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter write= response.getWriter();
        //write.println("Hello from writer and servlet");
    }

    protected void forward(String address, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    public User getUserByNameAndMail(String name,String email){// получить ползователя по имени и почте
        User user=null;
        String sql="select * from users where name=? and email=?";
        String url="jdbc:mysql://localhost:3306/BullsCowsDB";
        try (Connection con= DriverManager.getConnection(url,"root","root"); PreparedStatement st=con.prepareStatement(sql)){
            st.setString(1,name);
            st.setString(2,email);
            ResultSet resultSet=st.executeQuery();
            if(resultSet.next()){
                int rating=resultSet.getInt("rating");
                user=new User(name,email,rating);
                return user;
            }
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return user;
    }
    public User getUserByName(String name){// получить ползователя по имени и почте
        User user=null;
        String sql="select * from users where name=?";
        String url="jdbc:mysql://localhost:3306/BullsCowsDB";
        try (Connection con= DriverManager.getConnection(url,"root","root"); PreparedStatement st=con.prepareStatement(sql)){
            st.setString(1,name);
            ResultSet resultSet=st.executeQuery();
            if(resultSet.next()){
                int rating=resultSet.getInt("rating");
                String email=resultSet.getString("email");
                user=new User(name,email,rating);
                return user;
            }
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return user;
    }
    public boolean hasUser(String name,String email){/** проверка наличия ползователя с
     заданным именем или почтой*/
        String sql="select * from users where name=? or email=?";
        String url="jdbc:mysql://localhost:3306/BullsCowsDB";
        try (Connection con= DriverManager.getConnection(url,"root","root"); PreparedStatement st=con.prepareStatement(sql)){
            st.setString(1,name);
            st.setString(2,email);
            ResultSet resultSet=st.executeQuery();
            return resultSet.next();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return false;
    }
    public static List<Try> getUserTries(String name){
        List<Try> list= new  ArrayList();
        String sql="select * from usertries where username=?";
        //ServletContext context= getServletContext();
        String url = "jdbc:mysql://localhost:3306/BullsCowsDB";
        try (Connection con = DriverManager.getConnection(url, "root", "root"); PreparedStatement st = con.prepareStatement(sql)){
            st.setString(1,name);
            ResultSet set= st.executeQuery();
            //User user=getUserByName(name);
            while (set.next()){
                int trynumber=set.getInt("number");
                int number=set.getInt("gen_num");
                Date date=set.getDate("date");
                list.add(new Try(trynumber,number,date));
            }
        }
        catch (Exception e){}
        return list;
    }
}
