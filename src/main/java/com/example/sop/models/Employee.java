package com.example.sop.models;

import com.example.sop.models.base.IdCreatedModified;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "employees")
public class Employee extends IdCreatedModified {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Order> orders;


    protected Employee() {
    }


    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    @Column(name = "phone_number", nullable = false, unique = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Order> getOrders() {
        return orders;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}