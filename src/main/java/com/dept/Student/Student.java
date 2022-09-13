package com.dept.Student;

import java.util.Arrays;
import java.util.Vector;

import com.dept.Books.Books;

public class Student {
    private String prn;
    private String name;
    private String mail;
    private String roll;
    private boolean isIn;
    private Vector<Books> books;
    
    public Student(String prn) {
        this.prn = prn;
    }

    public Student(String prn, String name, String mail,Vector<Books> books) {
        this.prn = prn;
        this.name = name;
        this.mail = mail;
        this.books = books;
    }
    
    public String getPrn() {
        return prn;
    }
    public void setPrn(String prn) {
        this.prn = prn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getRoll() {
        return roll;
    }
    
    public void setRoll(String roll) {
        this.roll = roll;
    }    
    

    @Override
    public String toString() {
        return "Student books=" + books.toString() + ", isIn=" + isIn + ", mail=" + mail + ", name=" + name
                + ", prn=" + prn + ", roll=" + roll;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean isIn) {
        this.isIn = isIn;
    }

    public Vector<Books> getBooks() {
        return books;
    }

    public void setBooks(Vector<Books> books) {
        this.books = books;
    }
}
