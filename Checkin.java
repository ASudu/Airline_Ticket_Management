import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class Checkin{

    static Console cnsl = System.console();
    static String current_dir = System.getProperty("user.dir");

    public static int checkin(String user_name, String flight_code) throws Exception{

        int verification_no = Integer.parseInt(cnsl.readLine("Enter verification number you entered while booking: ").strip());

        try{
            
            Path path = Paths.get(current_dir + "\\flight_seats.txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            
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
                        if(username.equals(user_name)){

                            // Verification successful
                            if(verification == verification_no){

                                int baggage_weight = Integer.parseInt(cnsl.readLine("Enter baggage weight of customer " + user_name + ": ").strip());
                                
                                return baggage_weight;

                            }

                            // Verification failed
                            else{
                                System.out.println("Verification failed! Please check your verification number...");
                                return -1;
                            }

                        }

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