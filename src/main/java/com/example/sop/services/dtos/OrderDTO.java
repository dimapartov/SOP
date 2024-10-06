package com.example.sop.services.dtos;

import com.example.sop.enums.OrderStatusEnum;

import java.util.UUID;


public class OrderDTO {

    private UUID id;
    private UUID employeeId;
    private String customerName;
    private String customerEmail;
    private OrderStatusEnum orderStatus;


    public OrderDTO(UUID id, UUID employeeId, String customerName, String customerEmail, OrderStatusEnum orderStatus) {
        this.id = id;
        this.employeeId = employeeId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.orderStatus = orderStatus;
    }

    public OrderDTO(UUID employeeId, String customerName, String customerEmail, OrderStatusEnum orderStatus) {
        this.employeeId = employeeId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.orderStatus = orderStatus;
    }

    public OrderDTO(UUID employeeId, String customerName, String customerEmail) {
        this.employeeId = employeeId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public OrderDTO() {
    }


    public UUID getId() {
        return id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

}