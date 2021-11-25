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
    private int customers_booked;
    private int free_seats; // read only
    private String flight_status; //  read and write ; true if functional else false


    // Default constructor
    public Flight() {

        this.from = City.Home_city;
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
        this.free_seats = total_seats - this.customers_booked;
        this.flight_status = "true";
    }


    //--------------------------------------------Getters and setters---------------------------------------------//
    // Get total seats in a flight
    public int getTotal_seats() {
        return this.total_seats;
    }

    // Get the city from which this flight object travels
    public String getFrom() {
        return this.from;
    }

    // Set the city from which this flight object travels
    public void setFrom(String from) {
        this.from = from;
    }

    // Get the city to which this flight object travels
    public String getTo() {
        return this.to;
    }

    // Set the city to which this flight object travels
    public void setTo(String to) {
        this.to = to;
    }

    // Get the flight fare
    public int getFare() {
        return this.fare;
    }

    // Set Airline if and only the staff is sales head of that airline
    public void setAirline(Staff s){
        if(this.Airline.equals(s.Airline) && s.designation.equals("Sales Head")){
            this.Airline = s.Airline;
        }
    }

    // Update flight fare
    public void update_Fare(int fare) {
        this.fare = fare;
    }

    // Get flight code
    public String getFlight_code() {
        return this.flight_code;
    }

    // Get airline name of the current flight object
    public String getAirline() {
        return this.Airline;
    }

    // Get the number of customers booked
    public int getCustomers_booked() {
        return this.customers_booked;
    }

    // Update number of customers booked when booking and cancellation
    public void setCustomers_booked(int booked) {
        this.customers_booked = booked;
        free_seats = free_seats - booked;
    }

    // Get time of travel of the flight
    public String get_time() {
        return this.time;
    }

    // Update time of travrl of the flight
    public void set_time(String time) {
        this.time = time;
    }

    // Get number of free seats
    public int getFree_seats() {
        return this.free_seats;
    }

    // Get flight status (functional or not)
    public String getFlight_status() {
        return this.flight_status;
    }

    //------------------------------------------------------------------------------------------------------------//

    // Updates the value of the fields in flighs.txt
    public void update_flights(String airline, String code, String from, String to, String fare, String free_seats, String time,String status) throws Exception{

        // First find the flight by flight_code :(unique identity)
        Path path = Paths.get(current_dir + "\\flights.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {

            if (fileContent.get(i).contains(this.flight_code)) {

                fileContent.set(i, airline+","+code+","+from+","+to+","+fare+","+free_seats+","+ time + "," + status);
                break;

            }

        }

        // Update file content
        Files.write(path, fileContent, StandardCharsets.UTF_8);

    }


}
