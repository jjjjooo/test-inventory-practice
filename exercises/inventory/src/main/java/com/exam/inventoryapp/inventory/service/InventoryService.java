package com.exam.inventoryapp.inventory.service;

import com.exam.inventoryapp.inventory.repository.InventoryRepository;
import com.exam.inventoryapp.inventory.service.domain.Inventory;
import com.exam.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.exam.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.exam.inventoryapp.inventory.service.exception.InvalidStockException;
import com.exam.inventoryapp.inventory.service.exception.ItemNotFoundException;


public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory findByItemId(String itemId) {
        return inventoryRepository.findByItemId(itemId)
                .map(this::mapToDomain)
                .orElse(null);

    }

    public Inventory decreaseByItem(String itemId, Long quantity) {
        if (quantity < 0) throw new InvalidDecreaseQuantityException();

        InventoryEntity inventoryEntity = inventoryRepository.findByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);
        if (inventoryEntity.getStock() < quantity) throw new InsufficientStockException();

        final Integer updateCount = inventoryRepository.decreaseStock(itemId, quantity);
        if (updateCount == 0) throw new ItemNotFoundException();

        final InventoryEntity updatedEntity = inventoryRepository.findByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        return mapToDomain(updatedEntity);
    }

    private Inventory mapToDomain(InventoryEntity entity) {
        return new Inventory(entity.getItemId(), entity.getStock());
    }

    public Inventory updateStock(String itemId, Long newStock) {
        if (newStock < 0) throw new InvalidStockException();
        final InventoryEntity inventoryEntity = inventoryRepository.findByItemId(itemId).orElseThrow(ItemNotFoundException::new);

        inventoryEntity.setStock(newStock);
        return mapToDomain(inventoryRepository.save(inventoryEntity));
    }
}
