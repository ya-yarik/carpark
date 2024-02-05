package com.example.carpark.util;

public class TechInspectionErrorResponse {
    private String message;

    public TechInspectionErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}