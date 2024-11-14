package com.example.sop.services.exceptions;

import java.util.UUID;


public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(UUID id) {
        super("Order", id.toString());
    }

}