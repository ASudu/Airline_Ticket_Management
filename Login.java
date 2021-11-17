import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Login {
    static String current_dir = System.getProperty("user.dir");
    static File login_customerDB = new File(current_dir + "\\login_customerDB.txt"); //login database : username, password, Customer name, balance
    static Console cnsl = System.console();
    static LogFile log_file = new LogFile();
    static File login_staffDB = new File(current_dir + "\\login_staffDB.txt");

    //----------------------------------------------------------------------------------------------------------------//
    // Either Log in if you are an existing user
    public static Customer LOG_IN() throws Exception {


        String[] login = new String[2];

        // Reads the user credentials as user input
        login[0] = cnsl.readLine("Enter your username : ").strip(); // Returns a string
        login[1] = String.valueOf(cnsl.readPassword("Enter password : "));  // Returns char array which is converted to string
        String[] words = new String[4];

        // Reads file to check if credentials match
        FileReader fr = new FileReader(login_customerDB);
        BufferedReader br1 = new BufferedReader(fr);
        String str;
        int count = 0; // flag variable that indicate if login successful or not
        while ((str = br1.readLine()) != null) {

            // The file is comma separated and has 4 fields username, psswd, name, balance
            words = str.split(",");

            // Username matches with an entry
            if (words[0].equals(login[0])) {

                // Password matches
                if(words[1].equals(login[1])){

                    count = 2;
                    break;

                }

                else{

                    count = 1;
                    break;
                }

            }

        }

        br1.close();  // Close reader object

        // Login successful
        if (count == 2) {

            System.out.println("Login Successful!");

            Customer active = new Customer(words[2], words[0], words[1], words[2] + "@gmail.com");
//            System.out.println(words[3]);//current active customer
            active.update_balance(words[0], words[1], Integer.parseInt(words[3]), "add");
            log_file.append(active,"Logged in successfully");
            return active;


        }

        // Login failed
        else if(count == 1){
            System.out.println("Login Failed!.....");
            log_file.append("Login failed");
            String ch = cnsl.readLine("Do you want to continue or exit? Enter C to continue E to exit : ").strip();

            if(ch == "C")
                LOG_IN();

            else if(ch == "E")
                System.exit(0);
        }

        // User doesn't exist
        else{
            System.out.println("User doesn't exist! Please Sign Up.");
            log_file.append("Login failed_User doesn't exist");
            Main.go_to_login_page();
        }
        fr.close();

        return new Customer();
    }
    public static Staff sLOG_IN() throws Exception {


        String[] login = new String[2];

        // Reads the user credentials as user input
        login[0] = cnsl.readLine("Enter your username : ").strip(); // Returns a string
        login[1] = String.valueOf(cnsl.readPassword("Enter password : "));  // Returns char array which is converted to string
        String[] words = new String[5];

        // Reads file to check if credentials match
        FileReader fr = new FileReader(login_staffDB);
        BufferedReader br1 = new BufferedReader(fr);
        String str;
        int count = 0; // flag variable that indicate if login successful or not
        while ((str = br1.readLine()) != null) {

            // The file is comma separated and has 4 fields username, psswd, name, balance
            words = str.split(",");

            // Username matches with an entry
            if (words[0].equals(login[0])) {

                // Password matches
                if(words[1].equals(login[1])){

                    count = 2;
                    break;

                }

                else{

                    count = 1;
                    break;
                }

            }

        }

        br1.close();  // Close reader object

        // Login successful
        if (count == 2) {

            System.out.println("Login Successful!");

            Staff active = new Staff(words[2], words[0], words[1], words[3]);
//            System.out.println(words[3]);//current active customer

            log_file.append(active,"Logged in successfully");
            return active;

            
        } // Login failed
        else if(count ==1){
            System.out.println("Login Failed!.....");
            log_file.append("Login failed");
            String ch = cnsl.readLine("Do you want to continue or exit? Enter C to continue E to exit : ").strip();

            if(ch == "C")
                LOG_IN();

            else if(ch == "E")
                System.exit(0);
        }

       
        else {
            System.out.println("User doesn't exist! Please Sign Up.");
            log_file.append("Login failed_User doesn't exist");
            Main.go_to_login_page();
        }
        fr.close();

        return new Staff();
    }


    //----------------------------------------------------------------------------------------------------------------//
    // Or you are a new customer just set up your details
    public static void SIGN_UP(String str) throws Exception {
        System.out.println("Set up your account");
        String[] read = new String[4]; // String array to read file to check if entered credentials are unique

        // .strip() fn strips of the whitespaces on both sides of the string
        read[0] = cnsl.readLine("Enter your name : "); // Returns a string
        read[0] = read[0].strip();
        read[1] = cnsl.readLine("Enter your username : "); // Returns a string
        read[1] = read[1].strip();


        // If username is unique
        if (str.equals("Customer") && unique_user(read[1],str)) {

            read[2] = String.valueOf(cnsl.readPassword("Enter password : "));  // Returns char array which is converted to string
            Customer c_new = new Customer(read[0], read[1], read[2], read[1] + "@gmail.com");

            // Integer balance;
            String read_int =cnsl.readLine("Enter amount of money would you like to set up for the account in Rs. : ");

            // Updates the balance of the customer object that's being set up
            c_new.update_balance(read[1], read[2], Integer.parseInt(read_int), "add");


            FileWriter fw = new FileWriter(login_customerDB, true);

            //login database : username, password, Customer name, balance
            fw.write(c_new.username + "," + read[2] + "," + read[0] + "," + read_int.toString() + '\n');
            fw.close();
            log_file.append(c_new,"new user Signed Up");

        }
        else if(str.equals("Staff") && unique_user(read[1],str)){
            read[2] = String.valueOf(cnsl.readPassword("Enter password : "));  // Returns char array which is converted to string
            read[3] = cnsl.readLine("Enter your Airline Company: ");
            Staff s_new = new Staff(read[0], read[1], read[2], read[3]);

            // Integer balance;


            Integer ID = s_new.getID();

            // Updates the balance of the customer object that's being set up



            FileWriter fw = new FileWriter(login_staffDB, true);

            //login database : username, password, Staff name, Airline, ID
            fw.write(s_new.username + "," + read[2] + "," + read[0] + "," +read[3]+","+ID.toString());
            fw.close();
            log_file.append(s_new,"new user Signed Up");

        }

        // If username already exists
        else {

            System.out.println("Enter your details again, Existing username already exists !");
            // System.out.print("\033[H\033[2J");
            // System.out.flush();
            Main.go_to_login_page();
            log_file.append("Failed attempt at Signing up");

        }
    }

    //----------------------------------------------------------------------------------------------------------------//

    // Function to check if the username is unique or already exists
    public static boolean unique_user(String name,String object) throws Exception {

        String current_dir = System.getProperty("user.dir");

        // login customer_database : username, password, Customer name, balance
        File login_customerDB = new File(current_dir + "\\login_customerDB.txt");

        // login staff_database : username, password, Staff name, airline, ID
        File login_staffDB = new File(current_dir + "\\login_staffDB.txt");

        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Commented above line since the local var br wasn't used an only the class br declared at start was used
        
        if(object.equals("Customer")){

        FileReader fr = new FileReader(login_customerDB);
        BufferedReader br1 = new BufferedReader(fr);
        String str;
        int count = 0;
        String words[] = new String[4];
        while ((str = br1.readLine()) != null) {
            words = str.split(",");
            if (words[0].equals(name)) {
                count++;
                break;
            }

        }

        br1.close();  // Close reader object

        if (count != 0) return false;
        else return true;
        }
        else if(object.equals("Staff")){
            FileReader fr = new FileReader(login_staffDB);
            BufferedReader br1 = new BufferedReader(fr);
            String str;
            int count = 0;
            String words[] = new String[5];
            while ((str = br1.readLine()) != null) {
                words = str.split(",");
                if (words[0].equals(name)) {
                    count++;
                    break;
                }

            }

            br1.close();  // Close reader object

            if (count != 0) return false;
            else return true;
        }
        else return false;
    }

    //----------------------------------------------------------------------------------------------------------------//

}

class Customer implements Serializable{

    static String current_dir = System.getProperty("user.dir");
    static File login_customerDB = new File(current_dir + "\\login_customerDB.txt");
    static LogFile log_file = new LogFile();
    static Console cnsl = System.console();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    //-------------------------------------------------Variables------------------------------------------------------//
    public String Name;
    public String username;
    private String gender;
    private String password;
    private String address;
    private Integer Balance = 0;
    private int uniqueCode;


    //----------------------------------Constructor to create Customer object-----------------------------------------//
    Customer(){

    }

    // Constructor to set up the customer's account
    Customer(String name, String username, String password, String address){
        this.Name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.uniqueCode = hashCode();
    }

    //--------------------------------------------------Methods-------------------------------------------------------//

    // An option to change the user credentials
    public void change_credentials()throws Exception{

        String n, u, p, ad;
        n = cnsl.readLine("Enter your name : ").strip();
        u = cnsl.readLine("Enter your username : ").strip();

        if(Login.unique_user(u,"Customer")) {
            log_file.append(this,"Attempted changing credentials");
            p = String.valueOf(cnsl.readPassword("Enter password : "));
            ad = u + "@gmail.com";
            update_logDB(u, p, n, Balance.toString());
            this.Name = n;
            this.username = u;
            this.password = p;
            this.address = ad;
            log_file.append(this,"New credentials set-up done");

        }

        else {

            System.out.println("Username already exists!");
            log_file.append(this,"Failed attempt at changing credentials");
            change_credentials();

        }


    }

    // Update Balance to be called during setting up customer after login
    public int update_balance(String username, String password,Integer amount,String choice)throws Exception {
        if (this.username.equals(username) && this.password.equals(password)) {
            if (choice.equals("deduct")) {
//                System.out.println("Previous Balance Rs."+Balance);
                this.Balance -= amount;
//                System.out.println("Current Balance Rs."+Balance);
                return 1;
            } else if (choice.equals("add")) {
//                System.out.println("Previous Balance Rs."+Balance);
                this.Balance += amount;
//                System.out.println("Current Balance Rs."+Balance);
                return 1;
            }
        }
        log_file.append("Failed Attempt at updating balance,initialization error");
        return 0; //whenever it returns 0 need to call this function again.
    }

    // Update balance to be called during Actual payments
    public int update_balance() throws Exception{
        String username;
        String password;
        Integer amount;
        String choice;
        username = cnsl.readLine("Enter your username : ").strip();
        password = String.valueOf(cnsl.readPassword("Enter password : "));

        if (this.username.equals(username) && this.password.equals(password)) {

            choice = cnsl.readLine("Want to add money then type add or deduct : ").strip();

            if (choice.equals("deduct")) {

                System.out.println("Previous Balance Rs."+Balance);
                amount = Integer.parseInt(cnsl.readLine("Enter amount to withdraw : ").strip());
                Integer temp = Balance - amount;
                update_logDB(username,password,Name,temp.toString());
                this.Balance -= amount;
                System.out.println("Current Balance Rs."+Balance);
                log_file.append(this,"deducted"+amount);
                return 1;


            }

            else if (choice.equals("add")) {

                System.out.println("Previous Balance Rs."+Balance);
                amount = Integer.parseInt(cnsl.readLine("Enter amount to add : ").strip());
                Integer temp = Balance + amount;
                update_logDB(username,password,Name,temp.toString());
                this.Balance += amount;
                System.out.println("Current Balance Rs."+Balance);
                log_file.append(this,"added"+amount);
                return 1;

            }

            else{

                System.out.println("Invalid choice...Type \"add\" or \"deduct\"!");
                update_balance();

            }

        }

        else{

            System.out.println("Invalid credentials....");
            log_file.append(this,"Failed attempt at updating balance");
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
    public int make_payment(String username,String password,Integer amount)throws Exception{

        if (this.username.equals(username) && this.password.equals(password)){

            System.out.println("Previous Balance Rs."+Balance);
            update_balance(username,password,amount,"deduct");
            System.out.println("Your current balance is: Rs."+Balance);
            log_file.append(this,"Transaction initiated"+amount);
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


}



