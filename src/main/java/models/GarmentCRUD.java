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
public class GarmentCRUD extends Conexion {

    public boolean create(Garment garm, TypeCRUD typeCRUD) {
        String sql = "INSERT INTO GARMENTS ("
                + "COD_GARMENT, COD_UNIQUE, COD_TYPE, NAME_GARMENT, DESCRIPTION_GARMENT, SEX_GARMENT, "
                + "PRICE_PURCHASE_GARMENT, PRICE_SALE_GARMENT, STOCK_GARMENT, STATUS_GARMENT)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, garm.getCod());
            ps.setInt(2, getCodNext());
            ps.setInt(3, typeCRUD.getIdType(garm.getNameType()));
            ps.setString(4, garm.getName());
            ps.setString(5, garm.getDescription());
            ps.setString(6, garm.getSex());
            ps.setString(7, "0");
            ps.setString(8, garm.getPriceSale());
            ps.setString(9, "0");
            ps.setBoolean(10, true);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean update(Garment garm) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE GARMENTS SET "
                + "COD_TYPE=?, NAME_GARMENT=?, DESCRIPTION_GARMENT=?, SEX_GARMENT=?, PRICE_SALE_GARMENT=? "
                + "WHERE COD_GARMENT=?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, new TypeCRUD().getIdType(garm.getNameType()));
            ps.setString(2, garm.getName());
            ps.setString(3, garm.getDescription());
            ps.setString(4, garm.getSex());
            ps.setString(5, garm.getPriceSale());
            ps.setString(6, garm.getCod());
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
    
    public boolean delete(String cod) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM GARMENTS WHERE COD_GARMENT=?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
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
    
    public ArrayList<Garment> getGarments() throws SQLException {
        String sql = "SELECT G.*, NAME_TYPE FROM GARMENTS G "
                + "JOIN TYPES T ON G.COD_TYPE = T.COD_TYPE "
                + "WHERE G.STATUS_GARMENT = TRUE;";
        
        ArrayList<Garment> garments = new ArrayList<>();
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("COD_GARMENT");
                    int type = rs.getInt("COD_TYPE");
                    String nameType = rs.getString("NAME_TYPE");
                    String name = rs.getString("NAME_GARMENT");
                    String description = rs.getString("DESCRIPTION_GARMENT");
                    String sex = rs.getString("SEX_GARMENT");
                    String pricePurchase = rs.getString("PRICE_PURCHASE_GARMENT");
                    String priceSale = rs.getString("PRICE_SALE_GARMENT");
                    String stock = rs.getString("STOCK_GARMENT");
                    garments.add(new Garment(id, type, nameType, name, description, sex, pricePurchase, priceSale, stock));
                }
            }
        }
        return garments;
    }

    
    public ArrayList<Garment> searchGarments(String search) throws SQLException {
        String sql = "SELECT G.*, NAME_TYPE FROM GARMENTS G JOIN TYPES T ON G.COD_TYPE = T.COD_TYPE "
                + "WHERE (COD_GARMENT LIKE ? OR NAME_TYPE LIKE ? OR NAME_GARMENT LIKE ? "
                + "OR DESCRIPTION_GARMENT LIKE ? OR SEX_GARMENT LIKE ? OR PRICE_PURCHASE_GARMENT LIKE ? "
                + "OR PRICE_SALE_GARMENT LIKE ? OR STOCK_GARMENT LIKE ?) AND G.STATUS_GARMENT = TRUE;";
        ArrayList<Garment> garments = new ArrayList<>();
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            search = "%" + search + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);
            ps.setString(5, search);
            ps.setString(6, search);
            ps.setString(7, search);
            ps.setString(8, search);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("COD_GARMENT");
                    int type = rs.getInt("COD_TYPE");
                    String nameType = rs.getString("NAME_TYPE");
                    String name = rs.getString("NAME_GARMENT");
                    String description = rs.getString("DESCRIPTION_GARMENT");
                    String sex = rs.getString("SEX_GARMENT");
                    String pricePurchase = rs.getString("PRICE_PURCHASE_GARMENT");
                    String priceSale = rs.getString("PRICE_SALE_GARMENT");
                    String stock = rs.getString("STOCK_GARMENT");
                    garments.add(new Garment(id, type, nameType, name, description, sex, pricePurchase, priceSale, stock));
                }
            }
        }
        return garments;
    }
    
    public int getQuantity() throws SQLException {
        String sql = "SELECT COUNT(*) FROM GARMENTS;";
        int quantity = 0;
        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener la cantidad de prendas en la tabla GARMENTS.", ex);
        }
        return quantity;
    }
    
    public boolean isExistGarment(String id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM GARMENTS WHERE COD_GARMENT = ?;";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }
    
    public boolean isExistName(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM GARMENTS WHERE NAME_GARMENT = ?;";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }
    
    public boolean isExistGarmentUpdate(String id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM GARMENTS WHERE COD_GARMENT = ? AND STATUS_GARMENT = TRUE;";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }
    
    public boolean isExistProductInPurchases(String id) throws SQLException {
        String sql = "SELECT NOT EXISTS (SELECT * FROM PURCHASES WHERE COD_GARMENT = ?);";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 1;
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }
    
    public boolean isExistProductInSales(String id) throws SQLException {
        String sql = "SELECT NOT EXISTS (SELECT * FROM SALES WHERE COD_GARMENT = ?);";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 1;
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }

    public Garment getGarment(String idGarment) throws SQLException {
        String sql = "SELECT G.*, NAME_TYPE FROM GARMENTS G "
                + "JOIN TYPES T ON G.COD_TYPE = T.COD_TYPE "
                + "WHERE COD_GARMENT=? AND G.STATUS_GARMENT = TRUE;";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        int quantity = 0;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, idGarment);
            rs = ps.executeQuery();
            if (rs.next()) {
                String id = rs.getString("COD_GARMENT");
                int type = rs.getInt("COD_TYPE");
                String nameType = rs.getString("NAME_TYPE");
                String name = rs.getString("NAME_GARMENT");
                String description = rs.getString("DESCRIPTION_GARMENT");
                String sex = rs.getString("SEX_GARMENT");
                String pricePurchase = rs.getString("PRICE_PURCHASE_GARMENT");
                String priceSale = rs.getString("PRICE_SALE_GARMENT");
                String stock = rs.getString("STOCK_GARMENT");
                return new Garment(id, type, nameType, name, description, sex, pricePurchase, priceSale, stock);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener la cantidad de prendas en la tabla GARMENTS.", ex);
        }
        return null;
    }
    
    public int getCodNext() throws SQLException {
    String sql = "SELECT MAX(COD_UNIQUE) AS MAX_COD_UNIQUE FROM GARMENTS;";
        
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = getConexion();
    try {
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("MAX_COD_UNIQUE") + 1;
        }
    } catch (SQLException ex) {
        throw new SQLException("Error al obtener el siguiente código único en la tabla GARMENTS.", ex);
    } finally {
        if(rs == null){
            return 1;
        }
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (con != null) {
            con.close();
        }
    }
    return 0;
}
    
    public String getStock(String id) throws SQLException{
        String sql = "SELECT STOCK_GARMENT FROM GARMENTS WHERE COD_GARMENT=? AND STATUS_GARMENT = TRUE;";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("STOCK_GARMENT");
            }
        } catch (SQLException ex) {
            throw new SQLException("", ex);
        }
        return "0";
    }
    
    public boolean updateStock(String id, int quantity){
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE GARMENTS SET STOCK_GARMENT=? WHERE COD_GARMENT=? AND STATUS_GARMENT = TRUE;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(Integer.parseInt(getStock(id)) + quantity));
            ps.setString(2, id);
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
    
    public boolean updateStockPurchase(String id, String quantity){
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE GARMENTS SET STOCK_GARMENT=? WHERE COD_GARMENT=? AND STATUS_GARMENT = TRUE;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(Integer.parseInt(getStock(id)) + Integer.parseInt(quantity)));
            ps.setString(2, id);
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
    
    public boolean decrementStock(String id, int quantity){
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE GARMENTS SET STOCK_GARMENT=? WHERE COD_GARMENT=? AND STATUS_GARMENT = TRUE;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(Integer.parseInt(getStock(id)) - quantity));
            ps.setString(2, id);
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
    
    public boolean updatePricePurchase(String id, String pricePurchase){
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE GARMENTS SET PRICE_PURCHASE_GARMENT=? WHERE COD_GARMENT=? AND STATUS_GARMENT = TRUE;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pricePurchase);
            ps.setString(2, id);
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
    
}
