package com.example.enroute.RequestModels;

public class LoginResponse {
    public LoginResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    private String token;
    private String refreshToken;
}
