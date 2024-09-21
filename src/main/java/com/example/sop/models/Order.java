package com.example.sop.models;

import com.example.sop.enums.OrderStatusEnum;
import com.example.sop.models.base.IdCreatedModified;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "orders")
public class Order extends IdCreatedModified {

    private Employee employee;
    private String customerName;
    private String customerEmail;
    private OrderStatusEnum orderStatus;
    private List<OrderItem> orderItems;


    protected Order() {}


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    public Employee getEmployee() {
        return employee;
    }

    @Column(name = "customer_name", nullable = false)
    public String getCustomerName() {
        return customerName;
    }

    @Column(name = "customer_email", nullable = false)
    public String getCustomerEmail() {
        return customerEmail;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }


    public void setEmployee(Employee employee) {
        this.employee = employee;
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