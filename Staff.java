import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
// import java.util.*;

public class Staff implements Serializable{
    static String current_dir = System.getProperty("user.dir");
    static File login_staffDB = new File(current_dir + "\\login_staffDB.txt");

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public String Name;
    public String username;
//    private String gender;
    private String password;
    public String Airline;
    private String emp_ID;
    public String designation;

    Staff(){ //Null constructor
    }

    public Staff(String Name, String username, String password, String Airline, String emp_ID, String designation) {
        this.Name = Name;
        this.username = username;
//        this.gender = gender;
        this.password = password;
        this.Airline = Airline;
        this.emp_ID = emp_ID;
        this.designation = designation;
    }

    public String getID(){
        System.out.println("Your ID is "+this.emp_ID);
        return this.emp_ID;
    }

    public void update_slogDB(String n,String u,String p,String a,String id,String de)throws Exception{

        // First find the user by username :(unique identity)
        Path path = Paths.get(current_dir + "\\login_staffDB.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {

            if (fileContent.get(i).equals(Name+","+username+","+password+","+Airline+","+emp_ID+","+designation)) {

                fileContent.set(i, n+","+u+","+p+","+a+","+id+","+de);
                break;

            }

        }

        Files.write(path, fileContent, StandardCharsets.UTF_8);
    }

    public void change_StaffCredentials()throws Exception{

        System.out.println("***************   CHANGE CREDENTIALS    ***************");

        String n, u, p;
        System.out.println("Enter your new name : ");
        n = br.readLine().strip();
        System.out.println("Enter your new username : ");
        u = br.readLine().strip();

        if(Login.unique_user(u,"Staff")) {
            System.out.println("Enter password : ");
            p = String.valueOf(br.readLine());
            System.out.println("Confirm password : ");
            String p1 = String.valueOf(br.readLine());

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



    public void generate_boarding_pass()throws Exception{
        System.out.println("Enter username under which the booking exists");
        String username = br.readLine();
        System.out.println("Enter flightcode :");
        String flightcode = br.readLine();

        BoardingPass bp_Staff = new BoardingPass(username,flightcode);
        int verified = Checkin.checkin(username, flightcode);

        if(verified >= 0){

            int bag_weight = verified; // If verification successful function returns bag weight

            // Bag weight hard upper limit is 40kgs (assumed)
            if(bag_weight < 40){
                int bag_charge = (bag_weight > 25)? (bag_weight - 25)*100 : 0;

                // Notify about weight limit exceeded
                if(bag_charge >0)
                    System.out.println("Your luggage exceeds limit of 25kgs by " + Integer.toString(bag_weight - 25) + " you'll be charged extra Rs." + Integer.toString(bag_charge));
                int flag = bp_Staff.display_boarding_pass(bag_weight, bag_charge);

                if(flag==0){
                    System.out.println("Error in generating boarding pass, most probably no booking exists");
                    Main.Staff_Login(this);
                }

                else if(flag==1){
                    System.out.println("Boarding pass generated Successfully");
                    Main.Staff_Login(this);
                }
            }

            else{
                System.out.println("Too heavy to allow boarding....");
                System.out.println("Reduce baggage weight and try again.....");
                generate_boarding_pass();
            }

        }
        System.out.println("Verification was not successful !");
        Main.Staff_Login(this);


    }

    public void update_flight_status()throws Exception{

        if(this.designation.equals("Ground Staff")){
            System.out.println("Enter flight code :");
            String flight_code = br.readLine();
            Airline a1 = new Airline();
            String update_field = "status";
            System.out.println("Enter the  new status :");
            String new_update = br.readLine();
            a1.update_flight_details(this,flight_code,update_field,new_update);
        }else if(this.designation.equals("Senior Executive")){
            System.out.println("Enter flight code");
            String flight_code = br.readLine();
            Airline a1 = new Airline();
            System.out.println("Choose among the following");
            System.out.println("1. \"from\" to change departure location\n2. \"to\" to update arrival location\n3. \"status\" to update flight status\n4. \"fare\" to update new fare\n5. \"time\" to update time");
            String update_field = br.readLine().strip().toLowerCase();
            System.out.println("Enter updated value for the chosen field");
            String new_update = br.readLine().strip();
            a1.update_flight_details(this,flight_code,update_field,new_update);

        }else if(this.designation.equals("Sales Head")){
            System.out.println("Choose among the following: ");
            System.out.println("1. \"A\" to add a single flight\n2. \"B\" to add multiple flights in one go\n");
            String choice = br.readLine();
            if(choice.equals("A")){

                String from,to,flight_code,time;
                System.out.println("Enter departure City :");
                from = br.readLine();
                System.out.println("Enter arrival City :");
                to = br.readLine();
                System.out.println("Enter flight code : ");
                flight_code = br.readLine();
                System.out.println("Enter departure time in HHMM 24 hrs Format : ");
                String depart_time = br.readLine();
                System.out.println("Enter arrival time in HHMM 24 hrs Format : ");
                String arrival_time = br.readLine();
                time = depart_time+" - "+arrival_time;
                System.out.println("Enter the fare per head for the flight: ");
                Integer fare = Integer.parseInt(br.readLine());
                Flight temp = new Flight();
                temp.setAirline(this);
                Flight f = new Flight(from,to,fare,flight_code,time,temp.getAirline());
                Airline a1 = new Airline();
                a1.add_Flights(this,f);
            }else if(choice.equals("B")){
                System.out.println("Enter number of flights that you would like to add");
                int n = Integer.parseInt(br.readLine());
                ArrayList<Flight> flights = new ArrayList<Flight>();
                for(int i=0;i<n;i++){
                    String from,to,flight_code,time;
                    System.out.println("Enter departure City :");
                    from = br.readLine();
                    System.out.println("Enter arrival City :");
                    to = br.readLine();
                    System.out.println("Enter flight code : ");
                    flight_code = br.readLine();
                    System.out.println("Enter departure time in HHMM 24 hrs Format : ");
                    String depart_time = br.readLine();
                    System.out.println("Enter arrival time in HHMM 24 hrs Format : ");
                    String arrival_time = br.readLine();
                    time = depart_time+" - "+arrival_time;
                    System.out.println("Enter the fare per head for the flight: ");
                    Integer fare = Integer.parseInt(br.readLine());
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
        String choice = "";
        do{
            System.out.println("Press Q to quit or M to go back to Main Page ");
            choice = br.readLine();
            if(choice.equals("Q")){
                System.exit(0);
            }else if(choice.equals("M")){
                Main.Staff_Login(this);
            }else{
                System.out.println("Please enter valid input");
            }

        }while(!choice.equals("Q")&&!choice.equals("M"));


    }







}

