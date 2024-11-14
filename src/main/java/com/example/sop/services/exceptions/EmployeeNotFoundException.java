package com.example.sop.services.exceptions;

import java.util.UUID;


public class EmployeeNotFoundException extends ResourceNotFoundException {

    public EmployeeNotFoundException(UUID id) {
        super("Employee", id.toString());
    }

}