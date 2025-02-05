package br.com.xablau.pedidos.api.entity.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record PedidoDto(
        @NotNull(message = "Id cliente cannot be null")
        UUID clientId,
        @NotNull(message = "ItemPedidos cannot be null")
        List<ItemPedidoDto> itemPedidoDtos) {

}
