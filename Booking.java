import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

class Driver1{


    public static void main(String[] args) throws Exception {
        Seats f1 = new Seats("6E 0005");

        // System.out.println(Integer.toString(f1.seats_available));

        // f1.update_flightDB(5,0,4,"6E 0005","cancel");



    }

}

class Seats implements Serializable {
    
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String current_dir = System.getProperty("user.dir");
    static File flight_seats = new File(current_dir + "\\flight_seats.txt");

    Integer[][] seat_matrix = new Integer[30][6]; // 30 rows 6 columns - layout of seats in flight
    Flight temp_flight = new Flight();
    int seats_available = temp_flight.total_seats;
    String flightCode; // Can't use tem_flight.flight_code since it is public
    int num_user;

    Seats(String flightCode)throws Exception{
        this.flightCode = flightCode;

        for(int i=0;i<30;i++){
            for(int j=0;j<6;j++){
                seat_matrix[i][j] = 0;
            }
        }


        if(code_exists(flightCode)){
            Path path = Paths.get(current_dir + "\\flight_seats.txt");

            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String get_code = fileContent.get(i).split("@")[0];
                if(get_code.equals(flightCode)){
                    String str = fileContent.get(i).split("@")[1];
                    System.out.println(str);

                    String fields = str.split(",",2)[0];
                    System.out.println(fields);
                    String next_fields = str.split(",",2)[1];
                    System.out.println(next_fields);
                    while(fields!=null){
                    String seats = fields.split(":")[0];
                    System.out.println(seats);
                    assign_seat(seats);
                    fields = next_fields.split(",",2)[0];
                    try {
                        next_fields = next_fields.split(",", 2)[1];
                    }catch(Exception e){
                        String seat_last = next_fields.split(":")[0];
                        assign_seat(seat_last);
                        break;
                    }
                    }
                    break;
                }

            }
        }




    }



    void book_seat(/*Customer c*/ int num)throws Exception{
        System.out.println("Do you need a window seat ? Press Y for yes or N for no");
        String read = br.readLine();
        int counter = 0;
        if(read.equals("Y")){
            for(int i =0;i<30;i++){
                for(int j=0;j<6;j++){
                    if((check_seat_vacant(i,j))&&(j==0||j==5)){

                        seat_matrix[i][j] = 1;
                        update_flightDB(num,i,j,flightCode,"book");

                        System.out.println("Your seat number is : " +seat_index(i,j));
                        //seat_matrix_user[i][j]=/* c.username */;
                        counter =1;
                        seats_available--;
                        break;
                    }
                }
                if(counter!=0)break;
            }

        }else if(read.equals("N")){
            for(int i=0;i<30;i++){
                for(int j=0;j<6;j++){
                    if(check_seat_vacant(i,j)){
                        this.update_seats(i,j);
                        update_flightDB(num,i,j,flightCode,"book");
                        System.out.println("Your seat number is : " +seat_index(i,j));
                        //seat_matrix_user[i][j]=/* c.username */;
                        counter = 1;
                        seats_available--;
                        break;
                    }
                }
                if(counter!=0)break;
            }
        }else{
            System.out.println("Please enter a valid input");
            book_seat(num);
        }

    }
    void update_seats(int i,int j){
        this.seat_matrix[i][j] = 1;
    }

    boolean check_seats_available(){
        if(seats_available>=1){
            return true;
        }
        else return false;
    }
    boolean check_seat_vacant(int i,int j){
        if(this.seat_matrix[i][j]==0){
            return true;
        }else return false;
    }
    String seat_index(Integer i,int j){
        String col = (j>=0&&j<6)? String.valueOf((char)(j+65)) : null;
        Integer row = i+1;
        String seat = row.toString() +"-"+col;
        return seat;
    }
    void assign_seat(String str){
        String[] read = new String[2];
        read = str.split("-");
        int i = Integer.parseInt(read[0])-1;
        int j = (int)(read[1].charAt(0)) - 65;
        seat_matrix[i][j] = 1;
        seats_available--;
    }
    void cancel_seat(/* Customer c*/String seat){
        int found =0;
        String[] read = new String[2];
        read = seat.split("-");
        int i = Integer.parseInt(read[0])-1;
        int j = (int)(read[1].charAt(0)) - 65;
        seat_matrix[i][j] = 0;
        seats_available++;

    }
       void update_flightDB(/* Customer c */ Integer num,int i, int j,String code,String choice)throws Exception{
        FileWriter fw = new FileWriter(flight_seats,true);

        if(!code_exists(code)&&choice.equals("book")) {
            fw.write(flightCode + "@" + seat_index(i, j) + ":" + "user" + num.toString());
            fw.close();

        }else if(code_exists(code)&&choice.equals("book")){
            Path path = Paths.get(current_dir + "\\flight_seats.txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            for (int k = 0; k < fileContent.size(); k++) {
                String get_code = fileContent.get(k).split("@")[0];
                if(get_code.equals(code)) {
                    String temp = fileContent.get(k);
                    fileContent.set(k, temp +","+ seat_index(i, j) + ":" + "user" + num.toString());
                    break;
                }

            }
            Files.write(path, fileContent, StandardCharsets.UTF_8);

        }else if(code_exists(code)&&choice.equals("cancel")){
            Path path = Paths.get(current_dir + "\\flight_seats.txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            for (int k = 0; k < fileContent.size(); k++){
                if(fileContent.get(k).contains(seat_index(i,j))){
                    String str1_with_code = fileContent.get(k).split(seat_index(i,j))[0];
                    String str1 = str1_with_code.split("@")[1];
                    String str2 = fileContent.get(k).split(seat_index(i,j))[1];
                    System.out.println(str1);
                    System.out.println(str2);

                    break;
                }
            }
        }



    }
    static boolean code_exists(String code)throws Exception{
        Path path = Paths.get(current_dir + "\\flight_seats.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        for (int i = 0; i < fileContent.size(); i++) {
            String get_code = fileContent.get(i).split("@")[0];
            if(get_code.equals(code)){
                return true;
            }

        }
        return false;
    }



 }

