package com.example.sop.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class ResourceNotFoundException extends ResponseStatusException {

    public ResourceNotFoundException(String resourceName, String id) {
        super(HttpStatus.NOT_FOUND, resourceName + " not found with id: " + id);
    }

}