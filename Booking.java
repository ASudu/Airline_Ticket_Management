import java.io.*;
import java.util.*;

public class Booking {

    String date; //Format: DDMMYYY
    String time; // Format: HHMM (24 hrs)
    Airline airline; // to update the respective files


    public Booking() {
    }


    public Booking(String date, String time, Airline airline) {
        this.date = date;
        this.time = time;
        this.airline = airline;
    }


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

    
}
