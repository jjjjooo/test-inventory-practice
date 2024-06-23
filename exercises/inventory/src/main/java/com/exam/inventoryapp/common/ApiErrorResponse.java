package com.exam.inventoryapp.common;

import org.jetbrains.annotations.NotNull;

public record ApiErrorResponse(
        @NotNull String localMessage,
        @NotNull Long code
) {
}
