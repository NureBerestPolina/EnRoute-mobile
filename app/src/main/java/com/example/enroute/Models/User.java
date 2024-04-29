package com.example.enroute.Models;

import java.util.Date;
import java.util.UUID;

public class User {
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public User(UUID id, String email, String name, Date registerDate, String roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.registerDate = registerDate;
        this.roles = roles;
    }

    private UUID id;
    private String email;
    private String name;
    private Date registerDate;
    private String roles;
}