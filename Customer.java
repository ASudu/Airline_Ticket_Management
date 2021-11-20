import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Customer implements Serializable{

    static String current_dir = System.getProperty("user.dir");
    static File login_customerDB = new File(current_dir + "\\login_customerDB.txt");
    static Console cnsl = System.console();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    //-------------------------------------------------Variables------------------------------------------------------//
    public String Name;
    public String username;
    private String gender;
    private String password;
    private String address;
    private Integer Balance;
    private int uniqueCode;
    // Additional fields needed while booking
    String travel_date;
    String travel_from;
    String travel_to;
    // String seat_no = null;
    Booking booked;

    //----------------------------------Constructor to create Customer object-----------------------------------------//
    Customer(){
        this.Balance = 0;
        this.booked = null;
    }

    // Constructor to set up the customer's account
    Customer(String name, String username, String password, String address){
        this.Name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.uniqueCode = hashCode();
    }

    String get_password(){
        return this.password;
    }

    //--------------------------------------------------Methods-------------------------------------------------------//
    
      // An option to change the user credentials
      public void change_credentials()throws Exception{
       
        System.out.println("***************   CHANGE CREDENTIALS    ***************");

        String n, u, p, ad;
        n = cnsl.readLine("Enter your new name : ").strip();
        u = cnsl.readLine("Enter your new username : ").strip();

        if(Login.unique_user(u)) {

            p = String.valueOf(cnsl.readPassword("Enter password : "));
            String p1 = String.valueOf(cnsl.readPassword("Confirm password : "));

            if(p1.equals(p)){

                ad = u + "@gmail.com";
                update_logDB(u, p, n, Balance.toString());
                this.Name = n;
                this.username = u;
                this.password = p;
                this.address = ad;

            }

            else{

                System.out.println("Password did not match");
                change_credentials();

            }
            

        }
        
        else {

            System.out.println("Username already exists!");
            change_credentials();

        }


    }

    // Update Balance to be called during setting up customer after login
    public int update_balance(String username, String password,Integer amount,String choice) { 

        if (this.username.equals(username) && this.password.equals(password)) {
            if (choice.equals("deduct")) {
                this.Balance -= amount;
                return 1;
            } else if (choice.equals("add")) {
                this.Balance += amount;
                return 1;
            }
        }
        return 0; //whenever it returns 0 need to call this function again.
    }

    // Update balance to be called during Actual payments
    public int update_balance() throws Exception{
       
        System.out.println("*****************   UPDATE BALANCE    *****************");

        String username;
        String password;
        Integer amount;
        String choice;

        username = this.username;
        password = this.get_password();
        
        choice = cnsl.readLine("Want to add money then type add or deduct : ").strip();

        if (choice.equals("deduct")) {

            System.out.println("Previous Balance Rs."+Balance);
            amount = Integer.parseInt(cnsl.readLine("Enter amount to withdraw : ").strip());
            Integer temp = Balance - amount;
            update_logDB(username,password,this.Name,temp.toString());
            this.Balance -= amount;
            System.out.println("Current Balance Rs."+Balance);
            return 1;

        } 
        
        else if (choice.equals("add")) {

            System.out.println("Previous Balance Rs."+Balance);
            amount = Integer.parseInt(cnsl.readLine("Enter amount to add : ").strip());
            Integer temp = Balance + amount;
            update_logDB(username,password,this.Name,temp.toString());
            this.Balance += amount;
            System.out.println("Current Balance Rs."+Balance);
            return 1;

        }
        
        else{

            System.out.println("Invalid choice...Type \"add\" or \"deduct\"!");
            update_balance();

        }
        
        
        return 0;
    }

    // Check Balance
    public Integer check_balance(){

            System.out.println("Current Balance is Rs."+Balance);
            Integer bal = Balance;
            return bal;
    }

    // Make Payment
    public int make_payment(String username,String password,Integer amount){

        if (this.username.equals(username) && this.password.equals(password)){

            System.out.println("Previous Balance Rs."+Balance);
            update_balance(username,password,amount,"deduct");
            System.out.println("Your current balance is: Rs."+Balance);
            return 1;

        }

        return 0; //whenever it returns 0 need to call this function again.

    }

    public void update_logDB(String u,String p,String n,String b)throws Exception{
        
        // First find the user by username :(unique identity)
        Path path = Paths.get(current_dir + "\\login_customerDB.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {

            if (fileContent.get(i).equals(username+","+password+","+Name+","+Balance)) {

                fileContent.set(i, u+","+p+","+n+","+b);
                break;

            }

        }

        Files.write(path, fileContent, StandardCharsets.UTF_8);
    }

    public void do_booking(){

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        City.display_list_of_cities();
        
        // Get date of travel
        String travel_date = cnsl.readLine("Enter date of travel (format: DD-MM-YYY): ");
        String from = cnsl.readLine("Travel from: ");
        String to = cnsl.readLine("travel to: ");

        Booking b = new Booking(travel_date, from, to);



    }
    // public void view_ticket()
    // public void cancel_ticket()


}