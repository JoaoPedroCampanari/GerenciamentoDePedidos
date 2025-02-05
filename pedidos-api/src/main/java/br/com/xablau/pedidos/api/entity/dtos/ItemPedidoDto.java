package br.com.xablau.pedidos.api.entity.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemPedidoDto (
        @NotNull(message = "Product ID cannot be null")
        UUID productId,
        @NotNull(message = "Quantity cannot be null")
        @Min(value = 1, message = "The minimum quantity must be 1")
        Integer quantidade) {


}
