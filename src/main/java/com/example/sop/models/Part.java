package com.example.sop.models;

import com.example.sop.models.base.IdCreatedModified;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "parts")
public class Part extends IdCreatedModified {


    private String name;
    private int quantityOnStorage;
    private BigDecimal price;
    private List<OrderItem> orderItems;


    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "quantity_on_storage", nullable = false)
    public int getQuantityOnStorage() {
        return quantityOnStorage;
    }


    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    @OneToMany(mappedBy = "part")
    public List<OrderItem> getOrderItems() {
        return orderItems;
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

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}