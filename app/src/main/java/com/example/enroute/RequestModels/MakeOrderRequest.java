package com.example.enroute.RequestModels;

import java.util.UUID;

public class MakeOrderRequest {

    public MakeOrderRequest(UUID customerId, UUID goodId, UUID pickupCounterId, int amountOrdered) {
        this.customerId = customerId;
        this.pickupCounterId = pickupCounterId;
        this.goodId = goodId;
        this.amountOrdered = amountOrdered;
    }

    public UUID getPickupCounterId() {
        return pickupCounterId;
    }

    public void setPickupCounterId(UUID pickupCounterId) {
        this.pickupCounterId = pickupCounterId;
    }

    public UUID getGoodId() {
        return goodId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setGoodId(UUID goodId) {
        this.goodId = goodId;
    }

    public int getAmountOrdered() {
        return amountOrdered;
    }

    public void setAmountOrdered(int amountOrdered) {
        this.amountOrdered = amountOrdered;
    }

    private UUID pickupCounterId;
    private UUID goodId;
    private UUID customerId;
    private int amountOrdered;
}
