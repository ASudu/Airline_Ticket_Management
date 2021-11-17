import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static LogFile log_file = new LogFile();

    public static void go_to_login_page()throws Exception{

        String[] arg1 = new String[2];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press M to go back to Login prompt or Press Q to quit");
        String read = reader.readLine();

        if(read.equals("M")){

            main(arg1);

        }

        else if(read.equals("Q")){

            System.exit(0);

        }

        else{

            System.out.println("Please give a valid input !");
            go_to_login_page();

        }

    }

    public static void main(String[] args) throws Exception{

        // Login welcome = new Login();
        // String[] arg1 = new String[2];
        log_file.append("Program Started");

        System.out.println("*********************************************************************************");
        System.out.println("WELCOME TO AIRLINE RESERVATION SYSTEM !\n");
        System.out.println("Press L to Login or Press S to Sign up");
        System.out.println("Press Q to quit anytime");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String read = reader.readLine();

        if(read.equals("L")){
            System.out.println("Login as a Customer(C) or Login as a Staff-Member(S)");
            read = reader.readLine();

            if(read.equals("C")) {
                Customer c = Login.LOG_IN();

                System.out.println("Welcome Mr./Mrs." + c.Name);
                System.out.println("------------------------");
                System.out.println("------------------------");
                System.out.println("Press C to change credentials U to update balance: ");
                // System.out.print("||Press U to update balance");
                read = reader.readLine();

                if (read.equals("C")) {

                    c.change_credentials();
                    System.out.println("Do you want to quit? Type Y for yes and N for no: ");
                    read = reader.readLine();

                    if (read.equals("N"))
                        c = Login.LOG_IN();

                    else if (read.equals("Y")) {
                        log_file.append(c, "User exited,Program exited");
                        System.exit(0);
                    } else {

                        System.out.println("Please give a valid input !");
                        go_to_login_page();

                    }

                } else if (read.equals("U")) {
                    c.update_balance();
                }
            }else if(read.equals("S")){
                Staff s = Login.sLOG_IN();
            }else{
                System.out.println("invalid input");
                go_to_login_page();
            }



        }

        else if(read.equals("S")){
            System.out.println("Sign up as a Customer(C), or sign up as a Staff(S)");
            if(read.equals("C")) {
                Login.SIGN_UP("Customer");
                System.out.println("User account set up done !");
                go_to_login_page();
            }
            else if(read.equals("S")){
                Login.SIGN_UP("Staff");
                System.out.println("User account set up done !");
                go_to_login_page();
            }else{
                System.out.println("Invalid input");
                go_to_login_page();
            }

        }

        else if(read.equals("Q")){

            log_file.append("Program terminated");
            System.exit(0);


        }

        else{

            System.out.println("Please give a valid input !");
            go_to_login_page();

        }


    }
}
