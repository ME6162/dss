package com.rapidapi.core.dss.common.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseFormater implements IResponseFormater{

    @Override
    public ResponseEntity<Object> getForbidden() {
        // Create a map for the structured response
        Map<String, Object> response = new HashMap<>();
        response.put("status", 403);
        response.put("message", "You do not have permission to access this resource.");

        // Return the response as a ResponseEntity
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Object> getNotFound() {
        // Create a map for the structured response
        Map<String, Object> response = new HashMap<>();
        response.put("status", 404);
        response.put("message", "The requested resource could not be found.");

        // Return the response as a ResponseEntity
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Object> getBadRequest(String error) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 400);
        response.put("message", error);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
