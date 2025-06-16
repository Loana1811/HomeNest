/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author kloane
 */
public class UtilityType {

    private int id;
    private String name;
    private double price;
    private String unit;
    private boolean isSystem;

    public UtilityType(int id, String name, double price, String unit, boolean isSystem) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.isSystem = isSystem;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSystem() {
        return isSystem;
    }

}
