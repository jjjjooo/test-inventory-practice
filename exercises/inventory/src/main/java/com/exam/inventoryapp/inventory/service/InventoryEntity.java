package com.exam.inventoryapp.inventory.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryEntity {
    private @Nullable Long id;
    private @NotNull String itemId;
    private @NotNull Long stock;

    public InventoryEntity(@Nullable Long id, @NotNull String itemId, @NotNull Long stock) {
        this.id = id;
        this.itemId = itemId;
        this.stock = stock;
    }

    public String getItemId(){
        return this.itemId;
    }

    public Long getStock(){
        return this.stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
