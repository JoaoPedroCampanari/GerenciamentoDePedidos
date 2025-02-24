package br.com.xablau.pedidos.api.services.impl;

import br.com.xablau.pedidos.api.entity.dtos.ItemPedidoDto;
import br.com.xablau.pedidos.api.entity.ItemPedido;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.entity.dtos.PedidoDto;

import java.util.List;
import java.util.UUID;

public interface PedidoServices {

    Pedido findById(UUID id);

    List<Pedido> findAll();

    Pedido update(UUID id, PedidoDto pedidoDto);

    String deleteById(UUID id);

    Pedido save(PedidoDto pedidoDto);

    List<ItemPedido> itensDoPedidoById(UUID id);

    ItemPedido converterDtoParaItemPedido(ItemPedidoDto itemPedidoDto);

    Pedido converDtoParaPedido(PedidoDto pedidoDto);
}
