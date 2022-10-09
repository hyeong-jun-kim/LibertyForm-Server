package com.example.libertyformapiserver.config.response;

import lombok.Getter;

@Getter
public class ErrorResponse {
    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
