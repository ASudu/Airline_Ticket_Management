import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class Checkin{

    //static Console cnsl = System.console();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String current_dir = System.getProperty("user.dir");

    public static int checkin(String user_name, String flight_code) throws Exception{
        System.out.println("Enter verification number you entered while booking: ");



        try{
            String verification_no = br.readLine().strip();

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
                        String verification = s.split(":")[1].split("[+]")[1];

                        // Username found
                        if(username.equals(user_name)){

                            // Verification successful
                            if(verification.equals(verification_no)){

                                System.out.println("Enter baggage weight of customer " + user_name + ": ");
                                int baggage_weight = Integer.parseInt(br.readLine().strip());

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

            System.out.println("Please be consistent with input format");
            System.out.println("If you wish to continue, Press C to continue or E to exit");
            String choice = br.readLine();
            if(choice.equals("C")){
                checkin(user_name,flight_code);
            }else{
                System.exit(0);
            }
        }

        return -1;

    }
}
