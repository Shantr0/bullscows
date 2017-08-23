package main.java;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;

import java.time.Instant;
import java.util.Date;

/**
 * Created by Anton on 14.08.2017.
 */
public class Try{
    private String userTry;
    private String result;
    private Date date;

    public Try(){
        userTry=result="";
    }
    public Try(String attempt, String result, Date date){
        userTry=(attempt);
        this.result=(result);
        this.setDate(date);
    }
    public Try(int attempt, String result, Date date){
        userTry=String.valueOf(attempt);
        this.result=result;
        this.setDate(date);
    }
    public Try(String attempt, String result){
        userTry=(attempt);
        this.result=(result);
        date= Date.from(Instant.now());// текущая дата
    }
    public Try(int attempt, int result, Date date){
        userTry=String.valueOf(attempt);
        this.result=String.valueOf(result);
        this.setDate(date);
    }
    public Try(int attempt, int result){
        userTry=String.valueOf(attempt);
        this.result=String.valueOf(result);
        date= Date.from(Instant.now());// текущая дата
    }

    public String getUserTry() {
        return userTry;
    }

    public void setUserTry(String userTry) {
        this.userTry = userTry;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
