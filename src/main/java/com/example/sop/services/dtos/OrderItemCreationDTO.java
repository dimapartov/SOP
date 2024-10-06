package com.example.sop.services.dtos;

import java.util.UUID;


public class OrderItemCreationDTO {

    private UUID id;
    private UUID orderId;
    private UUID partId;
    private int quantity;


    public OrderItemCreationDTO(UUID id, UUID orderId, UUID partId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.partId = partId;
        this.quantity = quantity;
    }

    public OrderItemCreationDTO(UUID orderId, UUID partId, int quantity) {
        this.orderId = orderId;
        this.partId = partId;
        this.quantity = quantity;
    }

    public OrderItemCreationDTO() {
    }


    public UUID getId() {
        return id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getPartId() {
        return partId;
    }

    public int getQuantity() {
        return quantity;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setPartId(UUID partId) {
        this.partId = partId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}