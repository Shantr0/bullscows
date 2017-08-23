package main.java;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Anton on 10.08.2017.
 */
public class User {

    private String name;// имя
    private String password;// пароль
    private int rating;// рейтинг
    private String email;


    List<Try> tries=new ArrayList();// попытки

    public User(){
        rating=0;
    }
    public User(String name){
        this.name=name;
        password="";
        email="";
        rating=0;
    }
    public User(String name,String email){
        this.name=name;
        this.email=email;
        email="";
        rating=0;
    }
    public User(String name,String password,String email){
        this.name=name;
        this.password=password;
        this.email=email;
        rating=0;
    }
    public User(String name,int rating){
        this.name=name;
        this.rating=rating;
        password="";
    }
    public User(String name,String email,int rating){
        this.name=name;
        this.email=email;
        this.rating=rating;
    }
    public User(String name,String password,String email,int rating){
        this.name=name;
        this.password=password;
        this.email=email;
        this.rating=rating;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public Try addTry(String attempt,String result,Date date){
        Try at=new Try(attempt,result,date);
        tries.add(at);
        return at;
    }
    public void addTry(String attempt,String result){
        Date date=Date.from(Instant.now());
        Try at=new Try(attempt,result,date);
        tries.add(at);
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setTries(List<Try>list){
        tries=list;
    }
    public List<Try> getTries(){return tries;}

}
