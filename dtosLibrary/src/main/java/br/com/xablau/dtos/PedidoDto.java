package br.com.xablau.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PedidoDto {

    private UUID  clientId;
    private Double valorTotal;
    private List<ItemPedidoDto> itemPedidoDtos = new ArrayList<>();

    public PedidoDto(UUID clientId, Double valorTotal, List<ItemPedidoDto> itemPedidoDtos) {
        this.clientId = clientId;
        this.valorTotal = valorTotal;
        this.itemPedidoDtos = itemPedidoDtos;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
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
        return Objects.equals(clientId, pedidoDto.clientId) && Objects.equals(valorTotal, pedidoDto.valorTotal) && Objects.equals(itemPedidoDtos, pedidoDto.itemPedidoDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, valorTotal, itemPedidoDtos);
    }

    @Override
    public String toString() {
        return "PedidoDto{" +
                "clientId=" + clientId +
                ", valorTotal=" + valorTotal +
                ", itemPedidoDtos=" + itemPedidoDtos +
                '}';
    }
}
