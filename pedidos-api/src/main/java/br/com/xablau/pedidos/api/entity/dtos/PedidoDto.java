package br.com.xablau.pedidos.api.entity.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PedidoDto {

    private UUID  clientId;
    private List<ItemPedidoDto> itemPedidoDtos = new ArrayList<>();

    public PedidoDto() {
    }

    public PedidoDto(UUID clientId, List<ItemPedidoDto> itemPedidoDtos) {
        this.clientId = clientId;
        this.itemPedidoDtos = itemPedidoDtos;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public List<ItemPedidoDto> getItemPedidoDtos() {
        return itemPedidoDtos;
    }

    public void setItemPedidoDtos(List<ItemPedidoDto> itemPedidoDtos) {
        this.itemPedidoDtos = itemPedidoDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PedidoDto pedidoDto = (PedidoDto) o;
        return Objects.equals(clientId, pedidoDto.clientId) && Objects.equals(itemPedidoDtos, pedidoDto.itemPedidoDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, itemPedidoDtos);
    }

    @Override
    public String toString() {
        return "PedidoDto{" +
                "clientId=" + clientId +
                ", itemPedidoDtos=" + itemPedidoDtos +
                '}';
    }
}
