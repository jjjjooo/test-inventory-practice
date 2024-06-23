package com.exam.inventoryapp.inventory.controller;

import com.exam.inventoryapp.common.ApiResponse;
import com.exam.inventoryapp.inventory.service.InventoryService;
import com.exam.inventoryapp.inventory.service.domain.Inventory;
import com.exam.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.exam.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.exam.inventoryapp.inventory.service.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/inventory")
@RestController
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{itemId}")
    ApiResponse<InventoryResponse> findByItemId(
            @PathVariable String itemId
    ) {
        final Inventory inventory = inventoryService.findByItemId(itemId);
        if (inventory == null) throw new CommonInventoryHttpException(ErrorCodes.ITEM_NOT_FOUND, HttpStatus.NOT_FOUND);

        return ApiResponse.just(
                InventoryResponse.fromDomain(inventory)
        );
    }

    @PostMapping("/{itemId}/decrease")
    ApiResponse<InventoryResponse> decreaseQuantity(
            @PathVariable String itemId,
            @RequestBody DecreaseQuantityRequest request
    ) {
        Inventory inventory;

        try {
            inventory = inventoryService.decreaseByItemId(itemId, request.quantity());
        } catch (ItemNotFoundException e) {
            throw new CommonInventoryHttpException(ErrorCodes.ITEM_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (InsufficientStockException e) {
            throw new CommonInventoryHttpException(ErrorCodes.INSUFFICIENT_STOCK, HttpStatus.BAD_REQUEST);
        } catch (InvalidDecreaseQuantityException e) {
            throw new CommonInventoryHttpException(ErrorCodes.INVALID_DECREASE_QUANTITY, HttpStatus.BAD_REQUEST);
        }

        return ApiResponse.just(
                InventoryResponse.fromDomain(inventory)
        );
    }
}