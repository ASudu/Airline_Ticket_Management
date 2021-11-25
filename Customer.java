import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class Customer implements Serializable{

    static String current_dir = System.getProperty("user.dir");
    static File login_customerDB = new File(current_dir + "\\login_customerDB.txt");
    static File flights = new File(current_dir + "\\flights.txt");
    static File flight_seats = new File(current_dir + "\\flight_seats.txt");
    static LogFile log_file = new LogFile();
    static Console cnsl = System.console();

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
        this.Balance = 0;
    }

    String get_password(){
        return this.password;
    }

    //--------------------------------------------------Methods-------------------------------------------------------//

    // An option to change the user credentials
    public void change_credentials()throws Exception{

        System.out.println("***************   CHANGE CREDENTIALS    ***************");

        String n, u, p, ad;
        System.out.println("Enter your new name : ");
        n = br.readLine().strip();
        System.out.println("Enter your new username : ");
        u = br.readLine().strip();

        if(Login.unique_user(u,"Customer")) {
            p = String.valueOf(cnsl.readPassword("Enter password : ")).strip();

            String p1 = String.valueOf(cnsl.readPassword("Confirm Password : ")).strip();

            if(p1.equals(p)){


                Path path1 = Paths.get(current_dir + "\\flight_seats.txt");
                List<String> fileContent = new ArrayList<>(Files.readAllLines(path1, StandardCharsets.UTF_8));

                if(fileContent.size()>0){
                for (int k = 0; k < fileContent.size(); k++){
                    if(fileContent.get(k).contains(this.username)){
                        String[] str = fileContent.get(k).split("@");
                        String passengers = str[1];
                        String[] iter = passengers.split(",");  //   1-A:username$Proper Name
                        ArrayList<String> updated = new ArrayList<String>();
                        for(String s:iter){
                            if(s.contains(this.username+"$"+this.Name)){
                                String temp = s.split(":")[0];
                                String ver_no = s.split("\\+")[1];
                                String new_n = temp+":"+u+"$"+n+"+"+ver_no;
                                updated.add(new_n);
                            }else if(s.contains(this.username)){
                                String temp = s.split(":")[0];
                                String temp2 = s.split(Pattern.quote("$"))[1];
                                String new_n = temp+":"+u+"$"+temp2;
                                updated.add(new_n);
                            }else{
                                updated.add(s);
                            }
                        }

                        String temp = "";
                        for(String s:updated){
                            if(!s.equals(updated.get(updated.size()-1)))
                                temp = temp.concat(s+",");
                            else temp = temp.concat(s);
                        }
                        fileContent.set(k,str[0]+"@"+temp);


                    }
                }
                    Files.write(path1, fileContent, StandardCharsets.UTF_8);

                }

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
        System.out.println("Want to add money then type add or deduct : ");
        choice = br.readLine().strip();

        if (choice.equals("deduct")) {

            System.out.println("Previous Balance Rs."+Balance);
            System.out.println("Enter amount to withdraw : ");
            amount = Integer.parseInt(br.readLine().strip());
            Integer temp = Balance - amount;
            update_logDB(username,password,this.Name,temp.toString());
            this.Balance -= amount;
            System.out.println("Current Balance Rs."+Balance);
            return 1;


        } 

        else if (choice.equals("add")) {

            System.out.println("Previous Balance Rs."+Balance);
            System.out.println("Enter amount to add : ");
            amount = Integer.parseInt(br.readLine().strip());
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

    public int update_balance(String choice,Integer amount,float weight) throws Exception{

        System.out.println("*****************   UPDATE BALANCE    *****************");

        String username;
        String password;

      

        username = this.username;
        password = this.get_password();
        Integer original = amount;
       float amt = weight*amount;
       amount = (int)amt;
        
        if (choice.equals("book")&&Balance>amount) {

            System.out.println("Previous Balance Rs."+Balance);

            Integer temp = Balance -  amount;
            update_logDB(username,password,this.Name,temp.toString());
            this.Balance -= amount;
            System.out.println("Current Balance Rs."+Balance);
            return 1;

        }


        else if (choice.equals("cancel")) {

            System.out.println("Previous Balance Rs."+Balance);

            Integer temp = Balance + amount;
            update_logDB(username,password,this.Name,temp.toString());
            this.Balance += amount;
            System.out.println("Current Balance Rs."+Balance);
            return 1;

        }

        else{

            System.out.println("Not Sufficient funds");
            int c = update_balance();
            if(c==1){
                update_balance(choice,original,weight);
            }

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
        System.out.println("Enter date of travel (format: DD-MM-YYY): ");
        String travel_date = br.readLine().strip();
        if(date_check(travel_date) != 1){
            System.out.println("Press B to go for booking again or Press Q to quit and go back to main menu");
            String read = br.readLine();
            if(read.equals("B")){
            do_booking();}
            else if(read.equals("Q")){
                Main.Succesful_login(this);
            }else{
                System.exit(0);
            }
        }
        else if(date_check(travel_date) == 1) {
            System.out.println("Travel from: ");
            String from = br.readLine().strip();
            System.out.println("travel to: ");
            String to = br.readLine().strip();

            // Show relevant flights
            int count_flag = 0;
            int found = 0;


            try {
                Path path = Paths.get(current_dir + "\\flights.txt");
                List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

                for (int k = 0; k < fileContent.size(); k++) {

                    String[] fields = fileContent.get(k).split(",");
                    if (fields[2].equals(from) && fields[3].equals(to)) {
                        found = 1;

                        if (Integer.parseInt(fields[5]) > 0) {

                            for (int i = 0; i < 7; i++) {

                                String spaces = "";

                                for (int j = 0; j < 13 - fields[i].length(); j++)
                                    spaces += " ";

                                System.out.print(fields[i] + spaces);
                            }

                            System.out.println("\n");
                        }


                    }else continue;

                    }




            } catch (Exception e) {
                System.out.println(e.toString());
            }

            if(found==0){
                System.out.println("The Flight connecting these two cities don't exist");
                System.out.println("Press B to again go back to booking menu");
                String read = br.readLine();
                if(read.equals("B")){

                do_booking(); }
            }
            System.out.println("Enter flight code you choose to fly with from above list:  ");
            String code = br.readLine();
            System.out.println("Enter number of passengers: ");
            TreeMap<String, String> passengers = new TreeMap<String, String>();
            try{
            int n = Integer.parseInt(br.readLine().strip());


            for (int i = 0; i < n; i++) {
                System.out.println("Enter name of passenger " + Integer.toString(i + 1) + " : ");
                String np_name = br.readLine().strip();
                System.out.println("Enter age of passenger " + Integer.toString(i + 1) + " : ");
                String np_age = br.readLine().strip();
                passengers.put(np_name, np_age);

            }
        }catch (Exception e){
                System.out.println("Please be consistent with inputs !");
                do_booking();
            }

            int already_booked = -1;
            try {

                Path path = Paths.get(current_dir + "\\flight_seats.txt");
                List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
                String passenger_name = "";
                ArrayList<String> passenger_names = new ArrayList<>();

                if(fileContent.size()>0) {
                    for (int k = 0; k < fileContent.size(); k++) {

                        String get_code = fileContent.get(k).split("@")[0];
                        if (get_code.equals(code)) {

                            String temp = fileContent.get(k); // Copy existing contents of the line to temp
                            if (temp.contains(this.username)) {

                                String seat_users = fileContent.get(k).split("@")[1]; //get all the seat users
                                String[] getPassengers = seat_users.split(","); //separate each getPassengers : "seat-no:username$PassengerName"

                                for (String s : getPassengers) {
                                    if (s.contains(this.username)) {       //if seat-no:username$PassengerName contains username
                                        String[] op = s.split(":");

                                        String temp2 = op[1];
                                        String[] NAME = temp2.split(Pattern.quote("$"));

                                        if (passengers.keySet().contains(NAME[1])) {
                                            already_booked = 1;
                                            passenger_names.add(NAME[1].split("\\+")[0]);
                                        } else
                                            already_booked = 0;
                                    }
                                }
                                break;
                            }
                            else already_booked = 0;
                        }else
                            already_booked = 0;
                    }
                }else
                    already_booked = 0;

                    // Booking doesn't exist so allow booking
                    if (already_booked == 0) {

                        Booking b = new Booking(travel_date, from, to);
                        // Bookings done
                        Path path1 = Paths.get(current_dir + "\\flights.txt");
                        float weight = 0;

                        // For assigning fare
                        int adult = 0, child = 0;
                        for (Map.Entry<String, String> entry : passengers.entrySet()) {
                            if (Integer.parseInt(entry.getValue()) < 10) {
                                child++;
                            } else {
                                adult++;
                            }

                        }
                        weight = adult + (child / 2);
                        List<String> fileContent1 = new ArrayList<>(Files.readAllLines(path1, StandardCharsets.UTF_8));
                        for (int i = 0; i < fileContent1.size(); i++) {
                            if (fileContent1.get(i).contains(code)) {
                                String[] str = fileContent1.get(i).split(",");
                                this.update_balance("book", Integer.parseInt(str[4]), weight);
                            }
                        }
                        b.seat_ops(code, "book", this, passengers);

                        String[] arr = get_flight_details(code);
                        int amount = (int) weight * Integer.parseInt(arr[4]);
                        update_flight_amount(code, amount);
                        String choice = "";
                        do {
                            System.out.println("Enter 1. \"R\" to re-enter booking\n2. \"M\" to go to main page\n3. \"Q\" to quit");
                             choice = br.readLine().strip();

                            if (choice.equals("R"))
                                do_booking();

                            else if (choice.equals("M")) {
                                // Do something
                                Main.Succesful_login(this);
                            } else if (choice.equals("Q")) {
                                System.out.println("Exiting....");
                                System.exit(0);
                            } else
                                System.out.println("Please enter valid input!");
                        } while (!choice.equals("R") && !choice.equals("M") && !choice.equals("Q"));


                    }

                    // Booking already done for few members
                    else if (already_booked == 1) {

                        System.out.println("Seat is already booked for:");
                        for (String s : passenger_names) {
                            System.out.println(s);
                        }

                        String choice = "";

                        do {
                            System.out.println("Enter 1. \"R\" to re-enter booking\n2. \"M\" to go to main page\n3. \"Q\" to quit");
                            choice = br.readLine().strip();

                            if (choice.equals("R"))
                                do_booking();

                            else if (choice.equals("M")) {
                                // Do something
                                Main.Succesful_login(this);
                            } else if (choice.equals("Q")) {
                                System.out.println("Exiting....");
                                System.exit(0);
                            } else
                                System.out.println("Please enter valid input!");
                        } while (!choice.equals("R") && !choice.equals("M") && !choice.equals("Q"));
                    }
                }
             catch (Exception e) {
                System.out.println(e.toString());
                 System.out.println("please be consistent with inputs !");
                 String choice = "";
                 do {
                     System.out.println("Enter 1. \"R\" to re-enter booking\n2. \"M\" to go to main page\n3. \"Q\" to quit");
                     choice = br.readLine().strip();

                     if (choice.equals("R"))
                         do_booking();

                     else if (choice.equals("M")) {
                         // Do something
                         Main.Succesful_login(this);
                     } else if (choice.equals("Q")) {
                         System.out.println("Exiting....");
                         System.exit(0);
                     } else
                         System.out.println("Please enter valid input!");
                 } while (!choice.equals("R") && !choice.equals("M") && !choice.equals("Q"));
            }
        }
    }

    public void view_ticket() throws Exception {
        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int booking_exists = 0;

        //Get details of the flight
        BoardingPass bp;


        try {
            String fc="";

            Path path = Paths.get(current_dir + "\\flight_seats.txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

            for (int k = 0; k < fileContent.size(); k++) {

                String get_code = fileContent.get(k).split("@")[0];
                String temp = fileContent.get(k); // Copy existing contents of the line to temp

                    if(temp.contains(this.username)){
                        fc = get_code;
                        booking_exists = 1;
                        bp = new BoardingPass(this.username, fc);
                        bp.display_boarding_pass();
                        break;
                    }
            

                if(temp.contains(this.username)){
                    fc = get_code;
                    booking_exists = 1;
                    bp = new BoardingPass(this.username, fc);
                    bp.display_boarding_pass();
                    break;
                }

            }
            String choice1 = "";
            do {
                System.out.println("Enter 1. \"R\" to re-enter booking\n2. \"M\" to go to main page\n3. \"Q\" to quit");
                choice1 = br.readLine().strip();

                if (choice1.equals("R"))
                    do_booking();

                else if (choice1.equals("M")) {
                    // Do something
                    Main.Succesful_login(this);
                } else if (choice1.equals("Q")) {
                    System.out.println("Exiting....");
                    System.exit(0);
                } else
                    System.out.println("Please enter valid input!");
            } while (!choice1.equals("R") && !choice1.equals("M") && !choice1.equals("Q"));

            if(booking_exists == 0){
                System.out.println("Booking doesn't exists in this username");
                String choice = "";
                do{
                    System.out.println("Enter: 1. \"B\" to book\n2. \"M\" to go to main page\n3.\"Q\" to quit");
                    choice = br.readLine().strip();

                    if(choice.equals("B"))
                        do_booking();

                    else if(choice.equals("M")){
                        // Do something
                        Main.Succesful_login(this);
                    }

                    else if(choice.equals("Q")){
                        System.out.println("Exiting....");
                        System.exit(0);
                    }

                    else
                        System.out.println("Please enter a valid input");

                }while(!choice.equals("B") && !choice.equals("M") && !choice.equals("Q"));
            }
        }catch(Exception e ){
            System.out.println(e.toString());
            System.out.println("please be consistent with inputs !");
            String choice = "";
            do {
                System.out.println("Enter 1. \"R\" to re-enter booking\n2. \"M\" to go to main page\n3. \"Q\" to quit");
                choice = br.readLine().strip();

                if (choice.equals("R"))
                    do_booking();

                else if (choice.equals("M")) {
                    // Do something
                    Main.Succesful_login(this);
                } else if (choice.equals("Q")) {
                    System.out.println("Exiting....");
                    System.exit(0);
                } else
                    System.out.println("Please enter valid input!");
            } while (!choice.equals("R") && !choice.equals("M") && !choice.equals("Q"));

        }




    }

    public void cancel_ticket() throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Get date of travel
        System.out.println("Enter date of travel (format: DD-MM-YYY): ");
        String travel_date = br.readLine().strip();
        System.out.println("Travel from: ");
        String from = br.readLine().strip();
        System.out.println("travel to: ");
        String to = br.readLine().strip();
        System.out.println("Enter flight code you booked:  ");

        String code = br.readLine().strip();
        TreeMap<String, String> passengers = new TreeMap<String, String>();

        int booking_exists = 0;
        int flightcode_found = 0;


        // Check if booking exists
        try {

            Path path = Paths.get(current_dir + "\\flight_seats.txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            if(fileContent.size()>0){
            for (int k = 0; k < fileContent.size(); k++) {

                String get_code = fileContent.get(k).split("@")[0];
                if(get_code.equals(code)) {
                    flightcode_found = 1;

                    String temp = fileContent.get(k); // Copy existing contents of the line to temp
                    if(temp.contains(this.username)){

                        booking_exists = 1;
                        String seat_users = fileContent.get(k).split("@")[1]; //get all the seat users
                        String[] getPassengers = seat_users.split(","); //separate each getPassengers : "seat-no:username$PassengerName"
                        for(String s:getPassengers){
                            if(s.contains(this.username)){       //if seat-no:username$PassengerName contains username
                                String[] op = s.split(":");
                                String passenger_name = op[1].split(Pattern.quote("$"))[1];
                                String passenger_Seat = op[0];
                                passengers.put(passenger_name.split("\\+")[0],passenger_Seat);
                            }
                        }
                        break;
                    }else booking_exists = 0;
                }else{
                    flightcode_found = 0;
                    booking_exists = 0;}
            }
            }else booking_exists = 0;

            if(booking_exists == 0 && flightcode_found!=0){
                System.out.println("Booking doesn't exists in this username");
                String choice = "";
                do{
                    System.out.println("Enter: 1. \"B\" to book\n2. \"M\" to go to main page\n3.\"Q\" to quit");
                    choice = br.readLine().strip();

                    if(choice.equals("B"))
                        do_booking();

                    else if(choice.equals("M")){
                        // Do something
                        Main.Succesful_login(this);
                    }

                    else if(choice.equals("Q")){
                        System.out.println("Exiting....");
                        System.exit(0);
                    }

                    else
                        System.out.println("Please enter a valid input");


                }while(!choice.equals("B") && !choice.equals("M") && !choice.equals("Q"));
            }else if(flightcode_found==0){
                System.out.println("Please re-enter the details properly");
                cancel_ticket();
            }
        }

        catch (Exception e) {


            System.out.println("Sorry we encountered some unwanted error !");
            String choice = "";
            do {
                System.out.println("Enter 1. \"C\" to re-initiate cancellation\n2. \"M\" to go to main page\n3. \"Q\" to quit");
                choice = br.readLine().strip();

                if (choice.equals("C"))
                    cancel_ticket();

                else if (choice.equals("M")) {
                    // Do something
                    Main.Succesful_login(this);
                } else if (choice.equals("Q")) {
                    System.out.println("Exiting....");
                    System.exit(0);
                } else
                    System.out.println("Please enter valid input!");
            } while (!choice.equals("C") && !choice.equals("M") && !choice.equals("Q"));
        }
        Booking b = new Booking(travel_date, from, to);

        // Print the bookings that exist
        System.out.println("Bookings under " + this.username);
        for (Map.Entry<String, String> entry : passengers.entrySet())
            System.out.println(entry.getKey() + " : " + entry.getValue() );

        String choice = "";


        do{
            System.out.println("Enter: 1. \"C\" to continue\n2. \"M\" to go to main page\n3.\"Q\" to quit");
            choice = br.readLine().strip();


            if(choice.equals("C")){
                System.out.println("Enter number of seats to be cancelled: ");
                try{
                    int count = Integer.parseInt(br.readLine().strip());

                    if (count <= passengers.size()) {

                        String[] cancel_ppl = new String[count];
                        System.out.println("Enter names of passengers whose seats are to be cancelled: \n");

                        for (int i = 0; i < count; i++)
                            cancel_ppl[i] = br.readLine().strip();

                        for (Map.Entry<String, String> entry : passengers.entrySet()) {

                            if (!Arrays.asList(cancel_ppl).contains(entry.getKey()))
                                passengers.remove(entry.getKey());
                        }

                    } else {
                        System.out.println("You have booked only for " + Integer.toString(passengers.size()) + " passengers !!!!!!");
                        cancel_ticket();
                    }
                }catch (Exception e){
                    System.out.println(e.toString());
                    System.out.println("please be consistent with inputs !");
                    String choice_n = "";
                    do {
                        System.out.println("Enter 1. \"C\" to re-initiate cancellation\n2. \"M\" to go to main page\n3. \"Q\" to quit");
                        choice_n = br.readLine().strip();

                        if (choice_n.equals("R"))
                            cancel_ticket();

                        else if (choice_n.equals("M")) {
                            // Do something
                            Main.Succesful_login(this);
                        } else if (choice_n.equals("Q")) {
                            System.out.println("Exiting....");
                            System.exit(0);
                        } else
                            System.out.println("Please enter valid input!");
                    } while (!choice_n.equals("R") && !choice_n.equals("M") && !choice_n.equals("Q"));

                }

            }

            else if(choice.equals("M")){
                // Do something
                Main.Succesful_login(this);

            }

            else if(choice.equals("Q")){
                System.out.println("Exiting....");
                System.exit(0);
            }
            else
                System.out.println("Please enter a valid input");
        }while(!choice.equals("C") && !choice.equals("M") && !choice.equals("Q"));

        // Cancellation done


        b.seat_ops(code, "cancel", this, passengers);

        // After cancelling is done get age to refund amount according to age
        Integer amount = 0;
        Path path1 = Paths.get(current_dir + "\\flights.txt");
        List<String> fileContent1 = new ArrayList<>(Files.readAllLines(path1, StandardCharsets.UTF_8));
        for(int i=0;i<fileContent1.size();i++){
            if(fileContent1.get(i).contains(code)){
                String[] str = fileContent1.get(i).split(",");
                amount += Integer.parseInt(str[4]);
                break;
            }
        }


        float cumm_weight = 0;
        //Seats cancelled
        for (Map.Entry<String, String> entry : passengers.entrySet()) {
            System.out.println("Enter age of the Passenger : "+entry.getKey());
            String ages = br.readLine().strip();
            Integer age = Integer.parseInt(ages);
            float weight = (age<10)? (float)0.5 : (float)1.0;
            cumm_weight += weight;
            this.update_balance("cancel", amount, weight);


        }
        System.out.println("Seats cancelled sucesfully");

        String[] arr = get_flight_details(code);
        int amount1 = (int) cumm_weight*Integer.parseInt(arr[4]);
        update_flight_amount(code, -1*amount1);

        String choice2 = "";
        do {
            System.out.println("Enter 1. \"R\" to re-enter booking\n2. \"M\" to go to main page\n3. \"Q\" to quit");
            choice2 = br.readLine().strip();

            if (choice2.equals("R"))
                do_booking();

            else if (choice2.equals("M")) {
                // Do something
                Main.Succesful_login(this);
            } else if (choice2.equals("Q")) {
                System.out.println("Exiting....");
                System.exit(0);
            } else
                System.out.println("Please enter valid input!");
        } while (!choice2.equals("R") && !choice2.equals("M") && !choice2.equals("Q"));


    }

    static String[] get_flight_details(String code) throws IOException{
        String[] temp = null;

        Path p1 = Paths.get(current_dir + "\\flights.txt");
        List<String> fileContent1 = new ArrayList<>(Files.readAllLines(p1, StandardCharsets.UTF_8));
        for(int k = 0; k < fileContent1.size(); k++){

            temp = fileContent1.get(k).split(",");

            if(temp[1].equals(code)){

               return temp;

            }
        }
        return temp;
    }

    static void update_flight_amount(String code, int amount)throws Exception{

        Path path = Paths.get(current_dir + "\\flights.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {

            if (fileContent.get(i).contains(code)) {

                String airline = fileContent.get(i).split(",")[0];

                if(airline.equals("SpiceJet"))
                    SpiceJet.update_account(amount);

                else if(airline.equals("Indigo"))
                    Indigo.update_account(amount);

                else if(airline.equals("GoAir"))
                    GoAir.update_account(amount);

                else if(airline.equals("Vistara"))
                    Vistara.update_account(amount);



            }

        }

    }
        static int date_check(String s){

            String[] date = s.split("-");

            try{

                // Year check
                if(Integer.parseInt(date[2]) >= Year.now().getValue()){

                    // Month check
                    if(date[1].equals("04") ||date[1].equals("06") || date[1].equals("09") || date[1].equals("11")){

                        // Date check
                        if(Integer.parseInt(date[0]) >= 01 && Integer.parseInt(date[0]) <= 30)
                            return 1;
                    }

                    else if(date[1].equals("02")){

                        // Date check
                        if(Integer.parseInt(date[0]) >= 01 && Integer.parseInt(date[0]) <= 28)
                            return 1;
                    }

                    else if(date[1].equals("01") ||date[1].equals("03") || date[1].equals("05") || date[1].equals("07") || date[1].equals("08") || date[1].equals("10") || date[1].equals("12")){

                        // Date check
                        if(Integer.parseInt(date[0]) >= 01 && Integer.parseInt(date[0]) <= 31)
                            return 1;
                    }

                    else{

                        System.out.println("Enter valid date!");
                        return 0;
                    }
                }else{
                    System.out.println("Please enter a valid date");
                }
            }

            catch(Exception e){

                e.printStackTrace();
                return 0;
            }

            return 0;
        }


}

