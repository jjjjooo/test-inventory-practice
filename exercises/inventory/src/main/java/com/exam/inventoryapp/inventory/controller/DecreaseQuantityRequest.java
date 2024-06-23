package com.exam.inventoryapp.inventory.controller;

import org.jetbrains.annotations.NotNull;

public record DecreaseQuantityRequest(
        @NotNull Long quantity
) {
}
