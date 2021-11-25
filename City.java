// import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.Math;

public class City {

    static String current_dir = System.getProperty("user.dir");
    final static String Home_city = "Delhi"; // Home city
    private String name;
    private String airport;
    private Point coordinates;

    // Default constructor
    City(){

        // All members are read-only
        this.name = this.Home_city;
        this.coordinates = new Point();
        this.airport = "DEL";
    }

    // Parameterized constructor
    City(String name, String airport, Point point){
        this.name = name;
        this.airport = airport;
        this.coordinates = point;
    }

    // Objects of the 10 cities as members to be further used for an external object of class
    static City c1 = new City(); // For Delhi
    static City c2 = new City("Mumbai", "BOM", new Point(-2000,-4000));
    static City c3 = new City("Kolkata", "KOL", new Point(2000,-3000));
    static City c4 = new City("Bhubaneshwar", "BBI", new Point(1500,-3500));
    static City c5 = new City("Chennai", "MAA", new Point(1000,-5000));
    static City c6 = new City("Hyderabad", "HYD", new Point(1500,-4000));
    static City c7 = new City("Pune", "PNQ", new Point(-1500,-4000));
    static City c8 = new City("Srinagar", "SXR", new Point(0,500));
    static City c9 = new City("Bhopal", "BHO", new Point(0,-2000));
    static City c10 = new City("Chandigarh", "IXE", new Point(-200,0));


    // Map that has: key - City name, value - Coordinates (home city at origin)
    static Map<Object, Object> cities = Stream.of(new Object[][] {
            { c1, c1.coordinates },
            { c2, c2.coordinates },
            { c3, c3.coordinates},
            { c4, c4.coordinates},
            { c5, c5.coordinates},
            { c6, c6.coordinates},
            { c7, c7.coordinates},
            { c8, c8.coordinates},
            { c9, c9.coordinates},
            { c10, c10.coordinates}
    }).collect(Collectors.collectingAndThen(
            Collectors.toMap(data -> data[0], data -> data[1]),
            Collections::<Object, Object> unmodifiableMap));

    //--------------------------------------------Getters and setters---------------------------------------------//
            // Get city name
    public String getName() {
        return this.name;
    }

    // Get airport code of the city
    public String getAirport() {
        return this.airport;
    }

    // Get location of the city
    public Point getCoordinates() {
        return this.coordinates;
    }
    //------------------------------------------------------------------------------------------------------------//

    // Calculates and returns the distance between a given pair of cities
    static int cal_dist(String from, String to){
        Point p1 = (Point) cities.get(from);
        Point p2 = (Point) cities.get(to);

        int dist = (int) Math.sqrt(Math.pow((double)p1.x - p2.x, 2) + Math.pow((double)p1.y - p2.y, 2));

        return dist;
    }

    // Creates cities.txt file
    // Currently cities.txt has content of all 10 cities and distance between each other (45 rows)
    // To run this, clear exisiting contents of cities.txt, add a driver class with main function
    // and call this function in a static way (City.construct file)
    static void construct_file(){

        PrintWriter pw = null;

        try{
            pw = new PrintWriter(new FileWriter("cities.txt", true));

            Set<Object> keys = cities.keySet();
            String[] arr = new String[10];
            int i = 0;
            for(Object s:keys){
                arr[i] = s.toString();
                i++;
            }

            for(int k=0;k<arr.length;k++){
                for (int j=k+1;j<arr.length;j++ ){
                    pw.write(arr[k]+","+arr[j]+","+Integer.toString(cal_dist(arr[k], arr[j]))+"\n");
                }
            }

            pw.flush();
            pw.close();

            System.out.println("Cities file created!");
        }
        catch(Exception e){
            System.out.println(e.toString());
        }


    }

    // Displays the list of cities from and to each city in a formatted way
    public static void display_list_of_cities(){

        BufferedReader br = null;

        try{

            FileReader fr = new FileReader("cities.txt");
            br = new BufferedReader(fr);

            String[] display = new String[3];
            String str;
            int row = 1;

            System.out.println("-----------------------------------------");
            System.out.println("From              To");
            System.out.println("-----------------------------------------");

            // Read file
            while((str = br.readLine()) != null){
                display = str.split(",");
                String space = "";
                for(int i=0;i<13-display[0].length(); i++)

                    space += " ";

                System.out.println(row + ". " + display[0]+ space + "|  " + display[1] + "\n");
                row++;
            }
            System.out.println("-----------------------------------------");

            br.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    // Returns the city object while booking to extract airport code etc.
    public City assign_city(String city_name){

        ArrayList<City> city = new ArrayList<>();
        city.add(c1);
        city.add(c2);
        city.add(c3);
        city.add(c4);
        city.add(c5);
        city.add(c6);
        city.add(c7);
        city.add(c8);
        city.add(c9);
        city.add(c10);

        for(City c: city){
            if(c.name.equals(city_name))
                return c;
        }

        return c1; // If city name is not valid then returns home city
    }

}

// Class for handling city locations in terms of coordinates
class Point extends Object{

    int x;
    int y;


    // Default constructor
    public Point() {

        this.x = 0;
        this.y = 0;
    }
    
    // Parameterized constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Prints the x and y coordinates of the point object
    void get_coord(){
        System.out.println(Integer.toString(this.x)+ "," + Integer.toString(this.y));
    }

}


