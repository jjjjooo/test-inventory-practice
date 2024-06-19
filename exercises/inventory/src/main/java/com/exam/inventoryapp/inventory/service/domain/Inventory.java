package com.exam.inventoryapp.inventory.service.domain;

public class Inventory {
    private String itemId;
    private Long stock;

    public Inventory(String itemId, Long stock) {
        this.itemId = itemId;
        this.stock = stock;
    }

    public String getItemId() {
        return this.itemId;
    }

    public Long getStock() {
        return this.stock;
    }
}
