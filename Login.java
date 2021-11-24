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

            log_file.append("Login failed");
            System.out.println("Login Failed!.....");
            String ch = cnsl.readLine("Do you want to continue or exit? Enter \"C\" to continue \"E\" to exit : ").strip();

            do{
                if(ch.equals("C"))
                    LOG_IN();

                else if(ch.equals("E"))
                    System.exit(0);
                else
                    System.out.println("Please give a valid input !");
            }while(!ch.equals("C") && !ch.equals("E"));
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
        String[] words = new String[6];

        // Reads file to check if credentials match
        FileReader fr = new FileReader(login_staffDB);
        BufferedReader br1 = new BufferedReader(fr);
        String str;
        int count = 0; // flag variable that indicate if login successful or not
        while ((str = br1.readLine()) != null) {

            // The file is comma separated and has 6 fields csv : Name,username,password,Airline,emp_ID,designation
            words = str.split(",");

            // Username matches with an entry
            if (words[1].equals(login[0])) {

                // Password matches
                if(words[2].equals(login[1])){

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

            Staff active = new Staff(words[0], words[1], words[2], words[3],words[4],words[5]);
//            System.out.println(words[3]);//current active customer

            log_file.append(active,"Logged in successfully");
            return active;


        } // Login failed
        else if(count ==1){
            System.out.println("Login Failed!.....");
            log_file.append("Login failed");
            String ch = cnsl.readLine("Do you want to continue or exit? Enter C to continue E to exit : ").strip();

            if(ch == "C")
                sLOG_IN();

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
    // Or you are a new staff/customer just set up your details
    public static void SIGN_UP(String str) throws Exception {

        System.out.println("Set up your account");
        String[] read = new String[6]; // String array to read file to check if entered credentials are unique

        // .strip() fn strips of the whitespaces on both sides of the string
        read[0] = cnsl.readLine("Enter your name : "); // Returns a string
        read[0] = read[0].strip();
        read[1] = cnsl.readLine("Enter your username : "); // Returns a string
        read[1] = read[1].strip();


        // If username is unique
        if (str.equals("Customer") && unique_user(read[1],str)) {

            read[2] = String.valueOf(cnsl.readPassword("Enter password : "));  // Returns char array which is converted to string
            String p = String.valueOf(cnsl.readPassword("Confirm password : "));

            if(p.equals(read[2])) {
                Customer c_new = new Customer(read[0], read[1], read[2], read[1] + "@gmail.com");

                // Integer balance;
                String read_int = cnsl.readLine("Enter amount of money would you like to set up for the account in Rs. : ");

                // Updates the balance of the customer object that's being set up
                c_new.update_balance(read[1], read[2], Integer.parseInt(read_int), "add");


                FileWriter fw = new FileWriter(login_customerDB, true);

                //login database : username, password, Customer name, balance
                fw.write(c_new.username + "," + read[2] + "," + read[0] + "," + read_int.toString() + "\n");
                fw.close();
                log_file.append(c_new, "new user Signed Up");
            }else{
                System.out.println("Password did not match.....");
                SIGN_UP("Customer");
            }

        }
        else if(str.equals("Staff") && unique_user(read[1],str)){
            read[2] = String.valueOf(cnsl.readPassword("Enter password : "));  // Returns char array which is converted to string
            String p = String.valueOf(cnsl.readPassword("Confirm password : "));

            if(p.equals(read[2])) {
                read[3] = cnsl.readLine("Enter your Airline Company: ");
                read[4] = cnsl.readLine("Enter your Employee ID :");
                read[5] = cnsl.readLine("Enter your designation: ");
                Staff s_new = new Staff(read[0], read[1], read[2], read[3], read[4], read[5]);
                String ID = s_new.getID();


                FileWriter fw = new FileWriter(login_staffDB, true);

                //login database : Name,username,password,Airline,emp_ID,designation
                fw.write(s_new.Name + "," + s_new.username + "," + read[2] + "," + read[3] + "," + ID + "," + read[5]);
                fw.close();
                log_file.append(s_new, "new user Signed Up");
            }else{
                System.out.println("Password did not match.....");
                SIGN_UP("Staff");

            }

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
    public static boolean unique_user(String username,String object) throws Exception {

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
                if (words[0].equals(username)) { //username is the first entry in the database
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
            String words[] = new String[6];
            while ((str = br1.readLine()) != null) {
                words = str.split(",");
                if (words[1].equals(username)) { //name is the first entry in the database
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
