package com.example.enroute.Models;

import androidx.annotation.NonNull;

import java.util.UUID;

public class OrderItem {
    public OrderItem(UUID id, int count, UUID goodId, Good goodOrdered, UUID orderId, Order order) {
        this.id = id;
        this.count = count;
        this.goodId = goodId;
        this.goodOrdered = goodOrdered;
        this.orderId = orderId;
        this.order = order;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UUID getGoodId() {
        return goodId;
    }

    public void setGoodId(UUID goodId) {
        this.goodId = goodId;
    }

    public Good getGoodOrdered() {
        return goodOrdered;
    }

    public void setGoodOrdered(Good goodOrdered) {
        this.goodOrdered = goodOrdered;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private UUID id;
    private int count;
    private UUID goodId;
    private Good goodOrdered;
    private UUID orderId;
    private Order order;

    @NonNull
    @Override
    public String toString() {
        return this.goodOrdered.getName()
                + " ("
                + this.count
                + " "
                + this.goodOrdered.getMeasurementUnit()
                + " x "
                + this.goodOrdered.getPrice()
                + ")";
    }
}
