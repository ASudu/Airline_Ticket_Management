import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Customer implements Serializable{

    static String current_dir = System.getProperty("user.dir");
    static File login_customerDB = new File(current_dir + "\\login_customerDB.txt");
    static File flights = new File(current_dir + "\\flights.txt");
    static File flight_seats = new File(current_dir + "\\flight_seats.txt");
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
//        this.booked = null;
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

    public void do_booking()throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        City.display_list_of_cities();
        
        // Get date of travel
        String travel_date = cnsl.readLine("Enter date of travel (format: DD-MM-YYY): ");
        String from = cnsl.readLine("Travel from: ");
        String to = cnsl.readLine("travel to: ");

        // Show relevant flights
        BufferedReader br = null;

        try {
            FileReader fr = new FileReader(flights);
            br = new BufferedReader(fr);

            String line;

            while((line = br.readLine()) != null){

                String[] fields = line.split(",");

                if(fields[2].equals(from) && fields[3].equals(to)){

                    if(Integer.parseInt(fields[5]) > 0){

                        for(int i = 0; i < 6; i++){

                            String spaces = "";

                            for(int j = 0; j < 13 - fields[i].length(); j++)
                                spaces += " ";
                            
                            System.out.println(fields[i] + spaces);
                        }

                        System.out.println("\n");
                    }
                        
                }
                
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        String code = cnsl.readLine("Enter flight code you choose from above list:  ");
        int n = Integer.parseInt(cnsl.readLine("Enter number of passengers: "));

        TreeMap<String, String> passengers = new TreeMap<String, String>();
        for(int i = 0; i< n; i++){

            passengers.put(cnsl.readLine("Enter name of passenger" + Integer.toString(i+1) + ": "), 
            cnsl.readLine("Enter age of passenger" + Integer.toString(i+1) + ": "));
        }
        
        Booking b = new Booking(travel_date, from, to);

        // Bookings done
        b.seat_ops(code, "book", this, passengers);



    }
    // public void view_ticket()

    public void cancel_ticket() throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
      
        // Get date of travel
        String travel_date = cnsl.readLine("Enter date of travel (format: DD-MM-YYY): ");
        String from = cnsl.readLine("Travel from: ");
        String to = cnsl.readLine("travel to: ");
        String code = cnsl.readLine("Enter flight code you booked:  ");
        TreeMap<String, String> passengers = new TreeMap<String, String>();


        // Check if booking exists
        try {

            Path path = Paths.get(current_dir + "\\flight_seats.txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

            for (int k = 0; k < fileContent.size(); k++) {

                String get_code = fileContent.get(k).split("@")[0];
                if(get_code.equals(code)) {

                    String temp = fileContent.get(k); // Copy existing contents of the line to temp
                    if(temp.contains(this.username)){
                        String seat_users = fileContent.get(k).split("@")[1]; //get all the seat users
                        String[] getPassengers = seat_users.split(","); //separate each getPassengers : "seat-no:username$PassengerName"
                        for(String s:getPassengers){
                            if(s.contains(this.username)){       //if seat-no:username$PassengerName contains username
                                String[] op = s.split(":");
                                String passenger_name = op[1].split("$")[1];
                                String passenger_Seat = op[0];
                                passengers.put(passenger_name,passenger_Seat);
                            }
                        }
                        break;
                    }
                }
            }
        }

         catch (Exception e) {
            System.out.println(e.toString());
        }
        Booking b = new Booking(travel_date, from, to);

        // Print the bookings that exist
        System.out.println("Bookings under " + this.username);
        for (Map.Entry<String, String> entry : passengers.entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue() );
        
        String choice = cnsl.readLine("Enter: 1. \"C\" to continue\n2. \"M\" to go to main page\n3.\"Q\" to quit");
        
        if(choice.equals("C")){
            int count = Integer.parseInt(cnsl.readLine("Enter number of seats to be cancelled: "));

            if(count <= passengers.size()){

                String[] cancel_ppl = new String[count];
                System.out.println("Enter names of passengers who seats are to be cancelled: \n");

                for(int i = 0; i<count; i++)
                    cancel_ppl[i] = cnsl.readLine();
                
                for (Map.Entry<String, String> entry : passengers.entrySet()){

                    if(!Arrays.asList(cancel_ppl).contains(entry.getKey()))
                        passengers.remove(entry.getKey());
                }
                    
            }

            else{
                System.out.println("You have booked only " + Integer.toString(passengers.size()) + " !!!!!!");
                cancel_ticket();
            }
        }

        else if(choice.equals("M")){
            // Do something
        }

        else if(choice.equals("Q")){
            System.out.println("Exiting....");
            System.exit(0);
        }

        else{
            System.out.println("Invalid input");
            cancel_ticket();
        }

        // Cancellation done
        b.seat_ops(code, "cancel", this, passengers);
    }
}