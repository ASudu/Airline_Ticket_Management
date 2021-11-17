// package com.company;
import java.io.*;
import java.util.*;

public class Staff implements Serializable{
    static String current_dir = System.getProperty("user.dir");
    static File login_staffDB = new File(current_dir + "\\login_staffDB.txt");

    public String Name;
    public String username;
    private String gender;
    private String password;
    public String Airline;
    private Integer ID;

    Staff(){ //Null constructor
    }

    Staff(String name, String username,String password,String Airline){ //Actual constructor
        this.Name = name;
        this.username = username;
        this.password = password;
        this.Airline = Airline;
        this.ID = hashCode();
    }
    public int getID(){
        System.out.println("Your ID is "+this.ID);
        return this.ID;
    }





}
