import java.io.*;
import java.util.*;

public class Booking {

    private String date; //Format: DDMMYYY
    private String time; // Format: HHMM (24 hrs)
    private Airline airline; // to update the respective files
    private String from;
    private String to;


    public Booking() {
    }


    public Booking(String date, String from, String to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }

    //--------------------------------------------Getters and setters---------------------------------------------//

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Airline getAirline() {
        return this.airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
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
    
    //------------------------------------------------------------------------------------------------------------//


    
}
