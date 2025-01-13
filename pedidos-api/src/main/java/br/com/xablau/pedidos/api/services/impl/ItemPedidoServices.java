package br.com.xablau.pedidos.api.services.impl;


import br.com.xablau.pedidos.api.entity.ItemPedido;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.entity.dtos.ItemPedidoDto;

import java.util.List;
import java.util.UUID;

public interface ItemPedidoServices {

    ItemPedido findById(UUID id);

    List<ItemPedido> findAll();

    ItemPedido update(UUID id, ItemPedidoDto itemPedidoDto);

    String deleteById(UUID id);

    ItemPedido save(ItemPedidoDto itemPedidoDto);

    Pedido pedidoDoItemById(UUID id);
}
