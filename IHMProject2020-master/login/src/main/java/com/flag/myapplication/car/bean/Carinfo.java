package com.flag.myapplication.car.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Carinfo")
public class Carinfo   {


    @Column(name = "id", isId = true,autoGen=false)
    public int id;
    @Column(name = "serial")
    public String serial;
    @Column(name = "brand")

    public String brand;

    @Column(name = "type")

    public String type;

    @Column(name = "color")

    public String color;

    @Column(name = "confidence")

    public String confidence;

    @Column(name = "year")

    public String year;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Carinfo{" +
                "id=" + id +
                ", serial='" + serial + '\'' +
                ", brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", confidence='" + confidence + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
