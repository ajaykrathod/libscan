package com.dept;


import com.dept.Books.Books;
import com.dept.student.Student;


public final class App {
    private App() {
    }

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        dbConnection.setUserDatabase();
        dbConnection.setBooksDatabase();
        dbConnection.setInOutDatabase();
        dbConnection.setRetBorrowDatabase();

        // dbConnection.addStudent(new Student("121B2F1751", "Ajay Kisan Rathod", "ajay.rathod7521@pccoepune.org", new Vector<Books>()));
        // dbConnection.getAllStudents();
        // dbConnection.addStudent(new Student("prn", "name", "mail"));

        // dbConnection.addBook(new Books("JHBD367", "The Epic Shit", "Ankoor Warikoo"));
        // dbConnection.addBook(new Books("HJBEHJ376", "Harry Potter", "J. K. Rowling"));
        // dbConnection.getAllBooks();
        
        // dbConnection.decideInOut(new Student("121B2F1751"));
        // dbConnection.getStudent(new Student("121B2F1751"));
        // dbConnection.getStudentInOutRecord(new Student("121B2F1751"));
        
        dbConnection.decideRetBorrow(new Student("121B2F1751"), dbConnection.getBook(new Books("JHBD367")));
        dbConnection.getStudentRetBorrowRecord(new Student("121B2F1751"));
        
    }
}
