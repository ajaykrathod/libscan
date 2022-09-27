package com.dept.InOut;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InOut {
    private String prn;
    private String operation;
    private String date;
    private String time; 
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date dt = new Date();
    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    
    
    public InOut() {
    }

    public InOut(String prn, String operation) {
        this.prn = prn;
        this.operation = operation;
        this.date = dateFormat.format(dt);
        this.time = timeFormat.format(dt);
    }

    public String getPrn() {
        return prn;
    }
    public void setPrn(String prn) {
        this.prn = prn;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getDate() {
        return date;
    }
    public void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dt = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        this.date = dateFormat.format(dt);
        this.time = timeFormat.format(dt);
    }
    
    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "InOut [date=" + date + ", operation=" + operation + ", prn=" + prn + ", time=" + time + "]";
    }

}
