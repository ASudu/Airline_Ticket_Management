import java.io.*;

public class Main {

    static Console cnsl = System.console();


    // Function to re-direct users to main page or quit
    public static void go_to_login_page()throws Exception{

        String[] arg1 = new String[2];
        String read = "";

        do{

            read = cnsl.readLine("Press \"M\" to go back to Login prompt or Press \"Q\" to quit: ").strip();
            if(read.equals("M")){

                main(arg1);

            }
            
            else if(read.equals("Q")){

                System.out.println("Exitting.....");
                System.exit(0);

            }
            else
                System.out.println("Please give a valid input !");
        }while(!read.equals("M") && !read.equals("Q"));

    }

    public static void main(String[] args) throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Start point of the execution
        System.out.println("*********************************************************************************");
        System.out.println("WELCOME TO AIRLINE RESERVATION SYSTEM !\n");
        System.out.println("Enter one of the following: ");
        System.out.println("1. \"L\" to Login\n2. \"S\" to Sign up\n3. \"Q\" to quit");
        String read = "";


        do{

            read = cnsl.readLine("Your choice: ").strip();
            if(read.equals("L")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();


                System.out.println("*******************   LOG IN    *******************");
                System.out.println("Press \"C\" to Login from a  customer account  or Press \"S\" to Login from a staff account");
                String choice = cnsl.readLine("Your choice: ");
                
                do{
                    if(choice.equals("C")) {

                        Customer c = Login.LOG_IN();
                        Succesful_login(c);

                    }else if(choice.equals("S")){
                        Staff s = Login.sLOG_IN();
                        Staff_Login(s);

                    }else{
                        System.out.println("Please enter a valid input");
                        Main.main(args);
                    }
                }while(!choice.equals("C") && !choice.equals("S"));


            }

            else if(read.equals("S")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("*******************   SIGN UP    *******************");
                System.out.println("Press C to sign up as a customer or Press S to sign up as a staff account");
                String choice = cnsl.readLine("Your choice: ");

                if(choice.equals("C")){

                    Login.SIGN_UP("Customer");
                    System.out.println("User account set up done !");
                    go_to_login_page();

                }else if(choice.equals("S")){
                    Login.SIGN_UP("Staff");
                    System.out.println("User account set up done !");
                    go_to_login_page();
                }else{
                    System.out.println("Please enter a valid input");
                    Main.main(args);
                }

            }
            else if(read.equals("Q")){
                System.out.println("Exiting.......");
                System.exit(0);
            }
            else
                System.out.println("Please give a valid input !");
        }while(!read.equals("L") && !read.equals("S") && !read.equals("Q"));


    }

    // Called when login is successful and when redirect bak when error in user input
    public static void Succesful_login(Customer c)throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("**********************   DASHBOARD    **********************");
        System.out.println("Login successful!");
        System.out.println("Welcome Mr./Mrs."+c.Name);
        System.out.println("------------------------------------------------");
        System.out.println("Enter one of the following: ");
        System.out.println("1. \"C\" to change credentials\n2. \"U\" to update balance\n3. \"B\" to book ticket\n4. \"V\" to view ticket\n5. \"E\" to cancel ticket\n6. \"Q\" to quit");
        String read1 = "";

        do{
            read1 = cnsl.readLine("Your choice: ").strip();

            if(read1.equals("C")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();

                c.change_credentials();
                String read2 = "";

                do{

                    read2 = cnsl.readLine("Enter: 1. \"L\" to login\n2. \"Q\" to quit");
                    if(read2.equals("L")){

                        // Clears terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("*******************   LOG IN    *******************");
                        c = Login.LOG_IN();
                    }

                    else if(read2.equals("Q")){

                        System.out.println("Exitting.......");
                        System.exit(0);

                    }

                    else
                        System.out.println("Please give a valid input !");
                }while(!read2.equals("L") && !read2.equals("Q"));

            }

            else if(read1.equals("U")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();

                c.update_balance();
            }

            else if(read1.equals("B")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("*********************   BOOKING    *********************");
                c.do_booking();
            }

            else if(read1.equals("V")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("*******************   VIEW TICKET    *******************");
                c.view_ticket();
            }

            else if(read1.equals("E")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("******************   CANCEL TICKET    ******************");
                c.cancel_ticket();
            }

            else if(read1.equals("Q")){

                System.out.println("Exiting.......");
                System.exit(0);
            }

            else
                System.out.println("Please give a valid input !");
        }while(!read1.equals("C") && !read1.equals("U") && !read1.equals("B") && !read1.equals("V") && !read1.equals("E") && !read1.equals("Q"));

    }

    // Called when login is successful and when redirect bak when error in staff input
    public static void Staff_Login(Staff s)throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("**********************   DASHBOARD    **********************");
        System.out.println("Login successful!");
        
        System.out.println("Welcome Mr./Mrs."+s.Name);
        System.out.println("------------------------------------------------");
        System.out.println("Enter one of the following: ");
        System.out.println("1. \"C\" to change credentials\n2. \"B\" to generate Boarding pass\n3. \"U\" to update flight status\n4. \"Q\" to quit");
        String read1 = "";

        do{
            read1 = cnsl.readLine("Your choice: ").strip();

            if(read1.equals("C")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();

                s.change_StaffCredentials();
                String read2 = "";

                do{

                    read2 = cnsl.readLine("Enter: 1. \"L\" to login\n2. \"Q\" to quit");
                    if(read2.equals("L")){

                        // Clears terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("*******************   LOG IN    *******************");
                        s = Login.sLOG_IN();
                    }

                    else if(read2.equals("Q")){

                        System.out.println("Exiting.......");
                        System.exit(0);

                    }

                    else
                        System.out.println("Please give a valid input !");
                }while(!read2.equals("L") && !read2.equals("Q"));

            }

            else if(read1.equals("B")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();

                s.generate_boarding_pass();
            }

            // Yet to define fns
            else if(read1.equals("U")){

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();


                System.out.println("*********************   UPDATE FLIGHT STATUS    *********************");
                s.update_flight_status();
            }
            else if(read1.equals("Q")){

                System.out.println("Exiting.......");
                System.exit(0);
            }

            else
                System.out.println("Please give a valid input !");
        }while(!read1.equals("C") && !read1.equals("B") && !read1.equals("U") && !read1.equals("Q"));

    }

}

