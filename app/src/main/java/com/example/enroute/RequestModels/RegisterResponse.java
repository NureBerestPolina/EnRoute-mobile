package com.example.enroute.RequestModels;

import java.util.UUID;

public class RegisterResponse {
    public RegisterResponse(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    private UUID userId;
}
