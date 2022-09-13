package com.dept;

import com.dept.Student.Student;


public final class App {
    private App() {
    }

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        // dbConnection.addStudent(new Student("prn", "name", "mail"));
    }
}
