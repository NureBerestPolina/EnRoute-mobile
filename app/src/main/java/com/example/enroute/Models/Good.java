package com.example.enroute.Models;

import androidx.annotation.NonNull;

import java.util.UUID;

public class Good {
    public Good(UUID id, String name, String description, String measurementUnit, boolean needsCooling, double price, double amountInStock, UUID producerId, Producer producer, UUID categoryId, Category category, UUID organizationId, Organization organization) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.measurementUnit = measurementUnit;
        this.needsCooling = needsCooling;
        this.price = price;
        this.amountInStock = amountInStock;
        this.producerId = producerId;
        this.producer = producer;
        this.categoryId = categoryId;
        this.category = category;
        this.organizationId = organizationId;
        this.organization = organization;
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

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public boolean isNeedsCooling() {
        return needsCooling;
    }

    public void setNeedsCooling(boolean needsCooling) {
        this.needsCooling = needsCooling;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(double amountInStock) {
        this.amountInStock = amountInStock;
    }

    public UUID getProducerId() {
        return producerId;
    }

    public void setProducerId(UUID producerId) {
        this.producerId = producerId;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    private UUID id;
    private String name;
    private String description;
    private String measurementUnit;
    private boolean needsCooling;
    private double price;
    private double amountInStock;
    private UUID producerId;
    private Producer producer;
    private UUID categoryId;
    private Category category;
    private UUID organizationId;
    private Organization organization;

    @NonNull
    @Override
    public String toString() {
        return this.name
                + " ("
                + this.price
                + ") "
                + this.description;
    }
}
