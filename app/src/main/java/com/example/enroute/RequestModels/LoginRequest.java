package com.example.enroute.RequestModels;

public class LoginRequest {
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private String email;
    private  String password;
}
