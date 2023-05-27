/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author edwin
 */
public class Garment {
    
    private String cod;
    private int type;
    private String nameType;
    private String name;
    private String description;
    private String sex;
    private String pricePurchase;
    private String priceSale;
    private String stock;

    public Garment(String cod, String nameType, String name, String description, String sex, String priceSale) {
        this.cod = cod;
        this.nameType = nameType;
        this.name = name;
        this.description = description;
        this.sex = sex;
        this.priceSale = priceSale;
    }

    public Garment(String cod, int type, String nameType, String name, String description, String sex, String pricePurchase, String priceSale, String stock) {
        this.cod = cod;
        this.type = type;
        this.nameType = nameType;
        this.name = name;
        this.description = description;
        this.sex = sex;
        this.pricePurchase = pricePurchase;
        this.priceSale = priceSale;
        this.stock = stock;
    }
    
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPricePurchase() {
        return pricePurchase;
    }

    public void setPricePurchase(String pricePurchase) {
        this.pricePurchase = pricePurchase;
    }

    public String getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(String priceSale) {
        this.priceSale = priceSale;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    
    
}
