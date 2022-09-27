package com.dept.RetBorrow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


public class RetBorrow {
    private String prn;
    private Vector<String> isbn;
    private String operation;
    private String date;
    private String time; 
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date dt = new Date();
    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    

   
    public RetBorrow() {
    }

    public RetBorrow(String prn, Vector<String> isbn, String operation) {
        this.prn = prn;
        this.isbn = isbn;
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
    public Vector<String> getIsbn() {
        return isbn;
    }
    public void setIsbn(Vector<String> isbn) {
        this.isbn = isbn;
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

    public String getTime() {
        return time;
    }

    public void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dt = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        this.date = dateFormat.format(dt);
        this.time = timeFormat.format(dt);
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setDate(String date){
        this.date = date;
    }

    @Override
    public String toString() {
        return "RetBorrow [date=" + date + ", isbn=" + isbn + ", operation=" + operation + ", prn=" + prn + ", time="
                + time + "]";
    }

    
}
