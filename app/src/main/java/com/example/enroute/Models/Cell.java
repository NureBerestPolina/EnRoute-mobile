package com.example.enroute.Models;

import java.util.UUID;

public class Cell {
    public Cell(UUID id, boolean isFree, boolean hasTemperatureControl, UUID counterId, PickupCounter counter, String cellOpenKey, Order order) {
        this.id = id;
        this.isFree = isFree;
        this.hasTemperatureControl = hasTemperatureControl;
        this.counterId = counterId;
        this.counter = counter;
        this.cellOpenKey = cellOpenKey;
        this.order = order;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isHasTemperatureControl() {
        return hasTemperatureControl;
    }

    public void setHasTemperatureControl(boolean hasTemperatureControl) {
        this.hasTemperatureControl = hasTemperatureControl;
    }

    public UUID getCounterId() {
        return counterId;
    }

    public void setCounterId(UUID counterId) {
        this.counterId = counterId;
    }

    public PickupCounter getCounter() {
        return counter;
    }

    public void setCounter(PickupCounter counter) {
        this.counter = counter;
    }

    public String getCellOpenKey() {
        return cellOpenKey;
    }

    public void setCellOpenKey(String cellOpenKey) {
        this.cellOpenKey = cellOpenKey;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private UUID id;
    private boolean isFree;
    private boolean hasTemperatureControl;
    private UUID counterId;
    private PickupCounter counter;
    private String cellOpenKey;
    private Order order;
}
