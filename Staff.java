import java.io.*;
// import java.util.*;

public class Staff implements Serializable{
    static String current_dir = System.getProperty("user.dir");
    static File login_staffDB = new File(current_dir + "\\login_staffDB.txt");

    public String Name;
    public String username;
    private String gender;
    private String password;
    public String Airline;
    private int emp_ID;
    public String designation;

    Staff(){ //Null constructor
    }

    public Staff(String Name, String username, String gender, String password, String Airline, int emp_ID, String designation) {
        this.Name = Name;
        this.username = username;
        this.gender = gender;
        this.password = password;
        this.Airline = Airline;
        this.emp_ID = emp_ID;
        this.designation = designation;
    }
    
    
    public int getID(){
        System.out.println("Your ID is "+this.emp_ID);
        return this.emp_ID;
    }





}