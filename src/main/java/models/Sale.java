/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author edwin
 */
public class Sale {
    
    private int id;
    private String garment;
    private String garments;
    private String dateTime;
    private String unitPrice;
    private String quantity;
    private String total;
    private String comentaries;
    private int refund;

    public Sale(String garment, String unitPrice, String quantity, String total) {
        this.garment = garment;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
    }

    public Sale(int id, String garment, String garments, String dateTime, String unitPrice, String quantity, String total, String comentaries) {
        this.id = id;
        this.garment = garment;
        this.garments = garments;
        this.dateTime = dateTime;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
        this.comentaries = comentaries;
    }

    public Sale(int id, String garment, String garments, String unitPrice, String quantity, String total) {
        this.id = id;
        this.garment = garment;
        this.garments = garments;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
    }

    public Sale(int id, String garment, String quantity, String total, String comentaries, int refund) {
        this.id = id;
        this.garment = garment;
        this.quantity = quantity;
        this.total = total;
        this.comentaries = comentaries;
        this.refund = refund;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGarment() {
        return garment;
    }

    public void setGarment(String garment) {
        this.garment = garment;
    }

    public String getGarments() {
        return garments;
    }

    public void setGarments(String garments) {
        this.garments = garments;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getComentaries() {
        return comentaries;
    }

    public void setComentaries(String comentaries) {
        this.comentaries = comentaries;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    
   
}
