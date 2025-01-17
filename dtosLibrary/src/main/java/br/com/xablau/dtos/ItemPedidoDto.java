package br.com.xablau.dtos;

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
}
