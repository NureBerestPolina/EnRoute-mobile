package com.example.enroute.Models;

import java.util.UUID;

public class Producer {
    public Producer(UUID id, String name, String description, String producerCountry) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.producerCountry = producerCountry;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducerCountry() {
        return producerCountry;
    }

    public void setProducerCountry(String producerCountry) {
        this.producerCountry = producerCountry;
    }

    private UUID id;
    private String name;
    private String description;
    private String producerCountry;
}
