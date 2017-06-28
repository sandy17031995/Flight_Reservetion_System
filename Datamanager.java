
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Datamanager {
    
    FRS_Manager frsManager;
    Datamanager(FRS_Manager frs){
        this.frsManager=frs;
    }
    
    public ArrayList<Flight> readspicejet() throws IOException,FileNotFoundException{
        ArrayList<Flight> domFlight = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("2016.spicejet.csv"));
        String str;
        for(int i=0;i<5;i++)
            str=br.readLine();
        
        while((str = br.readLine())!=null){
            StringTokenizer st = new StringTokenizer(str, "|");
            String origin = st.nextToken();
            String dest = st.nextToken();
            String days = st.nextToken();
            StringTokenizer daysST= new StringTokenizer(days, ", ");
            ArrayList<String> frequency = new ArrayList<>();
            while(daysST.hasMoreTokens())
                frequency.add(daysST.nextToken());
            String flightNum = st.nextToken();
            String depTime = st.nextToken();
            String arrTime = st.nextToken();
            String via = st.nextToken();
            String from =st.nextToken();
            String till = st.nextToken();
            Flight tempFlight = new Flight(flightNum, origin, dest,frequency, depTime, arrTime, from, till,via);
            domFlight.add(tempFlight);
        }
        return domFlight;
    }
    
    public ArrayList<Flight> readsilkair() throws IOException,FileNotFoundException{
        ArrayList<Flight> intFlight = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("2016.silkair.csv"));
        String str;
        for(int i=0;i<3;i++)
            str=br.readLine();
        while((str = br.readLine())!=null){
            StringTokenizer st = new StringTokenizer(str, "|");
            String origin = st.nextToken();
            String dest = "SINGAPORE";
            String days = st.nextToken();
            StringTokenizer daysST= new StringTokenizer(days, ",");
            ArrayList<String> frequency = new ArrayList<>();
            while(daysST.hasMoreTokens())
                frequency.add(daysST.nextToken());
            String flightNO = st.nextToken();
            String temp = st.nextToken();
            daysST = new StringTokenizer(temp, "/");
            String depTime = daysST.nextToken();
            String arrTime = daysST.nextToken();
            String remark;
            if(st.hasMoreTokens()){ 
                remark = st.nextToken();
            } else remark = "";
            Flight tempFlight = new Flight(flightNO, origin, frequency, depTime, arrTime, remark);
            intFlight.add(tempFlight);
        }
        
        return intFlight;
    }
    public boolean checkseats(String spice,String spdate,String silkAir,String sadate) throws FileNotFoundException , IOException{
        BufferedReader br = new BufferedReader(new FileReader("bookedSeats.csv"));
        String x= br.readLine();
        String spicejet,spicedate,silkdate,silkair;
        int silkReservedSeats=0;
        int spiceReservedSeats=0;
        while(x!=null) 
        {
            StringTokenizer st = new StringTokenizer(x,"|");
            spicejet=st.nextElement().toString();
            spicejet=st.nextElement().toString();
            spicejet=st.nextElement().toString();
            spicedate=st.nextElement().toString();
            silkair=st.nextElement().toString();
            silkdate=st.nextElement().toString();
            int seats = Integer.parseInt(st.nextElement().toString());
            
            if(spicejet.equals(spice) && spdate.equals(spicedate))
                spiceReservedSeats+=seats;
            if(silkair.equals(silkAir) && sadate.equals(silkdate))
                silkReservedSeats+=seats;
            x=br.readLine();
        }
        br.close();
        if((spiceReservedSeats+this.frsManager.s.seatsReq)<=15 && (silkReservedSeats+this.frsManager.s.seatsReq)<=15)
            return true;
        else
            return false;
    }
    public void writebooking(String name,int bookingnum,String spice,String spicedate,String silkair,String silkdate,int seats) throws FileNotFoundException, IOException{
        File booked = new File("bookedSeats.csv");
        String newline=name+"|"+bookingnum+"|"+spice+"|"+spicedate+"|"+silkair+"|"+silkdate+"|"+seats;
        BufferedWriter br =new BufferedWriter(new FileWriter("bookedSeats.csv",true));
        br.write(newline);
        br.newLine();
        br.flush();
        br.close();
        
    }
    public int checkbookingnum() throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader("bookedSeats.csv"));
        String x= br.readLine();
        String y=x;
        if(y==null)
            return 1000;
        while(x!=null)
        {
            y=x;
            x=br.readLine();
        }
        StringTokenizer st = new StringTokenizer(y,"|");
            x=st.nextElement().toString();
            x=st.nextElement().toString();
        int p=Integer.parseInt(x);
        return p;
    }
}
