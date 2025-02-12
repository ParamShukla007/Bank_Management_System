/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank.manegement.system;

/**
 *
 * @author PARAM
 */
import java.sql.*;

public class Conn {
    
    Connection c;
    Statement s;
    public Conn()
    {
        try{
            //no need to write the below line, the driver is already registered by Java
            //Class.forName(com.mysql.cj.jdbc.Driver);
            //creating connection
            c = DriverManager.getConnection("jdbc:mysql:///bankmanagementsystem","root","param09@P");
            //using statement s we will execute queries
            s = c.createStatement();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
    
}
