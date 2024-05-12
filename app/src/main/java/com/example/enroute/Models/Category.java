package com.example.enroute.Models;

import androidx.annotation.NonNull;

import java.util.UUID;

public class Category {
    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private UUID id;
    private String name;

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
