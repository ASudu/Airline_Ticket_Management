public class BoardingPass{

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