import java.io.*;

public class Main {

    static Console cnsl = System.console();


    public static void go_to_login_page()throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        String[] arg1 = new String[2];
        String read = cnsl.readLine("Press \"M\" to go back to Login prompt or Press \"Q\" to quit: ").strip();

        if(read.equals("M")){

            main(arg1);

        }
        
        else if(read.equals("Q")){

            System.out.println("Exitting.....");
            System.exit(0);

        }
        
        else{

            System.out.println("Please give a valid input !");
            go_to_login_page();

        }

    }

    public static void main(String[] args) throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("*********************************************************************************");
        System.out.println("WELCOME TO AIRLINE RESERVATION SYSTEM !\n");
        System.out.println("Enter one of the following: ");
        System.out.println("1. \"L\" to Login\n2. \"S\" to Sign up\n3. \"Q\" to quit");
        String read = cnsl.readLine("Your choice: ").strip();

        if(read.equals("L")){

            // Clears terminal
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            System.out.println("*******************   LOG IN    *******************");
            Customer c = Login.LOG_IN();
//            c.check_balance();
//            System.out.println(c.username);
//            System.out.println(c.Name);
//            System.out.println(c.check_balance());

            // Clears terminal
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            System.out.println("**********************   DASHBOARD    **********************");

            System.out.println("Welcome Mr./Mrs."+c.Name);
            System.out.println("------------------------------------------------");
            System.out.println("Enter one of the following: ");
            System.out.println("1. \"C\" to change credentials\n2. \"U\" to update balance\n3. \"B\" to book ticket\n4. \"V\" to view ticket\n5. \"E\" to cancel ticket\n6. \"Q\" to quit");
            read = cnsl.readLine("Your choice: ").strip();

            if(read.equals("C")){
                
                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();
                
                c.change_credentials();
                read = cnsl.readLine("Do you want to quit? Type Y for yes and N for no: ").strip();

                if(read.equals("N")){

                    // Clears terminal
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    
                    System.out.println("*******************   LOG IN    *******************");
                    c = Login.LOG_IN();
                }

                else if(read.equals("Y")){

                    System.out.println("Exitting.......");
                    System.exit(0);

                }

                else{

                    System.out.println("Please give a valid input !");
                    go_to_login_page();

                }

            }
            
            else if(read.equals("U")){
                
                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();
                
                c.update_balance();
            }

            // Yet to define fns
            // else if(read.equals("B")){

            //     // Clears terminal
            //     System.out.print("\033[H\033[2J");
            //     System.out.flush();
                
            //     System.out.println("*********************   BOOKING    *********************");
            //     c.do_booking(); // Yet to define
            // }
            
            // else if(read.equals("V")){

            //     // Clears terminal
            //     System.out.print("\033[H\033[2J");
            //     System.out.flush();
                
            //     System.out.println("*******************   VIEW TICKET    *******************");
            //     c.view_ticket();  // Yet to define
            // }

            // else if(read.equals("E")){

            //     // Clears terminal
            //     System.out.print("\033[H\033[2J");
            //     System.out.flush();
                
            //     System.out.println("******************   CANCEL TICKET    ******************");
            //     c.cancel_ticket();  // Yet to define
            // }

            else if(read.equals("Q")){

                System.out.println("Exitting.......");
                System.exit(0);;
            }

        }
        
        else if(read.equals("S")){

            // Clears terminal
            System.out.print("\033[H\033[2J");
            System.out.flush();

            Login.SIGN_UP();
            System.out.println("User account set up done !");
            go_to_login_page();
            
        }
        
        else if(read.equals("Q")){

            System.out.println("Exitting.......");
            System.exit(0);

        }
        
        else{

            System.out.println("Please give a valid input !");
            go_to_login_page();

        }


    }
}

