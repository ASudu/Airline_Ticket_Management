import java.util.*;
import java.io.*;

public class Airline{

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

    public void add_Flights(Staff s,ArrayList<Flight> flights) throws Exception{
        if(s.designation.equals("Sales Head")){
            // this.flights.addAll(flights);

            if(s.Airline.equals(flights.get(0).getAirline())){

                try {
                    FileWriter fw = new FileWriter(flights_file, true);
                    br1 = new BufferedWriter(fw);


                    for(Flight flight_obj : flights){

                        // //  For testing
                        // System.out.println(flight_obj.getAirline() + "," + flight_obj.getFlight_code() + "," + flight_obj.getFrom() + ","
                        // + flight_obj.getTo() + "," + Integer.toString(flight_obj.getFare()) + ","
                        // + Integer.toString(flight_obj.getFree_seats()) + "," + flight_obj.get_time() + "," + flight_obj.getFlight_status() + '\n');

                        fw.write(flight_obj.getAirline() + "," + flight_obj.getFlight_code() + "," + flight_obj.getFrom() + ","
                                + flight_obj.getTo() + "," + Integer.toString(flight_obj.getFare()) + ","
                                + Integer.toString(flight_obj.getFree_seats()) + "," + flight_obj.get_time() + "," + flight_obj.getFlight_status() + '\n');

                    }

                    fw.close();


                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Some error was encountered");
                    Main.Staff_Login(s);

                }

            }

            else{
                System.out.println("You can't add flights of different airlines!....");

                Main.Staff_Login(s);
            }

        }

        else{
            System.out.println("Only Sales Head can add flights!");
            Main.Staff_Login(s);
        }

    }

    // Only Sales Head of the Airline can add flights into the system
    public void add_Flights(Staff s,Flight flight) throws Exception {
        if(s.designation.equals("Sales Head")){
            if(s.Airline.equals(flight.getAirline())){

                try {
                    FileWriter fw = new FileWriter(flights_file);
                    br1 = new BufferedWriter(fw);


                    // // For testing
                    // System.out.println(flight.getAirline() + "," + flight.getFlight_code() + "," + flight.getFrom() + ","
                    //     + flight.getTo() + "," + Integer.toString(flight.getFare()) + ","
                    //     + Integer.toString(flight.getFree_seats()) + "," + flight.get_time() + "," + flight.getFlight_status() + '\n');

                    fw.write(flight.getAirline() + "," + flight.getFlight_code() + "," + flight.getFrom() + ","
                            + flight.getTo() + "," + Integer.toString(flight.getFare()) + ","
                            + Integer.toString(flight.getFree_seats()) + "," + flight.get_time() + "," + flight.getFlight_status() + '\n');

                    fw.close();


                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Some error was encountered");
                    Main.Staff_Login(s);

                }

            }

            else{
                System.out.println("You can't add flights of different airlines!....");
                Main.Staff_Login(s);
            }

        }

        else{
            System.out.println("Only Sales Head can add flights!");
            Main.Staff_Login(s);
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
            String[] words = new String[8];

            String str;
            int count = 0; // flag variable that indicate if flight found or not

            // Reading the file
            while ((str = br.readLine()) != null) {

                // The file is comma separated and has 8 fields airline, flight code, from, to, fare, free_seats, time, functional
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

                Flight to_update = new Flight(words[2], words[3], Integer.parseInt(words[4]), words[1], words[6], words[0]);

                if(update_field.equals("from")&& (Integer.parseInt(words[5])==180)){
                    to_update.update_flights(words[0], words[1], new_value, words[3], words[4], words[5], words[6], words[7]);
                }

                else if(update_field.equals("to")&&(Integer.parseInt(words[5])==180)){
                    to_update.update_flights(words[0], words[1], words[2], new_value, words[4], words[5], words[6], words[7]);
                }

                else if(update_field.equals("fare")){
                    to_update.update_flights(words[0], words[1], words[2], words[3], new_value, words[5], words[6], words[7]);
                }

                else if(update_field.equals("seats")){
                    to_update.update_flights(words[0], words[1], words[2], words[3], words[4], new_value, words[6], words[7]);
                }

                else if(update_field.equals("time")){
                    to_update.update_flights(words[0], words[1], words[2], words[3], words[4], words[5], new_value, words[7]);
                }

                else if(update_field.equals("status")){
                    to_update.update_flights(words[0], words[1], words[2], words[3], words[4], words[5], words[6], new_value);
                }
                else{
                    System.out.println("You are not authorized to perform this operation !");
                }
            }

            // Flight code not part of Airline staff works in
            else if(count == 2){

                System.out.println("You don't have access to update flight details of another airlines");
                Main.Staff_Login(s);

            }

            // Flight not found
            else{

                System.out.println("Flight with entered code doesn't exist");
                Main.Staff_Login(s);

            }
        }

        catch(FileNotFoundException e){

            System.out.println(e.toString());
            Main.Staff_Login(s);

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
        this.flights.add(new Flight("Delhi", "Chennai", 2500, code + " 0001", "0730 - 1000", name));
        this.flights.add(new Flight("Delhi", "Bhubeneshwar", 1500, code + " 0002", "0800 - 1000", name));
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
        this.flights.add(new Flight("Delhi", "Kolkata", 2000, code + " 0001", "0900 - 1030", name));
        this.flights.add(new Flight("Delhi", "Chandigarh", 2500, code + " 0002", "1230 - 1330", name));
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
        this.flights.add(new Flight("Mumbai", "Chennai", 1000, code + " 0001", "1200 - 1400", name));
        this.flights.add(new Flight("Mumbai", "Bhubeneshwar", 1500, code + " 0002", "1300 - 1530", name));
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
        this.flights.add(new Flight("Chennai", "Pune", 1500, code + " 0001", "1500 - 1700", name));
        this.flights.add(new Flight("Chennai", "Bhubeneshwar", 1500, code + " 0002", "1330 - 1530", name));
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
        this.flights.add(new Flight("Hyderabad", "Delhi", 3000, code + " 0001", "1630 - 1900", name));
        this.flights.add(new Flight("Hyderabad", "Bhubeneshwar", 2500, code + " 0002", "1530 - 1700", name));
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
