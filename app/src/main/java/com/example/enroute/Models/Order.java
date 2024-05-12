package com.example.enroute.Models;

import androidx.annotation.NonNull;

import com.example.enroute.Constants.Status;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    public Order(UUID id, Date orderedDate, Date finalizedDate, UUID customerId, User customer, UUID assignedCellId, Cell assignedCell, List<OrderItem> items, Status status) {
        this.id = id;
        this.orderedDate = orderedDate;
        this.finalizedDate = finalizedDate;
        this.customerId = customerId;
        this.customer = customer;
        this.assignedCellId = assignedCellId;
        this.assignedCell = assignedCell;
        this.items = items;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Date getFinalizedDate() {
        return finalizedDate;
    }

    public void setFinalizedDate(Date finalizedDate) {
        this.finalizedDate = finalizedDate;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public UUID getAssignedCellId() {
        return assignedCellId;
    }

    public void setAssignedCellId(UUID assignedCellId) {
        this.assignedCellId = assignedCellId;
    }

    public Cell getAssignedCell() {
        return assignedCell;
    }

    public void setAssignedCell(Cell assignedCell) {
        this.assignedCell = assignedCell;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private UUID id;
    private Date orderedDate;
    private Date finalizedDate;
    private UUID customerId;
    private User customer;
    private UUID assignedCellId;
    private Cell assignedCell;
    private List<OrderItem> items;
    private Status status;

    @NonNull
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for (OrderItem orderItem:
             items) {
            res.append(orderItem.toString() + "\n");
        }

        res.append("\n" + this.getAssignedCell().getCounter().getAddress());
        return res.toString();
    }
}
