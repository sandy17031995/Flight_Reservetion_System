
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Searchmanager {
    FRS_Manager frsManager;
    public String Searchdate;
    public Flight result[][] = new Flight[30][3];
    ArrayList<String> spicejet;
    ArrayList<String> silkair;
    public ArrayList<Integer> TotalTime;
    public int seatsReq;
    public int cnt=0;
    Searchmanager(FRS_Manager frsm){
        this.frsManager = frsm;
        this.silkair = new ArrayList<>();
        this.spicejet = new ArrayList<>();
        this.TotalTime = new ArrayList<>();
        
    }
    public void searchFlight(String source,int Seats, int Date,int Month,int Day) throws IOException{
        this.seatsReq=Seats;
        this.Searchdate=Date+"/"+Month+"/2016";
        String Nextdate;
        int TIME=0;
        String monstr[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        String week[]={"DAILY","SUNDAY","MONdDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
        if(Date==31&&Month==10)
            Nextdate="1/11/2016";
        else
        {
                Nextdate=(Date+1)+"/"+Month+"/2016";
        }
        
        Flight f,f2;
        
        for(int i=0;i<frsManager.spicejet.size();i++)
        {
            
            f=frsManager.spicejet.get(i);
            if(f.depCity.substring(0,4).equalsIgnoreCase(source.substring(0,4)))
            {
                int m1=0,m2=0;
                String mon;
                
                int date1=(Integer.parseInt(f.effFrom.charAt(0)+"")*10 + Integer.parseInt(f.effFrom.charAt(1)+""));
                mon=(f.effFrom.substring(3,6));
                for(int p=0;p<12;p++){
                    if(mon.equalsIgnoreCase(monstr[p]))
                      m1=p+1;  
                }
                int date2=(Integer.parseInt(f.effTo.charAt(0)+"")*10 + Integer.parseInt(f.effTo.charAt(1)+""));
                mon=(f.effTo.substring(3,6));
                for(int p=0;p<12;p++){
                    if(mon.equalsIgnoreCase(monstr[p]))
                      m2=p+1;  
                }
                int year1,year2;
                year1=(Integer.parseInt(f.effFrom.charAt(7)+"")*10 + Integer.parseInt(f.effFrom.charAt(8)+""));
                year2=(Integer.parseInt(f.effTo.charAt(7)+"")*10 + Integer.parseInt(f.effTo.charAt(8)+""));
                
                int flag=0;
                if(year2==17){
                    if(Month>m1)
                        flag=1;
                    else if(Month==m1&&Date>=date1)
                        flag=1;
                } 
                else if(Month>m1 && Month<m2)
                    flag=1;
                else if(Month==m1&&m1==m2){
                    if(Date>=date1&&Date<=date2)
                        flag=1;
                }
                else if(Month==m1&&Date>=date1)
                    flag=1; 
                else if(Month==m2&&Date<=date2)
                    flag=1;
                
                int flag1=0;
                if(flag==1)
                {
                    
                    String actualday=week[Day];
                    for(int j=0;j<f.daysOfWeek.size();j++)
                    {
                        String weekday=f.daysOfWeek.get(j);
                        
                        if(weekday.equals(actualday) || weekday.equals("DAILY"))
                        {
                            flag1=1;
                            break;
                        }
                    }
                }
                if(flag1==1)
                {
                    String sTime=f.depTime,eTime=f.arrTime;
                    String actualday,effday;
                    int startTime,endTime,maxTime,minTime,lag,diffspice=0,diffsilk=0;
                    
                    int startHour=Integer.parseInt(sTime.charAt(0)+"")*10+Integer.parseInt(sTime.charAt(1)+"");
                    int startMin=Integer.parseInt(sTime.charAt(3)+"")*10+Integer.parseInt(sTime.charAt(4)+"");
                    int endHour=Integer.parseInt(eTime.charAt(0)+"")*10+Integer.parseInt(eTime.charAt(1)+"");
                    int endMin=Integer.parseInt(eTime.charAt(3)+"")*10+Integer.parseInt(eTime.charAt(4)+"");
                    startTime=startHour*60+startMin;
                    if(sTime.charAt(6)=='P')
                        startTime+=720;
                    
                    endTime=endHour*60+endMin;                        
                    if(eTime.charAt(6)=='P')
                        endTime+=720;
                    if(endTime<startTime)
                        endTime+=1440;
                    diffspice=endTime-startTime;
                    minTime=endTime+120;
                    maxTime=endTime+360;
                    
                    for(int k=0;k<16;k++)
                    {
                        f2=frsManager.silkair.get(k);
                        if(f.arrCity.substring(0,4).equalsIgnoreCase(f2.depCity.substring(0,4)))
                        {
                            
                            int silkStartTime = Integer.parseInt(f2.depTime);
                            silkStartTime = (silkStartTime/100)*60+(silkStartTime%100);
                            int silkEndTime=(Integer.parseInt(f2.arrTime.substring(0,4)));
                            silkEndTime=(silkEndTime/100)*60+(silkEndTime%100);
                            lag=silkStartTime-endTime;
                            diffsilk=silkEndTime-silkStartTime;            
                            if(maxTime<1440 && silkStartTime>minTime && silkStartTime<maxTime){
                                actualday=week[Day];
                                for(int j=0;j<f2.daysOfWeek.size()&&j<7;j++){
                                    String weekday=f2.daysOfWeek.get(j);
                                    if(weekday.equalsIgnoreCase(actualday.substring(0,3))){
                                        if(checkremark(f2.remark,Searchdate,k)&&frsManager.d.checkseats(f.flightNum, Searchdate, f2.flightNum, Searchdate)){
                                            addresult(f,Searchdate,f2,Searchdate,lag,diffspice,diffsilk);
                                        }
                                    }
                                }            
                            }
                            else if(minTime<1440&&maxTime>1440){
                                actualday=week[Day];
                                effday=week[(Day+1)%8==0?1:(Day+1)%8];
                                for(int j=0;j<f2.daysOfWeek.size()&&j<7;j++){
                                    String weekday=f2.daysOfWeek.get(j);
                                    
                                    if(silkStartTime>minTime){
                                        if(weekday.equalsIgnoreCase(actualday.substring(0,3))){
                                            if(checkremark(f2.remark,Searchdate,k)&&frsManager.d.checkseats( f.flightNum,Searchdate,f2.flightNum,Searchdate)){
                                                addresult(f,Searchdate,f2,Searchdate,lag,diffspice,diffsilk);
                                            }
                                        }
                                    }
                                    else if(silkStartTime<maxTime%1440){
                                        if(weekday.equalsIgnoreCase(effday.substring(0,3))){
                                            if(checkremark(f2.remark,Nextdate,k)&&frsManager.d.checkseats(f.flightNum, Searchdate, f2.flightNum, Nextdate)){
                                                addresult(f,Searchdate,f2,Nextdate,lag,diffspice,diffsilk);
                                            }
                                        }
                                    }     
                                }
                            }
                            else if(minTime>1440&&silkStartTime>minTime%1440&&silkStartTime<maxTime%1440){
                                effday=week[(Day+1)%8==0?1:(Day+1)%8];
                                for(int j=0;j<f2.daysOfWeek.size()&&j<7;j++){
                                    String weekday=f2.daysOfWeek.get(j);
                                    if(weekday.equalsIgnoreCase(effday.substring(0,3))){
                                        if(checkremark(f2.remark,Nextdate,k)&&frsManager.d.checkseats(f.flightNum, Searchdate, f2.flightNum, Nextdate)){
                                            addresult(f,Searchdate,f2,Nextdate,lag,diffspice,diffsilk);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    void addresult(Flight spice,String spdate,Flight silk,String sadate,int lag,int sptime,int sitime)
    {
        lag=(lag+1440)%1440;
        sptime=(sptime+1440)%1440;
        sitime=(sitime+1440)%1440;
        result[cnt][0]=spice;
        result[cnt][1]=silk;
        TotalTime.add(sptime+sitime+lag);
        spicejet.add(spdate);
        silkair.add(sadate);
        result[cnt][0].TotalTime=sptime;
        result[cnt][1].TotalTime=sitime;
        cnt++;
    }
    boolean checkremark(String r,String date,int p)
    {
        if(p==0)
            return true;
        if(p==1||p==3||p==5||p==7||p==9)
        {
            if((p!=7&&date.startsWith("30"))||date.startsWith("31"))
                return false;
            if(date.startsWith("11/11"))
                return false;
            return !(!date.startsWith("11")&&date.contains("11"));
        }
        if(p==2||p==4||p==6||p==8||p==10||p==12||p==15)
        {
            if(p!=8&&p!=15&&(date.startsWith("30"))||date.startsWith("31"))
                return true;
            if(date.contains("11/11"))
                return true;
            return !date.startsWith("11")&&date.contains("11");
        }
        if(p==13)
        {
            return date.startsWith("1/10")||date.startsWith("2/10")||date.startsWith("3/10");
        }
        if(p==14)
        {
            if(date.startsWith("1/10")||date.startsWith("2/10")||date.startsWith("3/10")||date.startsWith("4/10"))
                return false;
            else
            {
                if((date.startsWith("30"))||date.startsWith("31"))
                    return false;
                if(date.startsWith("11/11"))
                    return false;
                return !(!date.startsWith("11")&&date.contains("11"));
            }
        }
        if(p==11)
        {
            if(date.startsWith("12/10")||date.startsWith("19/10")||date.startsWith("26/10"))
                return false;
            if(date.startsWith("11/11"))
                return false;
            if(!date.startsWith("11")&&date.contains("11"))
                return false;
            return !(date.startsWith("28")||date.startsWith("29")||date.startsWith("30")||date.startsWith("31"));
        }
        return true;
    }
    public void sortFlight(){
        int i,j,temptime;
        String tempSilkDate,tempSpiceDate;
        Flight tempDomFlight,tempIntFlight;
        for(i=0;i<cnt-1;i++){
            for(j=0;j<cnt-i-1;j++){
                if(result[j][0].TotalTime+result[j][1].TotalTime>result[j+1][0].TotalTime+result[j+1][1].TotalTime){
                    tempDomFlight=result[j][0];
                    tempIntFlight=result[j][1];
                    result[j][0]=result[j+1][0];
                    result[j][1]=result[j+1][1];
                    temptime=TotalTime.get(j);
                    TotalTime.set(j,TotalTime.get(j+1));
                    TotalTime.set(j+1,temptime);
                    result[j+1][0]=tempDomFlight;
                    result[j+1][1]=tempIntFlight;
                    tempSilkDate=silkair.get(j);
                    silkair.set(j,silkair.get(j+1));
                    silkair.set(j+1,tempSilkDate);
                    tempSpiceDate=spicejet.get(j);
                    spicejet.set(j,spicejet.get(j+1));
                    spicejet.set(j+1,tempSpiceDate);
                }    
            }
        }
    }
}