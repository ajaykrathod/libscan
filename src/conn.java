/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author HP
 */
import java.sql.*;
public class conn {

    /**
     * @param args the command line arguments
     */
   public Connection c;
    public Statement s;
 
    public conn(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:mysql:///mydatabase","root","ajayrathod");
            s = c.createStatement();
            
        
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
