package com.example.sop.services.dtos;

import java.util.UUID;


public class OrderItemDTO {

    private UUID id;
    private OrderDTO order;
    private PartDTO part;
    private int quantity;


    public OrderItemDTO(UUID id, OrderDTO order, PartDTO part, int quantity) {
        this.id = id;
        this.order = order;
        this.part = part;
        this.quantity = quantity;
    }

    public OrderItemDTO(OrderDTO order, PartDTO part, int quantity) {
        this.order = order;
        this.part = part;
        this.quantity = quantity;
    }

    public OrderItemDTO() {}


    public UUID getId() {
        return id;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public PartDTO getPart() {
        return part;
    }

    public int getQuantity() {
        return quantity;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public void setPart(PartDTO part) {
        this.part = part;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}