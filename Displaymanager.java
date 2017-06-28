
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Displaymanager {
    Screen1 s1;
    Screen2 s2;
    Screen3 s3;
    Screen4 s4;
     
    FRS_Manager frsManager;
    
    public Displaymanager(FRS_Manager frsm){
        this.frsManager = frsm;
    }
    
    public void displayscreen1(){
        this.s1=new Screen1(this);
        this.s1.setVisible(true);
    }
    
    public void displayscreen2() throws IOException{
        this.s2=new Screen2(this);
        
        DefaultTableModel dm=new DefaultTableModel();
        dm=(DefaultTableModel) Screen2.jTable1.getModel();
        
        int flightcnt=0;
        String source,month;
        int date=0,seats=0;
        source=s1.getjComboBox1().getSelectedItem().toString();
        seats=Integer.parseInt(s1.getjSpinner1().getValue().toString());
        date=Integer.parseInt(s1.getjComboBox3().getSelectedItem().toString());
        
        month=s1.getjComboBox2().getSelectedItem().toString();
        s2.source.setText("Source:"+source);
        s2.seats.setText("Seats:"+(String.valueOf(seats)));
        s2.date.setText("Date Of Booking : "+s1.getjComboBox3().getSelectedItem().toString()+"-"+month+"-"+"2016");
        int day=5+date,m=10;
        if(month.equalsIgnoreCase("November")){
            day+=31;
            m++;
        }  
        day%=7;
        frsManager.s.searchFlight(source, seats, date,m, day+1);
        frsManager.s.sortFlight();
        
        Screen2.jTable1.setRowHeight(50);
            
        flightcnt=frsManager.s.cnt;
        if(flightcnt==1)
            Screen2.jTable1.setRowHeight(100);
        s2.count.setText("Number Of Flight Available : "+frsManager.s.cnt);
           
        for(int i=0;i<frsManager.s.cnt;i++)
        {
            
            String spiceflight=frsManager.s.result[i][0].flightNum;
            String via=frsManager.s.result[i][0].arrCity;
            String spicetime=frsManager.s.result[i][0].depTime+" to "+frsManager.s.result[i][0].arrTime;
            String silkflight=frsManager.s.result[i][1].flightNum;
            String silktime=frsManager.s.result[i][1].depTime+" to "+frsManager.s.result[i][1].arrTime.substring(0,4);
            int flighttime=frsManager.s.result[i][0].TotalTime+frsManager.s.result[i][1].TotalTime;
            int transit=frsManager.s.TotalTime.get(i)-flighttime;
            String[] row = {spiceflight,via,spicetime,silkflight,silktime,flighttime/60+"hrs "+flighttime%60+"min",transit/60+"hrs "+transit%60+"min"};
            dm.addRow(row);
        }
        
        if(frsManager.s.cnt==0)
        {
           s2.jScrollPane1.setVisible(false);
           s2.oo.setVisible(true);
           s2.jButton1.setVisible(false);
        }
        else
        {   
            Screen2.jTable1.setColumnSelectionAllowed(false);
            Screen2.jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Screen2.jTable1.setRowSelectionInterval(0,0);
        
        }
        this.s2.setVisible(true);
    }
    
    public void displayscreen3(){
        this.s3=new Screen3(this);
        this.s3.setVisible(true);
        int key;
        key=Screen2.jTable1.getSelectedRow();
        s3.spiceflight.setText("Spicejet Flight : "+(frsManager.s.result[key][0].flightNum));
        s3.via.setText("Via : "+(frsManager.s.result[key][0].arrCity));
        s3.spicetime.setText("Spicejet Time : "+(frsManager.s.result[key][0].depTime+" to "+frsManager.s.result[key][0].arrTime));
        s3.silkflight.setText("Silkair Flight : "+(frsManager.s.result[key][1].flightNum));
        s3.silktime.setText("Silkair Time : "+(frsManager.s.result[key][1].depTime+" to "+frsManager.s.result[key][1].arrTime.substring(0,4)));
      int time=frsManager.s.TotalTime.get(key);
      int hr=time/60;
      int min=time%60;
        s3.totaltime.setText("Total Time : "+String.valueOf(hr)+"hrs"+String.valueOf(min)+"min");
    }
    
    public void displayscreen4() throws IOException{
        this.s4=new Screen4(this);
        String name=s3.getjTextField1().getText();
        int key1=Screen2.jTable1.getSelectedRow();
        frsManager.b.bookticket(name, key1);
        this.s4.setVisible(true);
        s4.spiceflight.setText("Spicejet Flight : "+(frsManager.s.result[key1][0].flightNum));
        s4.via.setText("Via : "+(frsManager.s.result[key1][0].arrCity));
        s4.spicetime.setText("Spicejet Time : "+(frsManager.s.result[key1][0].depTime+" to "+frsManager.s.result[key1][0].arrTime));
        s4.silkflight.setText("Silkair Flight : "+(frsManager.s.result[key1][1].flightNum));
        s4.silktime.setText("Silkair Time : "+(frsManager.s.result[key1][1].depTime+" to "+frsManager.s.result[key1][1].arrTime.substring(0,4)));
        int time=frsManager.s.TotalTime.get(key1);
        int hr=time/60;
        int min=time%60;
        s4.totaltime.setText("Total Time : "+String.valueOf(hr)+"hrs"+String.valueOf(min)+"min");
        String source=s1.getjComboBox1().getSelectedItem().toString();
        s4.source.setText("Source:"+source);
        s4.name.setText("Name : "+name);
        int seats=Integer.parseInt(s1.getjSpinner1().getValue().toString());
        s4.seats.setText("Seats:"+(String.valueOf(seats)));
        String  month=s1.getjComboBox2().getSelectedItem().toString();
        s4.date.setText("Date Of Booking : "+s1.getjComboBox3().getSelectedItem().toString()+"-"+month+"-"+"2016");
    }
}
