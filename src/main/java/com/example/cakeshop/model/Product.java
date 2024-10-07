package com.example.cakeshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private BigDecimal price;

    @Getter @Setter
    private String imageUrl;

    // Getters and setters for id and other fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
