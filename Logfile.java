// import java.util.*;
// import java.io.*;
// import java.lang.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogFile{
    static String current_dir = System.getProperty("user.dir");
//     static File logfile = new File(current_dir + "\\logfile_user.txt");
    static Logger logger = Logger.getLogger("MyLog");
    static FileHandler fh;
    public void append(Customer c, String str)throws Exception{

        fh = new FileHandler(current_dir+"\\logfile.txt",true);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setLevel(Level.FINER);

        // Call finer method
        logger.finer(c.username+" :: "+str);
        System.out.print("\033[H\033[2J");
        System.out.flush();


    }
    public void append(Staff s, String str)throws Exception{

        fh = new FileHandler(current_dir+"\\logfile.txt",true);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setLevel(Level.FINER);

        // Call finer method
        logger.finer(s.username+" :: "+str);
        System.out.print("\033[H\033[2J");
        System.out.flush();


    }
    public void append(String str)throws Exception{

        fh = new FileHandler(current_dir+"\\logfile.txt",true);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setLevel(Level.FINER);

        // Call finer method
        logger.finer("--!!--"+" :: "+str);
        System.out.print("\033[H\033[2J");
        System.out.flush();


    }
    //will create a staff log_file append as well


}
