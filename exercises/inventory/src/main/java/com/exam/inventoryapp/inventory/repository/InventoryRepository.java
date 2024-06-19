package com.exam.inventoryapp.inventory.repository;

import com.exam.inventoryapp.inventory.service.InventoryEntity;

import java.util.Optional;

public interface InventoryRepository {
    void addInventoryEntity(String existingItemId, Long stock);

    Optional<InventoryEntity> findByItemId(String itemId);

    Integer decreaseStock(String itemId, Long quantity);

    InventoryEntity save(InventoryEntity inventoryEntity);
}
