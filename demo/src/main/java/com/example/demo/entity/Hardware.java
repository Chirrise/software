package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hardware")
public class Hardware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment for hardwareId
    @Column(name = "id")
    private Long hardwareId;

    @Column(name = "name")
    private String hardwareName;

    @Column(name = "type")
    private String type;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Lob  // To store large text or JSON format
    @Column(name = "specifications")
    private String specifications;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "price")
    private Double price;

    @Lob  // To store large text (optional)
    @Column(name = "description")
    private String description;

    // Getters and Setters
    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getHardwareName() {
        return hardwareName;
    }

    public void setHardwareName(String hardwareName) {
        this.hardwareName = hardwareName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
