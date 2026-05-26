package org.example.springmvc.dto;

public record ContactResponse(String username, String email, String message, String responseMessage) {
    public ContactResponse {
        if (responseMessage == null) {
            responseMessage = "Contact Success";
        }
    }
}
