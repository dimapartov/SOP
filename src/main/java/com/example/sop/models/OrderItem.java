package com.example.sop.models;

import com.example.sop.models.base.IdCreatedModified;
import jakarta.persistence.*;


@Entity
@Table(name = "order_items")
public class OrderItem extends IdCreatedModified {

    private Order order;
    private Part part;
    private int quantity;


    protected OrderItem() {}


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",referencedColumnName = "id", nullable = false)
    public Order getOrder() {
        return order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", referencedColumnName = "id", nullable = false)
    public Part getPart() {
        return part;
    }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }


    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}