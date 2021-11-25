import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.*;

public class LogFile{
    static String current_dir = System.getProperty("user.dir");
    static Logger logger = Logger.getLogger("MyLog");
    static FileHandler fh;

    // Adds log for customer operations
    public void append(Customer c, String str)throws Exception{

        try{
            fh = new FileHandler(current_dir+"\\logfile.txt",true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setLevel(Level.FINER);
        }
        
        catch (SecurityException e) {  
            e.printStackTrace();  
        } 
        catch (IOException e) {  
            e.printStackTrace();  
        }

        // Call finer method
        logger.finer(c.username+" :: "+str);
        
    }

    // Adds log for staff operations
    public void append(Staff s, String str)throws Exception{

        try{
            fh = new FileHandler(current_dir+"\\logfile.txt",true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setLevel(Level.FINER);
        }

        catch (SecurityException e) {  
            e.printStackTrace();  
        } 
        catch (IOException e) {  
            e.printStackTrace();  
        }

        // Call finer method
        logger.finer(s.username+" :: "+str);
        

    }

    // Adds log in case error arises (Login failed, etc.)
    public void append(String str)throws Exception{

        try{
            fh = new FileHandler(current_dir+"\\logfile.txt",true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setLevel(Level.FINER);
        }

        catch (SecurityException e) {  
            e.printStackTrace();  
        } 
        catch (IOException e) {  
            e.printStackTrace();  
        }

        // Call finer method
        logger.finer("--!!--"+" :: "+str);
        
    }

}
