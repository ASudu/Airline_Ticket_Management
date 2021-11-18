import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class Flight{

    static String current_dir = System.getProperty("user.dir");
    final int total_seats = 180; // 30 rows and 3+3 in each row

    // The read and write operations are done by Staff, Booking and Boarding Pass class
    private String from; // read and write
    private String to; // read and write
    private int fare; // read and write
    private String flight_code;  // read only
    private String Airline;  // read only
    // private HashSet<Customer>  customers_booked; // read and write
    private int customers_booked;
    private int free_seats; // read only
    private String flight_status; //  read and write ; true if functional else false


    // Default constructor
    public Flight() {

        // this.from = City.home_city; // Implement City class
        this.customers_booked = 0;
        this.free_seats = total_seats;
        this.flight_status = "true";

    }



    // Parameterized constructor
    public Flight(String from, String to, int fare, String flight_code, String Airline) {
        this.from = from;
        this.to = to;
        this.fare = fare;
        this.flight_code = flight_code;
        this.Airline = Airline;
        this.customers_booked = 0;
        this.free_seats = total_seats;
        this.flight_status = "true";
    }


    //--------------------------------------------Getters and setters---------------------------------------------//
    public int getTotal_seats() {
        return this.total_seats;
    }


    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getFare() {
        return this.fare;
    }

    public void update_Fare(int fare) {
        this.fare = fare;
    }

    public String getFlight_code() {
        return this.flight_code;
    }

    public String getAirline() {
        return this.Airline;
    }

    public int getCustomers_booked() {
        return this.customers_booked;
    }

    
    // public void update_Customers_booked(HashSet<Customer> customers_booked) {
    //     this.customers_booked = customers_booked;
    // }

    // public void update_Customers_booked(Customer c) {
    //     this.customers_booked.add(c);
    // }

    public int getFree_seats() {
        return this.free_seats;
    }

    public String getFlight_status() {
        return this.flight_status;
    }

    // public void setFlight_status(boolean flight_status) {
    //     this.flight_status = flight_status;
    // }
    //------------------------------------------------------------------------------------------------------------//

    public void update_flights(String airline, String code, String from, String to, String fare, String free_seats,String status) throws Exception{

        // First find the flight by flight_code :(unique identity)
        Path path = Paths.get(current_dir + "flights.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {

            if (fileContent.get(i).equals(this.Airline+","+this.flight_code+","+this.from+","+this.to+","+Integer.toString(this.fare)+","+Integer.toString(this.free_seats)+","+flight_status)) {

                fileContent.set(i, airline+","+code+","+from+","+to+","+fare+","+free_seats+","+status);
                break;

            }

        }

        // Update file content
        Files.write(path, fileContent, StandardCharsets.UTF_8);

    }

   
}

class Airline{

    ArrayList<Flight>  flights =  new ArrayList<Flight>(); // a set consisting of flights under the airlines
    HashSet<String>  places =  new HashSet<String>(); // list of cities or that airline is connected to
    ArrayList<Staff> staffs = new ArrayList<Staff>(); // List of employed staffs (Implement Staff class)
    HashMap<String,Integer> customers;
    String flights_file = "flights.txt";
    BufferedWriter br1 = null;
    BufferedReader br = null;


    //--------------------------------------------Getters and setters---------------------------------------------//
    public ArrayList<Flight> getFlights() {
        return this.flights;
    }

    // Only Sales Head of the Airline can add flights into the system
    public void add_Flights(Staff s,ArrayList<Flight> flights) {
        if(s.designation.equals("Sales Head")){
            // this.flights.addAll(flights);

            if(s.Airline.equals(flights.get(0).getAirline())){

                try {
                    FileWriter fw = new FileWriter(flights_file, true);
                    br1 = new BufferedWriter(fw);
    
                    for(Flight flight_obj : flights){
    
                        //  For testing
                        System.out.println(flight_obj.getAirline() + "," + flight_obj.getFlight_code() + "," + flight_obj.getFrom() + "," 
                        + flight_obj.getTo() + "," + Integer.toString(flight_obj.getFare()) + "," 
                        + Integer.toString(flight_obj.getFree_seats()) + "," + flight_obj.getFlight_status() + '\n');
                        
                        // fw.write(flight_obj.getAirline() + "," + flight_obj.getFlight_code() + "," + flight_obj.getFrom() + "," 
                        // + flight_obj.getTo() + "," + Integer.toString(flight_obj.getFare()) + "," 
                        // + Integer.toString(flight_obj.getFree_seats()) + "," + flight_obj.getFlight_status() + '\n');
    
                    }
    
                    fw.close();
    
    
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }

            else{
                System.out.println("You can't add flights of different airlines!....");
            }

           
        }

        else{
            System.out.println("Only Sales Head can add flights!");
        }
        
    }

    // Only Sales Head of the Airline can add flights into the system
    public void add_Flights(Staff s,Flight flight) {
        if(s.designation.equals("Sales Head")){
            
            if(s.Airline.equals(flight.getAirline())){

                try {
                    FileWriter fw = new FileWriter(flights_file);
                    br1 = new BufferedWriter(fw);
    
                    // For testing
                    System.out.println(flight.getAirline() + "," + flight.getFlight_code() + "," + flight.getFrom() + "," 
                        + flight.getTo() + "," + Integer.toString(flight.getFare()) + "," 
                        + Integer.toString(flight.getFree_seats()) + "," + flight.getFlight_status() + '\n');
                    
                    // fw.write(flight.getAirline() + "," + flight.getFlight_code() + "," + flight.getFrom() + "," 
                    // + flight.getTo() + "," + Integer.toString(flight.getFare()) + "," 
                    // + Integer.toString(flight.getFree_seats()) + "," + flight.getFlight_status() + '\n');
                
                    fw.close();
    
    
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }

            else{
                System.out.println("You can't add flights of different airlines!....");
            }

            
        }

        else{
            System.out.println("Only Sales Head can add flights!");
        }
        
    }

    public HashSet<String> getPlaces() {
        return this.places;
    }

    public void add_Places(HashSet<String> places) {
        this.places.addAll(places);
    }

    public void add_Places(String place) {
        this.places.add(place);
    }

    public ArrayList<Staff> getStaffs() {
        return this.staffs; 
    }

    public void add_Staffs(ArrayList<Staff> staffs) {
        this.staffs.addAll(staffs);
    }
    //------------------------------------------------------------------------------------------------------------//

    // Update function common to all classes
    void update_flight_details(Staff s, String flight_code, String update_field, String new_value)throws Exception{

        try{

            // FileReader object to find the flight whose status needs to be updated
            FileReader fr = new FileReader(flights_file);
            br = new BufferedReader(fr);
            String[] words = new String[7];

            String str;
            int count = 0; // flag variable that indicate if flight found or not

            // Reading the file
            while ((str = br.readLine()) != null) {

                // The file is comma separated and has 7 fields airline, flight code, from, to, fare, free_seats, functional
                words = str.split(",");
                if (words[1].equals(flight_code)) {

                    if(words[0].equals(s.Airline)){
                        count = 1;
                        break;
                    }

                    else{
                        count = 2;
                        break;
                    }
                    
                }
            }

            fr.close();
            br.close();

            // Flight found
            if(count == 1){

                Flight to_update = new Flight(words[2], words[3], Integer.parseInt(words[4]), words[1], words[0]);

                if(update_field == "from"){
                    to_update.update_flights(words[0], words[1], new_value, words[3], words[4], words[5], words[6]);
                }
                
                else if(update_field == "to"){
                    to_update.update_flights(words[0], words[1], words[2], new_value, words[4], words[5], words[6]);
                }

                else if(update_field == "fare"){
                    to_update.update_flights(words[0], words[1], words[2], words[3], new_value, words[5], words[6]);
                }

                else if(update_field == "seats"){
                    to_update.update_flights(words[0], words[1], words[2], words[3], words[4], new_value, words[6]);
                }

                else if(update_field == "status"){
                    to_update.update_flights(words[0], words[1], words[2], words[3], words[4], words[5], new_value);
                }
            }

            // Flight code not part of Airline staff works in
            else if(count == 2){

                System.out.println("You don't have access to update flight details of another airlines");

            }

            // Flight not found
            else{

                System.out.println("Flight with entered code doesn't exist");

            }
        }

        catch(FileNotFoundException e){

            System.out.println(e.toString());

        }    
    }

}

class SpiceJet extends Airline{

    private String name; // Airline name
    private String code; // Airline code
    private static int account = 0; // Account to and from which the transactions on bookings are done

    // Default constructor
    public SpiceJet() {

        this.name = "SpiceJet";
        this.code = "SG";
        this.flights.add(new Flight("Delhi", "Chennai", 2500, code + " 0001", name));
        this.flights.add(new Flight("Delhi", "Bhubeneshwar", 1500, code + " 0002", name));
        // this.staffs.add(); Add CEO alone
        
    }

    //--------------------------------------------Getters and setters---------------------------------------------//
    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public static void update_account(int amount){
        account += amount;
    }

    public static int get_accout(){
        return account;
    }
    //------------------------------------------------------------------------------------------------------------//
        
}

class Indigo extends Airline{

    private String name; // Airline name
    private String code; // Airline code
    private static int account = 0; // Account to and from which the transactions on bookings are done

    // Default constructor
    public Indigo() {

        this.name = "Indigo";
        this.code = "6E";
        this.flights.add(new Flight("Delhi", "Kolkata", 2000, code + " 0001", name));
        this.flights.add(new Flight("Delhi", "Chandigarh", 2500, code + " 0002", name));
        // this.staffs.add(); Add CEO alone

    }

    //--------------------------------------------Getters and setters---------------------------------------------//
    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public static void update_account(int amount){
        account += amount;
    }

    public static int get_accout(){
        return account;
    }
    //------------------------------------------------------------------------------------------------------------//

}

class AirIndia extends Airline{

    private String name; // Airline name
    private String code; // Airline code
    private static int account = 0; // Account to and from which the transactions on bookings are done

    // Default constructor
    public AirIndia() {

        this.name = "AirIndia";
        this.code = "AI";
        this.flights.add(new Flight("Mumbai", "Chennai", 1000, code + " 0001", name));
        this.flights.add(new Flight("Mumbai", "Bhubeneshwar", 1500, code + " 0002", name));
        // this.staffs.add(); Add CEO alone
        
    }

    //--------------------------------------------Getters and setters---------------------------------------------//
    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public static void update_account(int amount){
        account += amount;
    }

    public static int get_accout(){
        return account;
    }
    //------------------------------------------------------------------------------------------------------------//

}

class GoAir extends Airline{

    private String name; // Airline name
    private String code; // Airline code
    private static int account = 0; // Account to and from which the transactions on bookings are done

    // Default constructor
    public GoAir() {

        this.name = "GoAir";
        this.code = "G8";
        this.flights.add(new Flight("Chennai", "Pune", 1500, code + " 0001", name));
        this.flights.add(new Flight("Chennai", "Bhubeneshwar", 1500, code + " 0002", name));
        // this.staffs.add(); Add CEO alone
        
    }

    //--------------------------------------------Getters and setters---------------------------------------------//
    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public static void update_account(int amount){
        account += amount;
    }

    public static int get_accout(){
        return account;
    }
    //------------------------------------------------------------------------------------------------------------//

}

class Vistara extends Airline{

    private String name; // Airline name
    private String code; // Airline code
    private static int account = 0; // Account to and from which the transactions on bookings are done

    // Default constructor
    public Vistara() {

        this.name = "Vistara";
        this.code = "UK";
        this.flights.add(new Flight("Hyderabad", "Delhi", 3000, code + " 0001", name));
        this.flights.add(new Flight("Hyderabad", "Bhubeneshwar", 2500, code + " 0002", name));
        // this.staffs.add(); Add CEO alone
        
    }

    //--------------------------------------------Getters and setters---------------------------------------------//
    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }
    
    public static void update_account(int amount){
        account += amount;
    }

    public static int get_accout(){
        return account;
    }
    //------------------------------------------------------------------------------------------------------------//

}