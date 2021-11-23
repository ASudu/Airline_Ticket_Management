import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Booking {

    private String date; //Format: DDMMYYY
    private String time; // Format: HHMM (24 hrs)
    private Airline airline; // to update the respective files
    private String from;
    private String to;
    private String seat_no = null;


    public Booking() {
    }


    public Booking(String date, String from, String to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public Booking(String date, String time, String from, String to, String seat) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.time = time;
        this.seat_no = seat;
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

//    public void setAirline(Airline airline) {
//        this.airline = airline;
//    }

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

    public String getseat_no() {
        return this.seat_no;
    }

    public void setseat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public void seat_ops(String flightcode, String op, Customer c, TreeMap<String, String> passengers)throws Exception{

        Seats s = new Seats(flightcode);

        if(op.equals("book")){
            String seat_num = "";
            for (Map.Entry<String, String> entry : passengers.entrySet()) {
                seat_num = s.book_seat(c, entry.getKey());
                entry.setValue(seat_num);
            }


        }
        
        else if(op.equals("cancel"))

            for (Map.Entry<String, String> entry : passengers.entrySet())
                s.cancel_seat(c, entry.getKey(), entry.getValue());
    }
    
    //------------------------------------------------------------------------------------------------------------//
}
class BoardingPass{

    String passengerName;
    String seat_no;
    String to;
    String from;
    String flight_code;
    String time_of_departure;
    String flight_duration;

    BoardingPass(String p,String s,String t,String f,String c,String ti,String fd){
        this.passengerName = p;
        this.seat_no =s;
        this.flight_duration = fd;
        this.from =f;
        this.to = t;
        this.flight_code = c;
        this.time_of_departure = ti;
    }

    

}