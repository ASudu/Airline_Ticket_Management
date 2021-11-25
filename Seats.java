import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Seats implements Serializable {
    
    static String current_dir = System.getProperty("user.dir");
    static File flight_seats = new File(current_dir + "\\flight_seats.txt");
    static Console cnsl = System.console();

    Integer[][] seat_matrix = new Integer[30][6]; // 30 rows 6 columns - layout of seats in flight
    Flight temp_flight = new Flight();
    int seats_available = temp_flight.getFree_seats();
    String flightCode; // Can't use tem_flight.flight_code since it is public

    // Default constructor
    public Seats() {
    }
 
    // Parameterized constructor
    Seats(String flightCode)throws Exception{

        this.flightCode = flightCode;

        // Seat matrix
        for(int i=0;i<30;i++){
            for(int j=0;j<6;j++){
                seat_matrix[i][j] = 0;
            }
        }


        // If the flight exists
        if(code_exists(this.flightCode)){
            Path path = Paths.get(current_dir + "\\flight_seats.txt");


            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

            // Updates seat matrix - 1 if filled, 0 if vacant
            for (int i = 0; i < fileContent.size(); i++) {

                String get_code = fileContent.get(i).split("@")[0];

                // If the entry is a flight with same code
                if(get_code.equals(this.flightCode)){

                    
                    String[] str = fileContent.get(i).split("@");
                    String[] seats = str[1].split(",");

                    
                    for(String s: seats){

                        assign_seat(s.split(":")[0]);
                    }
                    break;

                }       
            }    
        }
    }


    // Get flight details from flight.txt for given flight code
    String[] get_flight_details(String code) throws IOException{
        String[] temp = null;

        Path p1 = Paths.get(current_dir + "\\flights.txt");
        List<String> fileContent1 = new ArrayList<>(Files.readAllLines(p1, StandardCharsets.UTF_8));
        for(int k = 0; k < fileContent1.size(); k++){

            temp = fileContent1.get(k).split(",");

            if(temp[1].equals(code)){
               return temp;
            }
        }
        return temp;
    }


    // When seat needs to be booked
    String book_seat(Customer c, String name)throws Exception{

        
        System.out.println("which seat do you prefer?: ");
        System.out.println("1. \"W\" for window seat\n2. \"M\" for middle seat\n3. \"A\" for Aisle seat\n4. \"N\" for no preference\n5. \"Q\" to quit");
        String read = cnsl.readLine("Your choice: ").strip();
        int counter = 0;
        String seat_no = "";
        int ver_no = 0;

        do{

            // Window seat to be booked
            if(read.equals("W")){

                for(int i =0;i<30;i++){
                    for(int j=0;j<6;j++){
                        if((j==0||j==5) && (check_seat_vacant(i,j))){

                            seat_matrix[i][j] = 1;
                            ver_no = Integer.parseInt(cnsl.readLine("Enter verification number to verify booking while check-in: ").strip());
                            update_flightDB(c,i,j,flightCode,name,Integer.toString(ver_no),"book");

                            System.out.println("Your seat number is : " +seat_index(i,j));
                            seat_no = seat_no + seat_index(i,j);
                            counter =1;
                            temp_flight.setCustomers_booked(1);
                            String[] arr = get_flight_details(this.flightCode);
                            temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);
                            break;
                        }
                    }
                    if(counter!=0)break;
                }

            }

            // Middle seat to be booked
            else if(read.equals("M")){

                for(int i =0;i<30;i++){
                    for(int j=0;j<6;j++){
                        if((j==1||j==4) && (check_seat_vacant(i,j))){

                            seat_matrix[i][j] = 1;
                            ver_no = Integer.parseInt(cnsl.readLine("Enter verification number to verify booking while check-in: ").strip());
                            update_flightDB(c,i,j,flightCode,name,Integer.toString(ver_no),"book");

                            System.out.println("Your seat number is : " +seat_index(i,j));
                            seat_no = seat_no + seat_index(i,j);
                            counter =1;
                            temp_flight.setCustomers_booked(1);
                            String[] arr = get_flight_details(this.flightCode);
                            temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);
                            break;
                        }
                    }
                    if(counter!=0)break;
                }

            }

            // Aisle seat to be booked
            else if(read.equals("A")){

                for(int i =0;i<30;i++){
                    for(int j=0;j<6;j++){
                        if((j==2||j==3) && (check_seat_vacant(i,j))){

                            seat_matrix[i][j] = 1;
                            ver_no = Integer.parseInt(cnsl.readLine("Enter verification number to verify booking while check-in: ").strip());
                            update_flightDB(c,i,j,flightCode,name,Integer.toString(ver_no),"book");

                            System.out.println("Your seat number is : " +seat_index(i,j));
                            seat_no = seat_no + seat_index(i,j);
                            counter =1;
                            temp_flight.setCustomers_booked(1);
                            String[] arr = get_flight_details(this.flightCode);
                            temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);
                            break;
                        }
                    }
                    if(counter!=0)break;
                }

            }
            
            // Customer has no preference
            else if(read.equals("N")){
                for(int i=0;i<30;i++){
                    for(int j=0;j<6;j++){
                        if(check_seat_vacant(i,j)){

                            this.update_seats(i,j);
                            ver_no = Integer.parseInt(cnsl.readLine("Enter verification number to verify booking while check-in: ").strip());
                            update_flightDB(c,i,j,flightCode,name,Integer.toString(ver_no),"book");

                            System.out.println("Your seat number is : " +seat_index(i,j));
                            seat_no = seat_no + seat_index(i,j);
                            counter = 1;
                            temp_flight.setCustomers_booked(1);
                            String[] arr = get_flight_details(this.flightCode);
                            temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);
                            break;
                        }
                    }
                    if(counter!=0)break;
                }
            }
            
            else if(read.equals("Q")){
                System.out.println("Exiting.....");
                System.exit(0);
            }

            else
                System.out.println("Please enter a valid input");

        }while(!read.equals("W") && !read.equals("M") && !read.equals("A") && !read.equals("N") && !read.equals("Q"));

        return seat_no;

    }


    // Updates entry (i,j) in seat matrix
    void update_seats(int i,int j){

        this.seat_matrix[i][j] = 1;

    }


    // Checks if seats are available in given flight
    boolean check_seats_available(){

        boolean seat_available;

        // If condition in bracket is true the value of seat_available is true else false
        seat_available = (this.seats_available>=1)? true : false;
        
        return seat_available;
    }


    // Checks if a specific seat in flight is vacant
    boolean check_seat_vacant(int i,int j){

        boolean vacant;

        // If condition in bracket is true the value of vacant is true else false
        vacant = (this.seat_matrix[i][j]==0)? true : false;
        
        return vacant;
    }


    // Formats seat number and assigns
    String seat_index(Integer i,int j){
        // String col = (j>=0&&j<6)? String.valueOf((char)(j+65)) : null;
        String[] list_char = {"A", "B", "C", "D", "E", "F"};
        String col = (j>=0&&j<6)? list_char[j] : null;
        Integer row = i+1;

        String seat = row.toString() +"-"+col;
        return seat;
    }


    // Assigns seat to the booking
    void assign_seat(String str){

        String[] read = new String[2];
        read = str.split("-");
        int i = Integer.parseInt(read[0])-1;
        int j = (int)(read[1].charAt(0)) - 65;
        seat_matrix[i][j] = 1;
        seats_available--;

    }


    // Called when seat is to be cancelled
    void cancel_seat(Customer c, String name, String seat) throws Exception{
        
        String[] read = new String[2];
        read = seat.split("-");
        int i = Integer.parseInt(read[0])-1;
        int j = (int)(read[1].charAt(0)) - 65;
        seat_matrix[i][j] = 0;
        temp_flight.setCustomers_booked(-1);


        int ver_no = Integer.parseInt(cnsl.readLine("Enter verification number to verify booking while check-in: ").strip());
        update_flightDB(c,i,j,flightCode,name,Integer.toString(ver_no),"book");
        String[] arr = get_flight_details(this.flightCode);
        temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);


    }


    // Adds entry of user with username and passenger name
    void update_flightDB(Customer c,int i, int j,String code, String name, String ver_no, String choice)throws Exception{

        try{
            FileWriter fw = new FileWriter(flight_seats,true);

            // If this is the first seat being booked in that flight (entry doesn't exists in the file)
            if(!code_exists(code) && choice.equals("book")) {

                fw.write(flightCode + "@" + seat_index(i, j) + ":" + c.username + "$" + name + "+" + ver_no);
                

            }
            
            // Few seats in that flight have already been booked (entry exists in the file)
            else if(code_exists(code) && choice.equals("book")){

                // Updates seat number and username in flight_seats.txt
                Path path = Paths.get(current_dir + "\\flight_seats.txt");
                List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

                for (int k = 0; k < fileContent.size(); k++) {

                    String get_code = fileContent.get(k).split("@")[0];
                    if(get_code.equals(code)) {

                        String temp = fileContent.get(k); // Copy existing contents of the line to temp
                        fileContent.set(k, temp +","+ seat_index(i, j) + ":" + c.username + "$" + name + "+" + ver_no); // Modifying it and rewriting to list
                        break;

                    }

                }
                // Writing modified list to the file
                Files.write(path, fileContent, StandardCharsets.UTF_8);

                // Updates number of free seats in that flight in flights.txt
                Path path1 = Paths.get(current_dir + "\\flights.txt");
                List<String> fileContent1 = new ArrayList<>(Files.readAllLines(path1, StandardCharsets.UTF_8));

                for (int w = 0; w < fileContent1.size(); w++) {

                    String[] line = fileContent1.get(w).split(",");
                    if(line[1].equals(code)) {

                        // String temp = fileContent1.get(w); // Copy existing contents of the line to temp
                        fileContent.set(w, line[0] + "," + line[1] + "," + line[2] + "," + line[3] + "," + line[4] + ","
                        + Integer.toString(seats_available)  + "," + line[6] + "\n"); // Modifying it and rewriting to list
                        break;

                    }

                }
                // Writing modified list to the file
                Files.write(path1, fileContent1, StandardCharsets.UTF_8);

            }
            
            else if(code_exists(code) && choice.equals("cancel")){

                Path path = Paths.get(current_dir + "\\flight_DB.txt");
                List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

                for (int k = 0; k < fileContent.size(); k++){
                    if(fileContent.get(k).contains(seat_index(i,j))){
                        String flight_code = fileContent.get(k).split("@")[0];
                        String str = fileContent.get(k).split("@")[1];
                        String[] seats_user = str.split(",");
                        ArrayList<String> new_Seats  = new ArrayList<String>();

                        for(int m=0;m<seats_user.length;m++){
                            if(!seats_user[m].contains(seat_index(i,j))){
                                System.out.println(seats_user[m]);
                                new_Seats.add(seats_user[m]);
                            }
                        }

                        // Modifying the line which contained seat
                        String temp = "";
                        for(String s:new_Seats){
                            if(!s.equals(new_Seats.get(new_Seats.size()-1)))
                            temp = temp.concat(s+",");
                            else temp = temp.concat(s);
                        }

                        // Updating the arraylist
                        fileContent.set(k,flight_code+"@"+temp);

                        break;
                    }
                }

                // Writing back to file
                Files.write(path, fileContent, StandardCharsets.UTF_8);
            }

            fw.close();
        }

        catch(Exception e){
            System.out.println("updateflightdb");
            e.printStackTrace();
        }
    }


    // Check if any bookings are done for a given flight
    static boolean code_exists(String code)throws Exception{
        Path path = Paths.get(current_dir + "\\flight_seats.txt");
    
        if(flight_seats.length() != 0){

            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String get_code = fileContent.get(i).split("@")[0];
                if(get_code.equals(code)){
                    return true;
                }

            }

        }
        
        return false;
    }



 }

