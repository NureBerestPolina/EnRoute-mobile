package com.example.enroute.Models;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.UUID;

public class PickupCounter {
    public PickupCounter(UUID id, String address, String placementDescription, String URI, int cellCount, int cellWithTempControlCount, boolean isDeleted, UUID organizationId, Organization ownerOrganization, List<Cell> cells) {
        this.id = id;
        this.address = address;
        this.placementDescription = placementDescription;
        this.URI = URI;
        this.cellCount = cellCount;
        this.cellWithTempControlCount = cellWithTempControlCount;
        this.isDeleted = isDeleted;
        this.organizationId = organizationId;
        this.ownerOrganization = ownerOrganization;
        this.cells = cells;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlacementDescription() {
        return placementDescription;
    }

    public void setPlacementDescription(String placementDescription) {
        this.placementDescription = placementDescription;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public int getCellCount() {
        return cellCount;
    }

    public void setCellCount(int cellCount) {
        this.cellCount = cellCount;
    }

    public int getCellWithTempControlCount() {
        return cellWithTempControlCount;
    }

    public void setCellWithTempControlCount(int cellWithTempControlCount) {
        this.cellWithTempControlCount = cellWithTempControlCount;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public Organization getOwnerOrganization() {
        return ownerOrganization;
    }

    public void setOwnerOrganization(Organization ownerOrganization) {
        this.ownerOrganization = ownerOrganization;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    private UUID id;
    private String address;
    private String placementDescription;
    private String URI;
    private int cellCount;
    private int cellWithTempControlCount;
    private boolean isDeleted;
    private UUID organizationId;
    private Organization ownerOrganization;
    private List<Cell> cells;

    @NonNull
    @Override
    public String toString() {
        return this.address + " (" + this.placementDescription + ")";
    }
}
