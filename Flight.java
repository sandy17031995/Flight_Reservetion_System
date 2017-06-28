
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
public class Flight {
    FRS_Manager frsManager;
    String depCity;
    String arrCity;
    ArrayList<String> daysOfWeek;
    String flightNum;
    String depTime;
    String arrTime;
    String effFrom;
    String effTo;
    String via; 
    String remark;
    int TotalTime;
    Flight(String fn,String d,String a,ArrayList<String> dow,String dt,String at,String ef,String et,String v){
        depCity = d;
        arrCity = a;
        daysOfWeek = dow;
        flightNum = fn;
        depTime = dt;
        arrTime = at;
        effFrom = ef;
        effTo = et;
        via=v;
    }
    Flight(String fn,String d,ArrayList<String> dow,String dt,String at,String r){
        depCity = d;
        daysOfWeek = dow;
        flightNum = fn;
        depTime = dt;
        arrTime = at;
        remark=r;
    }
}
