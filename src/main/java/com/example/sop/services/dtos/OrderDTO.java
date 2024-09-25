package com.example.sop.services.dtos;

import com.example.sop.enums.OrderStatusEnum;
import com.example.sop.models.OrderItem;

import java.util.List;
import java.util.UUID;


public class OrderDTO {

    private UUID id;
    private UUID employeeId;
    private String customerName;
    private String customerEmail;
    private OrderStatusEnum orderStatus;
    private List<OrderItem> orderItems;


    public OrderDTO(UUID id, UUID employeeId, String customerName, String customerEmail, OrderStatusEnum orderStatus, List<OrderItem> orderItems) {
        this.id = id;
        this.employeeId = employeeId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public OrderDTO(UUID employeeId, String customerName, String customerEmail, OrderStatusEnum orderStatus, List<OrderItem> orderItems) {
        this.employeeId = employeeId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public OrderDTO(UUID employeeId, String customerName, String customerEmail, OrderStatusEnum orderStatus) {
        this.employeeId = employeeId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.orderStatus = orderStatus;
    }

    public OrderDTO() {}


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

    public List<OrderItem> getOrderItems() {
        return orderItems;
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

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}