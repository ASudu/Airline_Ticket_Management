import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Flight{

    static String current_dir = System.getProperty("user.dir");
    final int total_seats = 180; // 30 rows and 3+3 in each row

    // The read and write operations are done by Staff, Booking and Boarding Pass class
    private String from; // read and write
    private String to; // read and write
    private int fare; // read and write
    private String flight_code;  // read only
    private String Airline;  // read only
    private String time; // read adnd write (by staff only) format: HHMM(24 hrs)
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
    public Flight(String from, String to, int fare, String flight_code, String time, String Airline) {
        this.from = from;
        this.to = to;
        this.fare = fare;
        this.flight_code = flight_code;
        this.Airline = Airline;
        this.time = time;
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
    public void setAirline(Staff s){
        if(s.designation.equals("Sales Head")){
            this.Airline = s.Airline;
        }
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

    public void setCustomers_booked(int booked) {
        this.customers_booked = booked;
        this.free_seats -= booked;
    }

    public String get_time() {
        return this.time;
    }

    public void set_time(String time) {
        this.time = time;
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

    public void update_flights(String airline, String code, String from, String to, String fare, String free_seats, String time,String status) throws Exception{

        // First find the flight by flight_code :(unique identity)
        Path path = Paths.get(current_dir + "\\flights.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {

            if (fileContent.get(i).contains(code)){

                fileContent.set(i, airline+","+code+","+from+","+to+","+fare+","+free_seats+","+ time + "," + status);
                break;

            }

        }

        // Update file content
        Files.write(path, fileContent, StandardCharsets.UTF_8);

    }


}
