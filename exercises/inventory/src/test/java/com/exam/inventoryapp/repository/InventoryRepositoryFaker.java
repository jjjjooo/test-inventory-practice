package com.exam.inventoryapp.repository;

import com.exam.inventoryapp.inventory.repository.InventoryRepository;
import com.exam.inventoryapp.inventory.service.InventoryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InventoryRepositoryFaker implements InventoryRepository {
    private final List<InventoryEntity> inventoryEntities = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public void addInventoryEntity(String itemId, Long stock) {
        final Long id = idGenerator.getAndIncrement();
        inventoryEntities.add(new InventoryEntity(id, itemId, stock));
    }

    @Override
    public Optional<InventoryEntity> findByItemId(String itemId) {
        return inventoryEntities.stream().filter(item -> item.getItemId().equals(itemId))
                .findFirst();
    }

    @Override
    public Integer decreaseStock(String itemId, Long quantity) {
        final Optional<InventoryEntity> inventoryEntityOp = inventoryEntities.stream().filter(item -> item.getItemId().equals(itemId))
                .findFirst();
        if (inventoryEntityOp.isEmpty()) return 0;
        final InventoryEntity entity = inventoryEntityOp.get();
        entity.setStock(entity.getStock() - quantity);
        return 1;
    }

    @Override
    public InventoryEntity save(InventoryEntity inventoryEntity) {
        final Optional<InventoryEntity> inventoryEntityOp = inventoryEntities.stream()
                .filter(item -> item.getItemId() !=null && item.getItemId().equals(inventoryEntity.getItemId()))
                .findFirst();

        InventoryEntity entity = null;
        if(inventoryEntityOp.isPresent()) {
            entity = inventoryEntityOp.get();
            entity.setStock(inventoryEntity.getStock());
        } else {
            final Long id = idGenerator.getAndIncrement();
            entity = new InventoryEntity(
                    id,
                    inventoryEntity.getItemId(),
                    inventoryEntity.getStock()
            );
            inventoryEntities.add(entity);
        }
        return inventoryEntity;
    }
}
