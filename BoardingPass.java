import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class BoardingPass{

    String booking_name; // The username under which booking was done
    String flight_code;
    static String current_dir = System.getProperty("user.dir");

    BoardingPass(String n,String c){

        this.booking_name = n;
        this.flight_code = c;
    }

    public void display_boarding_pass()throws Exception{

        Path path = Paths.get(current_dir + "\\flight_seats.txt");
        Path p1 = Paths.get(current_dir + "\\flights.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        List<String> fileContent1 = new ArrayList<>(Files.readAllLines(p1, StandardCharsets.UTF_8));

        String from = City.Home_city;
        String to = "";
        String time = "0000";
        String airline = "";

        for(int k = 0; k < fileContent1.size(); k++){

            String[] temp = fileContent1.get(k).split(",");

            if(temp[1].equals(this.flight_code)){
                airline = temp[0];
                from = temp[2];
                to = temp[3];
                time = temp[6];
            }
        }

        for(int i = 0; i<fileContent.size(); i++){

            String get_code = fileContent.get(i).split("@")[0];
            if(get_code.equals(this.flight_code)) {

                String temp = fileContent.get(i); // Copy existing contents of the line to temp
                if(temp.contains(booking_name)){

                    String seat_users = fileContent.get(i).split("@")[1]; //get all the seat users
                    String[] getPassengers = seat_users.split(","); //separate each getPassengers : "seat-no:username$PassengerName"
                    for(String s:getPassengers){
                        if(s.contains(booking_name)){       //if seat-no:username$PassengerName contains username

                            String[] op = s.split(":");
                            String passenger_name = op[1].split("$")[1];
                            String passenger_Seat = op[0];

                            System.out.println("***********************************************************************");
                            System.out.println("THANK YOU FOR FLYING WITH " + airline + "!!");
                            System.out.println("   CODE: " + this.flight_code);
                            System.out.println("   FROM: " + from + "            TO: " + to);
                            System.out.println("   TIME: " + time);
                            System.out.println("   PASSENGER NAME: " + passenger_name + "  SEAT NUMBER: " + passenger_Seat);
                            System.out.println("***********************************************************************");

                        }
                    }
                    break;
                }
            }

        }


        

    }

    

}