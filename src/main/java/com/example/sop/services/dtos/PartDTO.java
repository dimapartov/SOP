package com.example.sop.services.dtos;

import java.math.BigDecimal;
import java.util.UUID;


public class PartDTO {

    private UUID id;
    private String name;
    private int quantityOnStorage;
    private BigDecimal price;


    public PartDTO(UUID id, String name, int quantityOnStorage, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantityOnStorage = quantityOnStorage;
        this.price = price;
    }

    public PartDTO(String name, int quantityOnStorage, BigDecimal price) {
        this.name = name;
        this.quantityOnStorage = quantityOnStorage;
        this.price = price;
    }

    public PartDTO() {
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantityOnStorage() {
        return quantityOnStorage;
    }

    public BigDecimal getPrice() {
        return price;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantityOnStorage(int quantityOnStorage) {
        this.quantityOnStorage = quantityOnStorage;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}