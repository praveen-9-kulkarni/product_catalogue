package com.learn.product_catalogue.product_catalogue.api.response;

/**
 * Simple class to represent an error response to the client
 */
public class ErrorResponse {
    
    private String message;
    
    public ErrorResponse() {
    }
    
    public ErrorResponse(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
} 