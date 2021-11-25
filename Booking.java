import java.util.Map;
import java.util.TreeMap;

public class Booking {

    private String date; //Format: DDMMYYY
    private String time; // Format: HHMM (24 hrs)
    private Airline airline; // to update the respective files
    private String from;
    private String to;
    private String seat_no = null;

    // For the logs
    static LogFile log_file = new LogFile();

    // Default constructor
    public Booking() {
    }


    // Parameterized constructor with just date, from and to (called just before booking)
    public Booking(String date, String from, String to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }

    // Parameterized constructor with date, time, from, to and seat number (called just before cancellation)
    public Booking(String date, String time, String from, String to, String seat) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.time = time;
        this.seat_no = seat;
    }

    //--------------------------------------------Getters and setters---------------------------------------------//

    // Get travel date of the booking
    public String getDate() {
        return this.date;
    }

    // Assign travel date of the booking
    public void setDate(String date) {
        this.date = date;
    }

    // Get travel time of the booking
    public String getTime() {
        return this.time;
    }

    // Set travel time of the booking
    public void setTime(String time) {
        this.time = time;
    }

    // Get airline of the booking
    public Airline getAirline() {
        return this.airline;
    }

    // Get travel from of the booking
    public String getFrom() {
        return this.from;
    }

    // Assign travel from of the booking
    public void setFrom(String from) {
        this.from = from;
    }

    // Get travel to of the booking
    public String getTo() {
        return this.to;
    }

    // Assign travel to of the booking
    public void setTo(String to) {
        this.to = to;
    }

    // Get seat number of the booking
    public String getseat_no() {
        return this.seat_no;
    }

    // Get seat number of the booking
    public void setseat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    // All operations (namely, booking and cancellation) handled here
    public void seat_ops(String flightcode, String op, Customer c, TreeMap<String, String> passengers)throws Exception{

        Seats s = new Seats(flightcode);

        // If operations is booking, then op = book
        if(op.equals("book")){

            // TreeMap contains: key - Name of passenger, value - Age of passenger
            for (Map.Entry<String, String> entry : passengers.entrySet()) {

                
                s.book_seat(c, entry.getKey());

                // Updates log file
                log_file.append(c, "Ticket booked by " + c.username);

            }

            System.out.println("Booking successful! Thank you!");
            


        }

        // If operations is cancellation, then op = cancel
        else if(op.equals("cancel")){

            // TreeMap contains: key - Name of passenger, value - Seat number of passenger
            for (Map.Entry<String, String> entry : passengers.entrySet()){

                s.cancel_seat(c, entry.getKey(), entry.getValue());

                // Updates log file
                log_file.append(c, "Ticket cancelled by " + c.username);
            }

            System.out.println("Cancellation successful! Your money has been refunded successfully!");
        }



    }


    //------------------------------------------------------------------------------------------------------------//
}
