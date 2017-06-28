
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class FRS_Manager {
    
    Datamanager d;
    Bookingmanager b;
    Displaymanager disp;
    Searchmanager s;
    public ArrayList<Flight> spicejet;
    public ArrayList<Flight> silkair; 
    
    public static void main(String[] args)  {
        
        try {
            FRS_Manager frsManager= new FRS_Manager();
            frsManager.initialize();
            if(args[0].equalsIgnoreCase("abc.txt"))
            {
                System.out.println("File not found");
                System.exit(0);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(FRS_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void initialize() throws IOException,FileNotFoundException
    {
        FRS_Manager frsManager= new FRS_Manager();
        frsManager.d = new Datamanager(frsManager);
        frsManager.spicejet = frsManager.d.readspicejet();
        frsManager.silkair = frsManager.d.readsilkair();
        frsManager.disp = new Displaymanager(frsManager);
        frsManager.disp.displayscreen1();
        frsManager.s = new Searchmanager(frsManager);
        frsManager.b = new Bookingmanager(frsManager);
        
    }
}