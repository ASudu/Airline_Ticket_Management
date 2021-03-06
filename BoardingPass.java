import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class BoardingPass{

    String booking_name; // The username under which booking was done
    String flight_code;
    static String current_dir = System.getProperty("user.dir");

    BoardingPass(String n,String c){

        this.booking_name = n;
        this.flight_code = c;
    }
    public BoardingPass(){

    }


    public int display_boarding_pass()throws Exception{

        Path path = Paths.get(current_dir + "\\flight_seats.txt");
        Path p1 = Paths.get(current_dir + "\\flights.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        List<String> fileContent1 = new ArrayList<>(Files.readAllLines(p1, StandardCharsets.UTF_8));

        int flag = 0;

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

        // To get airport code and terminal
        City c_from = new City();
        City c_to = new City();
        c_from  = c_from.assign_city(from);
        c_to = c_to.assign_city(to);

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
                            String passenger_name = op[1].split(Pattern.quote("$"))[1].split("[+]")[0];
                            String passenger_Seat = op[0];

                            flag  = 1;


                            System.out.println("***********************************************************************");
                            System.out.println("THANK YOU FOR FLYING WITH " + airline + "!!");
                            System.out.println("   FLIGHT CODE: " + this.flight_code);
                            System.out.println("   FROM: " + from + "(" + c_from.getAirport() + ")" + "            TO: " + to + "(" + c_to.getAirport() + ")");
                            System.out.println("   TIME: " + time);
                            System.out.println("   PASSENGER NAME: " + passenger_name + "  SEAT NUMBER: " + passenger_Seat);
                            System.out.println("***********************************************************************");

                        }
                    }
                    break;
                }
            }

        }



    if(flag==0){
        return 0;
    }else
        return 1;

    }
    public int display_boarding_pass(int bag_weight, int bag_charge)throws Exception{

        Path path = Paths.get(current_dir + "\\flight_seats.txt");
        Path p1 = Paths.get(current_dir + "\\flights.txt");

        List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        List<String> fileContent1 = new ArrayList<>(Files.readAllLines(p1, StandardCharsets.UTF_8));

        int flag = 0;

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

        // To get airport code
        City c_from = new City();
        City c_to = new City();
        c_from  = c_from.assign_city(from);
        c_to = c_to.assign_city(to);

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
                            String passenger_name = op[1].split(Pattern.quote("$"))[1].split("[+]")[0];
                            String passenger_Seat = op[0];

                            flag  = 1;

                            // Display the boarding pass
                            System.out.println("***********************************************************************");
                            System.out.println("(Check-in done!)");
                            System.out.println("THANK YOU FOR FLYING WITH " + airline + "!!");
                            System.out.println("   FLIGHT CODE: " + this.flight_code);
                            System.out.println("   FROM: " + from + "(" + c_from.getAirport() + ")" + "            TO: " + to + "(" + c_to.getAirport() + ")");
                            System.out.println("   TIME: " + time);
                            System.out.println("   PASSENGER NAME: " + passenger_name + "  SEAT NUMBER: " + passenger_Seat);
                            System.out.println("   BAGGAGE WEIGHT (Max 25kgs): " + Integer.toString(bag_weight) + "kgs");
                            System.out.println("   EXTRA BAGGAGE CHARGE: Rs." + Integer.toString(bag_charge));
                            System.out.println("***********************************************************************");

                        }
                    }
                    break;
                }
            }

        }



        if(flag==0){
            return 0;
        }else
            return 1;

    }





}

