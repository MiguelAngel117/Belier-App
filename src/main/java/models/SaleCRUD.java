/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author edwin
 */
public class SaleCRUD extends Conexion {
    
    public boolean create(Sale sale) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO SALES (COD_GARMENT, DATE_TIME_SALE, UNIT_PRICE_SALE, QUANTITY_SALE, TOTAL_SALE) VALUES (?, ?, ?, ?, ?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, sale.getGarment());
            
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);
            
            ps.setString(2, formattedDate);
            ps.setString(3, sale.getUnitPrice());
            ps.setString(4, sale.getQuantity());
            ps.setString(5, sale.getTotal());
            ps.execute();
            new GarmentCRUD().decrementStock(sale.getGarment(), Integer.parseInt(sale.getQuantity()));
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean update(Sale sale) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE SALES SET QUANTITY_SALE=?, TOTAL_SALE=?, COMENTARIES_SALE=? WHERE COD_SALE=?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, sale.getQuantity());
            ps.setString(2, sale.getTotal());
            ps.setString(3, sale.getComentaries());
            ps.setInt(4, sale.getId());
            ps.execute();
            new GarmentCRUD().updateStock(sale.getGarment(), sale.getRefund());
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean delete(int cod) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM SALES WHERE COD_SALE = ?;";

        try {
            Sale sale = getSale(cod);
            new GarmentCRUD().updateStock(sale.getGarment(), Integer.parseInt(sale.getQuantity()));
            ps = con.prepareStatement(sql);
            ps.setInt(1, cod);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public ArrayList<Sale> getSales() throws SQLException{
        ArrayList<Sale> sales = new ArrayList<>();
        Connection con = getConexion();
        java.sql.Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT S.*, NAME_GARMENT FROM SALES S JOIN GARMENTS G ON S.COD_GARMENT = G.COD_GARMENT;");
        while (rs.next()) {
            int id = rs.getInt("COD_SALE");
            String garment = rs.getString("COD_GARMENT");
            String nameGarment = rs.getString("NAME_GARMENT");
            String dateTime = rs.getString("DATE_TIME_SALE");
            String unitPrice = rs.getString("UNIT_PRICE_SALE");
            String quantity = rs.getString("QUANTITY_SALE");
            String total = rs.getString("TOTAL_SALE");
            String comentaries = rs.getString("COMENTARIES_SALE");
            sales.add(new Sale(id, garment, nameGarment, dateTime, unitPrice, quantity, total, comentaries));
        }
        return sales;
    }
    
    public ArrayList<Sale> searchSales(String search) throws SQLException {
        String sql = "SELECT S.*, NAME_GARMENT FROM SALES S JOIN GARMENTS G ON S.COD_GARMENT = G.COD_GARMENT "
                + "WHERE COD_SALE LIKE ? OR NAME_GARMENT LIKE ? OR DATE_TIME_SALE LIKE ? "
                + "OR UNIT_PRICE_SALE LIKE ? OR QUANTITY_SALE LIKE ? OR TOTAL_SALE LIKE ? "
                + "OR COMENTARIES_SALE LIKE ?;";
        ArrayList<Sale> sales = new ArrayList<>();
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            search = "%" + search + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);
            ps.setString(5, search);
            ps.setString(6, search);
            ps.setString(7, search);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("COD_SALE");
                    String garment = rs.getString("COD_GARMENT");
                    String nameGarment = rs.getString("NAME_GARMENT");
                    String dateTime = rs.getString("DATE_TIME_SALE");
                    String unitPrice = rs.getString("UNIT_PRICE_SALE");
                    String quantity = rs.getString("QUANTITY_SALE");
                    String total = rs.getString("TOTAL_SALE");
                    String comentaries = rs.getString("COMENTARIES_SALE");
                    sales.add(new Sale(id, garment, nameGarment, dateTime, unitPrice, quantity, total, comentaries));
                }
            }
        }
        return sales;
    }
    
    public int getQuantity() throws SQLException {
        String sql = "SELECT COUNT(*) FROM SALES";
        int quantity = 0;
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener la cantidad.", ex);
        }
        return quantity;
    }
    
    public int getNextId() throws SQLException {
        String sql = "SELECT MAX(COD_SALE) FROM SALES;";
        int quantity = 0;
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener la cantidad.", ex);
        }
        return quantity + 1;
    }
    
    public boolean isExistSale(int id) throws SQLException {
        String sql = "SELECT 1 FROM SALES WHERE COD_SALE = ?";
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new SQLException("Error al verificar si existe una venta con el ID " + id, e);
        }
    }
    
    public Sale getSale(int idSale) throws SQLException {
        String sql = "SELECT P.*, G.NAME_GARMENT FROM SALES P JOIN GARMENTS G ON P.COD_GARMENT = G.COD_GARMENT WHERE P.COD_SALE = ?;";
        try (Connection conn = getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSale);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("COD_SALE");
                    String garmentId = rs.getString("COD_GARMENT");
                    String garment = rs.getString("NAME_GARMENT");
                    String unitPrice = rs.getString("UNIT_PRICE_SALE");
                    String quantity = rs.getString("QUANTITY_SALE");
                    String total = rs.getString("TOTAL_SALE");
                    return new Sale(id, garmentId, garment, unitPrice, quantity, total);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener la compra con el ID " + idSale, ex);
        }
        return null;
    }
    
}
