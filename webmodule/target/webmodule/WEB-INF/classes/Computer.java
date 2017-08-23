package main.java;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Anton on 11.08.2017.
 */
public class Computer {

    private String number;// загаданное число
    private boolean guessed;// угадано ли число
    private int triesCount;
    private TryC lastTry;
    public class TryC extends Try{
        private String user;
        public TryC(String user){
            super();
            this.user=user;
        }
        public TryC(String user,String tryNumb){
            setUserTry(tryNumb);
            setDate(Date.from(Instant.now()));
            this.user=user;
        }
        public TryC(String user,String tryNumb,String result){
            setUserTry(tryNumb);
            setDate(Date.from(Instant.now()));
            setResult(result);
            this.user=user;
        }
        public TryC(String user,Try tryy){
            setUserTry(tryy.getUserTry());
            setResult(tryy.getResult());
            setDate(tryy.getDate());
            this.user=user;
        }
        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }


    public Computer(){
        guessed=false;
        triesCount=0;
        generateNumber();
    }
    public Computer(int number){
        guessed=false;
        triesCount=0;
        this.number=String.valueOf(number);
    }

    public void setNumber(String number){
        this.number=(number);
    }
    public boolean isGuessed(){
        return guessed;
    }
    public int getTriesCount(){
        return triesCount;
    }
    public void generateNumber(){   // генерация числа
        LinkedList<Integer> digits=new LinkedList();
        for(int i=0;i<10;i++)digits.add(i);
        Random random=new Random();
        StringBuilder v=new StringBuilder(4);

        //int pow=1000;
        //int val=0;
        for(int i=0;i<4;i++){
            int ind=random.nextInt(digits.size()-1);
            v.append(digits.remove(ind));
            //val+=pow*digits.remove(ind);// рандомная цифра на порядок
            //pow/=10;
        }
        number=v.toString();
    }

    public String getResult(){return lastTry.getResult();}
    public String getResult(String attempt){
        int bulls,cows;
        bulls=cows=0;
        String numb=String.valueOf(number);
        for (int i=0;i<4;i++){
            CharSequence cs=attempt.substring(i,i+1);
            if(numb.contains(cs)){// проверка наличия цифры
                if(attempt.charAt(i)==numb.charAt(i)) bulls++;// совпадение по позициям
                else cows++;
            }
        }
        String str=bulls+"Б"+cows+"К";
        guessed=bulls==4;
        //triesCount++;
        return str;
    }
    public void trying(User user,String numb){// попытка пользователя
        String res=getResult(numb);// результат быки-коровы
        Try aTry=user.addTry(numb,res, new Date());
        lastTry=new TryC(user.getName(),aTry);
    }
    public Try getLastTry(){
        return lastTry;
    }
    public String getNumber() {
        return number;
    }

}
