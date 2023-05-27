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
public class PurchaseCRUD extends Conexion {

    public boolean create(Purchase purch) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO PURCHASES (COD_GARMENT, DATE_TIME_PURCHASE, UNIT_PRICE_PURCHASE, QUANTITY_PURCHASE, TOTAL_PURCHASE) VALUES (?, ?, ?, ?, ?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, purch.getGarment());
            
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);
            
            ps.setString(2, formattedDate);
            ps.setString(3, purch.getUnitPrice());
            ps.setString(4, purch.getQuantity());
            ps.setString(5, purch.getTotal());
            ps.execute();
            
            new GarmentCRUD().updateStockPurchase(purch.getGarment(), purch.getQuantity());
            new GarmentCRUD().updatePricePurchase(purch.getGarment(), purch.getUnitPrice());
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
    
    public boolean update(Purchase purch) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE PURCHASES SET QUANTITY_PURCHASE=?, TOTAL_PURCHASE=?, COMENTARIES_PURCHASE=? WHERE COD_PURCHASE=?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, purch.getQuantity());
            ps.setString(2, purch.getTotal());
            ps.setString(3, purch.getComentaries());
            ps.setInt(4, purch.getId());
            ps.execute();
            new GarmentCRUD().decrementStock(purch.getGarment(), purch.getRefund());
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

        String sql = "DELETE FROM PURCHASES WHERE COD_PURCHASE = ?;";

        try {
            Purchase purchase = getPurchase(cod);
            new GarmentCRUD().decrementStock(purchase.getGarment(), Integer.parseInt(purchase.getQuantity()));
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
    
    public ArrayList<Purchase> getPurchases() throws SQLException{
        ArrayList<Purchase> purchases = new ArrayList<>();
        Connection con = getConexion();
        java.sql.Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT P.*, G.NAME_GARMENT FROM PURCHASES P JOIN GARMENTS G ON P.COD_GARMENT = G.COD_GARMENT;");
        while (rs.next()) {
            int id = rs.getInt("COD_PURCHASE");
            String garment = rs.getString("NAME_GARMENT");
            String dateTime = rs.getString("DATE_TIME_PURCHASE");
            String unitPrice = rs.getString("UNIT_PRICE_PURCHASE");
            String quantity = rs.getString("QUANTITY_PURCHASE");
            String total = rs.getString("TOTAL_PURCHASE");
            String comentaries = rs.getString("COMENTARIES_PURCHASE");
            purchases.add(new Purchase(id, garment, dateTime, unitPrice, quantity, total, comentaries));
        }
        return purchases;
    }
    
    public ArrayList<Purchase> searchPurchases(String search) throws SQLException {
        String sql = "SELECT P.*, G.NAME_GARMENT FROM PURCHASES P JOIN GARMENTS G ON P.COD_GARMENT = G.COD_GARMENT "
                + "WHERE COD_PURCHASE LIKE ? OR NAME_GARMENT LIKE ? OR DATE_TIME_PURCHASE LIKE ? "
                + "OR UNIT_PRICE_PURCHASE LIKE ? OR QUANTITY_PURCHASE LIKE ? OR TOTAL_PURCHASE LIKE ? "
                + "OR COMENTARIES_PURCHASE LIKE ?;";
        ArrayList<Purchase> purchases = new ArrayList<>();
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
                    int id = rs.getInt("COD_PURCHASE");
                    String garment = rs.getString("NAME_GARMENT");
                    String dateTime = rs.getString("DATE_TIME_PURCHASE");
                    String unitPrice = rs.getString("UNIT_PRICE_PURCHASE");
                    String quantity = rs.getString("QUANTITY_PURCHASE");
                    String total = rs.getString("TOTAL_PURCHASE");
                    String comentaries = rs.getString("COMENTARIES_PURCHASE");
                    purchases.add(new Purchase(id, garment, dateTime, unitPrice, quantity, total, comentaries));
                }
            }
        }
        return purchases;
    }
    
    public int getQuantity() throws SQLException {
        String sql = "SELECT COUNT(*) FROM PURCHASES";
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
        String sql = "SELECT MAX(COD_PURCHASE) FROM PURCHASES;";
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
    
    
    public boolean isExistPurchase(int id) throws SQLException {
        String sql = "SELECT 1 FROM PURCHASES WHERE COD_PURCHASE = ?";
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new SQLException("Error al verificar si existe una compra con el ID " + id, e);
        }
    }
    
    public Purchase getPurchase(int idPurchase) throws SQLException {
        String sql = "SELECT P.*, G.NAME_GARMENT FROM PURCHASES P JOIN GARMENTS G ON P.COD_GARMENT = G.COD_GARMENT WHERE P.COD_PURCHASE = ?;";
        try (Connection conn = getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPurchase);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("COD_PURCHASE");
                    String idGarment = rs.getString("COD_GARMENT");
                    String garment = rs.getString("NAME_GARMENT");
                    String unitPrice = rs.getString("UNIT_PRICE_PURCHASE");
                    String quantity = rs.getString("QUANTITY_PURCHASE");
                    String total = rs.getString("TOTAL_PURCHASE");
                    return new Purchase(id, idGarment, garment, unitPrice, quantity, total);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener la compra con el ID " + idPurchase, ex);
        }
        return null;
    }
    
}
