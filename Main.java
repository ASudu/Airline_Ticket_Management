import java.io.*;

public class Main {

    static Console cnsl = System.console();


    public static void go_to_login_page()throws Exception{

        // Clears terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

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
                Customer c = Login.LOG_IN();
                Succesful_login(c);
                

                // Clears terminal
                System.out.print("\033[H\033[2J");
                System.out.flush();
                
                System.out.println("**********************   DASHBOARD    **********************");

                
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
            
            else
                System.out.println("Please give a valid input !");

        }while(!read.equals("L") && !read.equals("S") && !read.equals("Q"));


    }
    public static void Succesful_login(Customer c)throws Exception{

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

                    // Yet to define fns
                    else if(read1.equals("B")){

                        // Clears terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        
                        System.out.println("*********************   BOOKING    *********************");
                        c.do_booking(); // Yet to define
                    }
                    
                    else if(read1.equals("V")){

                        // Clears terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        
                        System.out.println("*******************   VIEW TICKET    *******************");
                        c.view_ticket();  // Yet to define
                    }

                    else if(read1.equals("E")){

                        // Clears terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        
                        System.out.println("******************   CANCEL TICKET    ******************");
                        c.cancel_ticket();  // Yet to define
                    }

                    else if(read1.equals("Q")){

                        System.out.println("Exitting.......");
                        System.exit(0);
                    }

                    else
                        System.out.println("Please give a valid input !");
                }while(!read1.equals("C") && !read1.equals("U") && !read1.equals("B") && !read1.equals("V") && !read1.equals("E") && !read1.equals("Q"));
        
    }
}

