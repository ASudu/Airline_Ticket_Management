import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class Checkin{

    static Console cnsl = System.console();
    static String current_dir = System.getProperty("user.dir");

    public int checkin(Customer c, String flight_code) throws Exception{

        int verification_no = Integer.parseInt(cnsl.readLine("Enter verification number you entered while booking: ").strip());

        try{
            
            Path path = Paths.get(current_dir + "\\flight_seats.txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            int flag = -100;

            
            // Reads file to check verification number
            for (int i = 0; i < fileContent.size(); i++) {
                
                String[] temp = fileContent.get(i).split("@");

                // Flight code matched
                if(temp[0].equals(flight_code)){

                    String[] users = temp[1].split(",");

                    for(String s: users){

                        String username = s.split(":")[1].split(Pattern.quote("$"))[0];
                        int verification = Integer.parseInt(s.split(":")[1].split("\\+")[0]);

                        // Username found
                        if(username.equals(c.username)){

                            // Verification successful
                            if(verification == verification_no){

                                flag = 1;
                                int baggage_weight = Integer.parseInt(cnsl.readLine("Enter baggage weight of customer " + c.username + ": ").strip());
                                int baggage_charge = (baggage_weight > 25)? (baggage_weight - 25)*100 : 0;

                                if(baggage_charge > 0)
                                    System.out.println("You'll be charged extra Rs." + baggage_charge);

                                return baggage_charge;

                            }

                            // Verification failed
                            else{
                                System.out.println("Verification failed! Please check your verification number...");
                                return -1;
                            }

                        }

                    }

                    // If user doesn't exist in that flight
                    if(flag == -100){
                        System.out.println("Bookinf under this username doesn't exists");
                        return -1;
                    }

                    


                }
            }
            

        }
        
        catch(Exception e){
            e.printStackTrace();
        }

        return -1;
        
    }
}