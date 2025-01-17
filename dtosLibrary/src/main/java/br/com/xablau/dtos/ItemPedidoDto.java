package br.com.xablau.dtos;

import java.util.Objects;
import java.util.UUID;

public class ItemPedidoDto {

    private UUID productId;
    private Integer quantidade;

    public ItemPedidoDto() {
    }

    public ItemPedidoDto(UUID productId, Integer quantidade) {
        this.productId = productId;
        this.quantidade = quantidade;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemPedidoDto{" +
                "productId=" + productId +
                ", quantidade=" + quantidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoDto that = (ItemPedidoDto) o;
        return Objects.equals(productId, that.productId) && Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantidade);
    }
}
