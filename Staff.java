import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
// import java.util.*;

public class Staff implements Serializable{
    static String current_dir = System.getProperty("user.dir");
    static File login_staffDB = new File(current_dir + "\\login_staffDB.txt");

    public String Name;
    public String username;
//    private String gender;
    private String password;
    public String Airline;
    private String emp_ID;
    public String designation;

    Staff(){ //Null constructor
    }

    public Staff(String Name, String username, String password, String Airline, String emp_ID, String designation) {
        this.Name = Name;
        this.username = username;
//        this.gender = gender;
        this.password = password;
        this.Airline = Airline;
        this.emp_ID = emp_ID;
        this.designation = designation;
    }

    public String getID(){
        System.out.println("Your ID is "+this.emp_ID);
        return this.emp_ID;
    }

    public void update_slogDB(String n,String u,String p,String a,String id,String de)throws Exception{

        // First find the user by username :(unique identity)
        Path path = Paths.get(current_dir + "\\login_customerDB.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {

            if (fileContent.get(i).equals(Name+","+username+","+password+","+Airline+","+emp_ID+","+designation)) {

                fileContent.set(i, n+","+u+","+p+","+a+","+id+","+de);
                break;

            }

        }

        Files.write(path, fileContent, StandardCharsets.UTF_8);
    }

    public void change_StaffCredentials()throws Exception{

        System.out.println("***************   CHANGE CREDENTIALS    ***************");

        String n, u, p;
        Console cnsl1 = System.console();
        n = cnsl1.readLine("Enter your new name : ").strip();
        u = cnsl1.readLine("Enter your new username : ").strip();

        if(Login.unique_user(u,"Staff")) {

            p = String.valueOf(cnsl1.readPassword("Enter password : "));
            String p1 = String.valueOf(cnsl1.readPassword("Confirm password : "));

            if(p1.equals(p)){

                update_slogDB(n,u,p,Airline,emp_ID,designation);
                this.Name = n;
                this.username = u;
                this.password = p;

            }

            else{

                System.out.println("Password did not match");
                change_StaffCredentials();

            }


        }

        else {

            System.out.println("Username already exists!");
            change_StaffCredentials();

        }


    }
    public void generate_boarding_pass(Customer c){
        c.view_ticket();

    }






}