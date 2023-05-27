/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author edwin
 */
public class Conexion {
    
    Connection con = null;

    String base = "belier"; 
    String url = "jdbc:mysql://localhost:3306/" + base; 
    String user = "root"; 
    String password = "strange2404";

    public Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("En Linea!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return con;
    }
    
    public void close() throws SQLException{
        if (con != null) {
            if (!con.isClosed()) {
                con.close();
                System.out.println("Fuera de Linea!");
            }
        }
    }
    
}
