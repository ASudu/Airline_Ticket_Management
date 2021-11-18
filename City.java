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
    final static String Home_city = "Delhi";
    // Map<String, Point> temp = new HashMap<String, Point>();
    // temp.add_City("Delhi", new Point(0,0));
    static Map<Object, Object> cities = Stream.of(new Object[][] { 
        { "Delhi", new Point(0,0) }, 
        { "Mumbai", new Point(-2000,-4000) },
        { "Kolkata", new Point(2000,-3000)},
        { "Bhubaneshwar", new Point(1500,-3500)},
        { "Chennai", new Point(1000,-5000)},
        { "Jharkhand", new Point(1000,-3000)},
        { "Nagpur", new Point(-1500,-4000)},
        { "Srinagar", new Point(0,500)},
        { "Bhopal", new Point(0,-2000)},
        { "Gangtok", new Point(500,0)}
    }).collect(Collectors.collectingAndThen(
        Collectors.toMap(data -> data[0], data -> data[1]), 
        Collections::<Object, Object> unmodifiableMap));

        // void print_city(){
        //  ((Point) cities.get("Delhi")).get_coord();
        // }        

    // void add_City(String s, Point p){
    //     this.temp.put(s,p);
    // }

    int cal_dist(String from, String to){
        Point p1 = (Point) cities.get(from);
        Point p2 = (Point) cities.get(to);

        int dist = (int) Math.sqrt(Math.pow((double)p1.x - p2.x, 2) + Math.pow((double)p1.y - p2.y, 2));

        return dist;
    }
    
    void construct_file(){

        // File city_file = new File(current_dir + "\\cities.txt");
        PrintWriter pw = null;

        try{
            pw = new PrintWriter(new FileWriter("cities.txt", true));



            Set<Object> keys = cities.keySet();
            String[] arr = new String[10];
            int i = 0;
            for(Object s:keys){
                arr[i] = s.toString();
                // System.out.println(arr[i]);
                i++;
            }

            for(int k=0;k<arr.length;k++){
                for (int j=k+1;j<arr.length;j++ ){
                    pw.write(arr[k]+","+arr[j]+","+Integer.toString(this.cal_dist(arr[k], arr[j]))+"\n");
                }
            }

            pw.flush();
            pw.close();
            // fw.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }


    }

    public static void display_list_of_cities(){

        BufferedReader br = null;

        try{

            FileReader fr = new FileReader("cities.txt");
            br = new BufferedReader(fr);

            String[] display = new String[3];
            String str;

            System.out.println("-------------------------");
            System.out.println("From           To");
            System.out.println("-------------------------");
            while((str = br.readLine()) != null){
                display = str.split(",");
                String space = "";
                for(int i=0;i<13-display[0].length(); i++)
                    space += " ";   
                System.out.println(display[0]+ space + "|" + display[1]);
            }
            System.out.println("-------------------------");

            br.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    


    
}

class Point extends Object{

    int x;
    int y;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void get_coord(){
        System.out.println(Integer.toString(this.x)+ "," + Integer.toString(this.y));
    }

}
 class Driver{
     public static void main(String[] args){
        //  c.construct_file();
        City.display_list_of_cities();
     }
 }