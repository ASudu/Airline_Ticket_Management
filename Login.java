
import java.io.*;

public class Login {
    static String current_dir = System.getProperty("user.dir");
    static File login_customerDB = new File(current_dir + "\\login_customerDB.txt"); //login database : username, password, Customer name, balance
    static Console cnsl = System.console();

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
            return active;


        } 
        
        // Login failed
        else if(count == 1){
            System.out.println("Login Failed!.....");
            String ch = cnsl.readLine("Do you want to continue or exit? Enter C to continue E to exit : ").strip();

            if(ch == "C")
                LOG_IN();
            
            else if(ch == "E")
                System.exit(0);
        }

        // User doesn't exist
        else{
            System.out.println("User doesn't exist! Please Sign Up.");
            Main.go_to_login_page();
        }
        fr.close();

        return new Customer();
    }

    //----------------------------------------------------------------------------------------------------------------//
    // Or you are a new customer just set up your details
    public static void SIGN_UP() throws Exception { 
     
        System.out.println("***********************   SIGN UP    ***********************");

        System.out.println("Set up your account");
        String[] read = new String[4]; // String array to read file to check if entered credentials are unique

        // .strip() fn strips of the whitespaces on both sides of the string
        read[0] = cnsl.readLine("Enter your name : ").strip(); // Returns a string
        read[1] = cnsl.readLine("Enter your username : ").strip(); // Returns a string

        // If username is unique
        if (unique_user(read[1])) {

            read[2] = String.valueOf(cnsl.readPassword("Enter password : "));  // Returns char array which is converted to string
            String p = String.valueOf(cnsl.readPassword("Confirm password : "));

            if(p.equals(read[2])){

                Customer c_new = new Customer(read[0], read[1], read[2], read[1] + "@gmail.com");

                // Integer balance;
                String read_int =cnsl.readLine("Enter amount of money would you like to set up for the account in Rs. : ");

                // Updates the balance of the customer object that's being set up
                c_new.update_balance(read[1], read[2], Integer.parseInt(read_int), "add");


                FileWriter fw = new FileWriter(login_customerDB, true);

                //login database : username, password, Customer name, balance
                fw.write(c_new.username + "," + read[2] + "," + read[0] + "," + read_int.toString() + '\n');
            fw.close();

            }

            else{

                System.out.println("Password did not match.....");
                SIGN_UP();

            }
            

        }
        
        // If username already exists
        else {

            System.out.println("Enter your details again, Existing username already exists !");
            // System.out.print("\033[H\033[2J");
            // System.out.flush();
            Main.go_to_login_page();

        }
    }

    //----------------------------------------------------------------------------------------------------------------//

    // Function to check if the username is unique or already exists
    public static boolean unique_user(String name) throws Exception {

        String current_dir = System.getProperty("user.dir");

        // login database : username, password, Customer name, balance
        File login_customerDB = new File(current_dir + "\\login_customerDB.txt");
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Commented above line since the local var br wasn't used an only the class br declared at start was used 

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

    //----------------------------------------------------------------------------------------------------------------//

}





