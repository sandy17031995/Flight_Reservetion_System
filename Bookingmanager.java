
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Bookingmanager {
    FRS_Manager frsManager;
    Bookingmanager(FRS_Manager frsm){
        this.frsManager = frsm;
    }
    int bookingnum;
    public void bookticket(String name,int key) throws IOException{
        bookingnum=frsManager.d.checkbookingnum()+1;
        frsManager.d.writebooking(name,bookingnum,
        frsManager.s.result[key][0].flightNum,
        frsManager.s.spicejet.get(key),
        frsManager.s.result[key][1].flightNum,
        frsManager.s.silkair.get(key),
        frsManager.s.seatsReq);
        
    }
}
