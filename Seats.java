import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Seats implements Serializable {
    

    static String current_dir = System.getProperty("user.dir");
    static File flight_seats = new File(current_dir + "\\flight_seats.txt");
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    Integer[][] seat_matrix = new Integer[30][6]; // 30 rows 6 columns - layout of seats in flight
    Flight temp_flight = new Flight();
    int seats_available = temp_flight.getFree_seats();
    String flightCode; // Can't use tem_flight.flight_code since it is public



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



    String book_seat(Customer c, String name)throws Exception{

        System.out.println("which seat do you prefer?: ");
        System.out.println("1. \"W\" for window seat\n2. \"M\" for middle seat\n3. \"A\" for Aisle seat\n4. \"N\" for no preference\n5. \"Q\" to quit");
        System.out.println("Your choice: ");
        String read = br.readLine().strip();
        int counter = 0;
        String ver_no = "";
        String seat_no = "";


        do{

            // Window seat to be booked
            if(read.equals("W")){

                for(int i =0;i<30;i++){
                    for(int j=0;j<6;j++){
                        if((j==0||j==5) && (check_seat_vacant(i,j))){

                            seat_matrix[i][j] = 1;
                            System.out.println("Enter verification number : ");
                            ver_no = br.readLine();
                            update_flightDB(c,i,j,flightCode,name,ver_no,"book");

                            System.out.println(name+" Your seat number is : " +seat_index(i,j));
                            seat_no = seat_no+seat_index(i,j);
                            // c.seat_no = seat_index(i, j);
                            //seat_matrix_user[i][j]=/* c.username */;
                            counter =1;
                            temp_flight.setCustomers_booked(1);
                            String[] arr = get_flight_details(this.flightCode);
                            temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);
                            break;
                        }
                    }
                    if(counter!=0)break;
                }if(counter==0){
                    System.out.println("All window seats are booked !");
                    System.out.println("Please choose some different seat");
                    book_seat(c,name);
                }

            }

            // Middle seat to be booked
            else if(read.equals("M")){

                for(int i =0;i<30;i++){
                    for(int j=0;j<6;j++){
                        if((j==1||j==4) && (check_seat_vacant(i,j))){

                            seat_matrix[i][j] = 1;
                            System.out.println("Enter verification number : ");
                            ver_no = br.readLine();
                            update_flightDB(c,i,j,flightCode,name,ver_no,"book");

                            System.out.println(name+ " Your seat number is : " +seat_index(i,j));
                            seat_no = seat_no+seat_index(i,j);
                            // c.seat_no = seat_index(i, j);
                            //seat_matrix_user[i][j]=/* c.username */;
                            counter =1;
                            temp_flight.setCustomers_booked(1);
                            String[] arr = get_flight_details(this.flightCode);
                            temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);
                            break;
                        }
                    }
                    if(counter!=0)break;
                }if(counter==0){
                    System.out.println("All Middle seats are booked !");
                    System.out.println("Please choose some different seat");
                    book_seat(c,name);
                }

            }

            // Aisle seat to be booked
            else if(read.equals("A")){

                for(int i =0;i<30;i++){
                    for(int j=0;j<6;j++){
                        if((j==2||j==3) && (check_seat_vacant(i,j))){

                            seat_matrix[i][j] = 1;
                            System.out.println("Enter verification number : ");
                            ver_no = br.readLine();
                            update_flightDB(c,i,j,flightCode,name,ver_no,"book");

                            System.out.println(name +" Your seat number is : " +seat_index(i,j));
                            seat_no = seat_no+seat_index(i,j);
                            // c.seat_no = seat_index(i, j);
                            //seat_matrix_user[i][j]=/* c.username */;
                            counter =1;
                            temp_flight.setCustomers_booked(1);
                            String[] arr = get_flight_details(this.flightCode);
                            temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);
                            break;
                        }
                    }
                    if(counter!=0)break;
                }
                if(counter==0){
                    System.out.println("All aisle seats are booked !");
                    System.out.println("Please choose some different seat");
                    book_seat(c,name);
                }

            }
            
            else if(read.equals("N")){
                for(int i=0;i<30;i++){
                    for(int j=0;j<6;j++){
                        if(check_seat_vacant(i,j)){
                            seat_matrix[i][j] = 1;
                            System.out.println("Enter verification number : ");
                            ver_no = br.readLine();
                            update_flightDB(c,i,j,flightCode,name,ver_no,"book");
                            System.out.println(name+" Your seat number is : " +seat_index(i,j));
                            seat_no = seat_no+seat_index(i,j);
                            // c.seat_no = seat_index(i, j);
                            //seat_matrix_user[i][j]=/* c.username */;
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

            else {
                System.out.println("Please enter a valid input");
                book_seat(c,name);
            }
        }while(!read.equals("W") && !read.equals("M") && !read.equals("A") && !read.equals("N") && !read.equals("Q"));

        return seat_no;

    }


    void update_seats(int i,int j){

        this.seat_matrix[i][j] = 1;

    }


    boolean check_seats_available(){

        boolean seat_available;

        // If condition in bracket is true the value of seat_available is true else false
        seat_available = (this.seats_available>=1)? true : false;
        
        return seat_available;
    }


    boolean check_seat_vacant(int i,int j){

        boolean vacant;

        // If condition in bracket is true the value of vacant is true else false
        vacant = (this.seat_matrix[i][j]==0)? true : false;
        
        return vacant;
    }


    String seat_index(Integer i,int j){
        // String col = (j>=0&&j<6)? String.valueOf((char)(j+65)) : null;
        String[] list_char = {"A", "B", "C", "D", "E", "F"};
        String col = (j>=0&&j<6)? list_char[j] : null;
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
        this.temp_flight.setCustomers_booked(1);

    }


    void cancel_seat(Customer c, String name,String seat) throws Exception{
        // int found =0;
        String[] read = new String[2];
        read = seat.split("-");
        int i = Integer.parseInt(read[0])-1;
        int j = (int)(read[1].charAt(0)) - 65;
        seat_matrix[i][j] = 0;
        temp_flight.setCustomers_booked(-1);
        System.out.println("Enter verification number to verify booking while check-in: ");
        String ver_no = br.readLine().strip();


        // c.seat_no = null;
        update_flightDB(c, i, j, this.flightCode, name,ver_no, "cancel");
        String[] arr = get_flight_details(this.flightCode);
        temp_flight.update_flights(arr[0], arr[1], arr[2], arr[3], arr[4], Integer.toString(temp_flight.getFree_seats()), arr[6], arr[7]);


    }


    void update_flightDB(Customer c,int i, int j,String code, String name,String ver_no, String choice)throws Exception{
        FileWriter fw = new FileWriter(flight_seats,true);

        // If this is the first seat being booked in that flight (entry doesn't exists in the file)
        if(!code_exists(code) && choice.equals("book")) {

            fw.write(flightCode + "@" + seat_index(i, j) + ":" + c.username + "$" + name+"+"+ver_no);
            fw.close();

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
                    fileContent.set(k, temp +","+ seat_index(i, j) + ":" + c.username + "$" + name+"+"+ver_no); // Modifying it and rewriting to list
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

            Path path = Paths.get(current_dir + "\\flight_seats.txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));

            for (int k = 0; k < fileContent.size(); k++){
                if(fileContent.get(k).contains(seat_index(i,j))){
                    String flight_code = fileContent.get(k).split("@")[0];
                    //try{
                    String str = fileContent.get(k).split("@")[1];
                    String[] seats_user = str.split(",");
                    ArrayList<String> new_Seats  = new ArrayList<String>();

                    for(int m=0;m<seats_user.length;m++){
                        if(!seats_user[m].contains(seat_index(i,j)) && !(seats_user[m].split(Pattern.quote("$"))[1].split("[+]")[1].equals(ver_no))){
                            System.out.println(seats_user[m]);
                            new_Seats.add(seats_user[m]);
                        }
                    }

                    if(seats_user.length==new_Seats.size()){
                        System.out.println("Verification number didn't match");
                        System.out.println("Press C to try once more or Q to quit and go back to main page : ");
                        String t = "";
                        do {
                            t = br.readLine();
                            if(t.equals("C")){
                                cancel_seat(c,name,seat_index(i,j));
                            }else if(t.equals("Q")){
                                Main.Succesful_login(c);
                            }else{
                                System.out.println("Please enter a valid input");
                            }
                        }while(!t.equals("C")&&!t.equals("Q"));
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
    }


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

