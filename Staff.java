import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Staff implements Serializable{
    static String current_dir = System.getProperty("user.dir");
    static File login_staffDB = new File(current_dir + "\\login_staffDB.txt");

    static Console cnsl1 = System.console();


    public String Name;
    public String username;
    private String password;
    public String Airline;
    private String emp_ID;
    public String designation;

    // Default constructor
    Staff(){
    }

    // Parameterized constructor
    public Staff(String Name, String username, String password, String Airline, String emp_ID, String designation) {
        this.Name = Name;
        this.username = username;
        this.password = password;
        this.Airline = Airline;
        this.emp_ID = emp_ID;
        this.designation = designation;
    }

    // Get employee ID of the staff
    public String getID(){
        System.out.println("Your ID is "+this.emp_ID);
        return this.emp_ID;
    }

    // Updates or adds the credentials login file for staff
    public void update_slogDB(String n,String u,String p,String a,String id,String de)throws Exception{

        // First find the user by username :(unique identity)
        Path path = Paths.get(current_dir + "\\login_customerDB.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {

            if (fileContent.get(i).equals(Name+","+username+","+password+","+Airline+","+emp_ID+","+designation)) {

                fileContent.set(i, n+","+u+","+p+","+a+","+id+","+de);
                break;

            }

        }

        Files.write(path, fileContent, StandardCharsets.UTF_8);
    }

    // Option for staff to change credentials
    public void change_StaffCredentials()throws Exception{

        System.out.println("***************   CHANGE CREDENTIALS    ***************");

        String n, u, p;
        Console cnsl1 = System.console();
        n = cnsl1.readLine("Enter your new name : ").strip();
        u = cnsl1.readLine("Enter your new username : ").strip();

        if(Login.unique_user(u,"Staff")) {

            p = String.valueOf(cnsl1.readPassword("Enter password : "));
            String p1 = String.valueOf(cnsl1.readPassword("Confirm password : "));

            if(p1.equals(p)){

                update_slogDB(n,u,p,Airline,emp_ID,designation);
                this.Name = n;
                this.username = u;
                this.password = p;

            }

            else{

                System.out.println("Password did not match");
                change_StaffCredentials();

            }


        }

        else {

            System.out.println("Username already exists!");
            change_StaffCredentials();

        }


    }


    // Generate boarding pass for a specific user
    public void generate_boarding_pass()throws Exception{

        String username = cnsl1.readLine("Enter username under which the booking exists");
        String flightcode = cnsl1.readLine("Enter flightcode :");

        BoardingPass bp_Staff = new BoardingPass(username,flightcode);
        int flag = bp_Staff.display_boarding_pass();

        if(flag==0){
            System.out.println("Error in generating boarding pass, most probably no booking exists");
            Main.Staff_Login(this);
        }else if(flag==1){
            System.out.println("Boarding pass generated Successfully");
            Main.Staff_Login(this);
        }

    }

    // Update flight status and other details like adding flights
    public void update_flight_status()throws Exception{

        if(this.designation.equals("Ground Staff")){
            String flight_code = cnsl1.readLine("Enter flight code :");
            Airline a1 = new Airline();
            String update_field = "status";
            String new_update = cnsl1.readLine("Enter the  new status :");
            a1.update_flight_details(this,flight_code,update_field,new_update);
        }
        
        else if(this.designation.equals("Senior Executive")){
            String flight_code = cnsl1.readLine("Enter flight code");
            Airline a1 = new Airline();
            System.out.println("Choose among the following");
            String update_field = cnsl1.readLine("1. \"from\" to change departure location\n2. \"to\" to update arrival location\n3. \"status\" to update flight status\n4. \"fare\" to update new fare\n5. \"time\" to update time");
            String new_update = cnsl1.readLine("Enter updated value for the chosen field");
            a1.update_flight_details(this,flight_code,update_field,new_update);

        }
        
        else if(this.designation.equals("Sales Head")){
            System.out.println("Choose among the following: ");
            String choice = cnsl1.readLine("1. \"A\" to add a single flight\n2. \"B\" to add multiple flights in one go\n");

            if(choice.equals("A")){

                String from,to,flight_code,time;
                from = cnsl1.readLine("Enter departure City :");
                to = cnsl1.readLine("Enter arrival City :");
                flight_code = cnsl1.readLine("Enter flight code : ");
                String depart_time = cnsl1.readLine("Enter departure time in HHMM 24 hrs Format : ");
                String arrival_time = cnsl1.readLine("Enter arrival time in HHMM 24 hrs Format : ");
                time = depart_time+" - "+arrival_time;
                Integer fare = Integer.parseInt(cnsl1.readLine("Enter the fare per head for the flight: "));
                Flight temp = new Flight();
                temp.setAirline(this);
                Flight f = new Flight(from,to,fare,flight_code,time,temp.getAirline());
                Airline a1 = new Airline();
                a1.add_Flights(this,f);
            }
            
            else if(choice.equals("B")){
                int n = Integer.parseInt(cnsl1.readLine("Enter number of flights that you would like to add"));
                ArrayList<Flight> flights = new ArrayList<Flight>();
                for(int i=0;i<n;i++){
                    String from,to,flight_code,time;
                    from = cnsl1.readLine("Enter departure City :");
                    to = cnsl1.readLine("Enter arrival City :");
                    flight_code = cnsl1.readLine("Enter flight code : ");
                    String depart_time = cnsl1.readLine("Enter departure time in HHMM 24 hrs Format : ");
                    String arrival_time = cnsl1.readLine("Enter arrival time in HHMM 24 hrs Format : ");
                    time = depart_time+" - "+arrival_time;
                    Integer fare = Integer.parseInt(cnsl1.readLine("Enter the fare per head for the flight: "));
                    Flight temp = new Flight();
                    temp.setAirline(this);
                    Flight f = new Flight(from,to,fare,flight_code,time,temp.getAirline());
                    flights.add(f);
                }
                Airline a1 = new Airline();
                a1.add_Flights(this,flights);
            }
        }

        else{
            System.out.println("You do not have access to this functionality !");
            Main.Staff_Login(this);
        }

    }







}

