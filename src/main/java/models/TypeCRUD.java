/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edwin
 */
public class TypeCRUD extends Conexion {

    public boolean createType(Type type) throws SQLException {
        String sql = "INSERT INTO TYPES (NAME_TYPE) VALUES (?);";
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            ps.setString(1, type.getName());
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            con.rollback();
            return false;
        }
    }
    
    public boolean updateType(Type type) throws SQLException {
        String sql = "UPDATE TYPES SET NAME_TYPE=? WHERE COD_TYPE=?;";
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            ps.setString(1, type.getName());
            ps.setInt(2, type.getId());
            int rowsAffected = ps.executeUpdate();
            con.commit();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println(e);
            con.rollback();
            return false;
        }
    }
    
    public ArrayList<Type> getTypes() throws SQLException {
        ArrayList<Type> types = new ArrayList<>();
        try (Connection con = getConexion(); PreparedStatement stmt = con.prepareStatement("SELECT * FROM TYPES"); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("COD_TYPE");
                String name = rs.getString("NAME_TYPE");
                types.add(new Type(id, name));
            }
        }
        return types;
    }
    
     public int getIdType(String chain) throws SQLException {
        String sql = "SELECT COD_TYPE FROM TYPES WHERE NAME_TYPE='" + chain + "';";
        int id = 0;
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener la cantidad de prendas en la tabla GARMENTS.", ex);
        }
        return id;
    }
    
}
