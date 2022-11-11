package dept.student;

import java.util.Vector;


public class Student {
    private String prn;
    private String name;
    private String mail;
    private String contact;
    private boolean in;
    private Vector<String> books;

    
    public Student(String prn) {
        this.prn = prn;
    }
    
    public Student(){
        
    }
    public Student(String prn, String name, String mail,Vector<String> books,String contact) {
        this.prn = prn;
        this.name = name;
        this.mail = mail;
        this.books = books;
        this.contact = contact;
        this.in = false;
        System.out.println(this.prn+this.name+this.mail+this.books+this.contact+this.in);
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
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }    
    

    @Override
    public String toString() {
        return "Student books=" + books + ", isIn=" + in + ", mail=" + mail + ", name=" + name
                + ", prn=" + prn + ", Contact=" + contact;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean isIn) {
        this.in = isIn;
    }

    public Vector<String> getBooks() {
        return books;
    }

    public void setBooks(Vector<String> books) {
        this.books = books;
    }
}
