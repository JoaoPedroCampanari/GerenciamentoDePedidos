package br.com.xablau.pedidos.api.services;

import br.com.xablau.dtos.ItemPedidoDto;
import br.com.xablau.dtos.PedidoDto;
import br.com.xablau.pedidos.api.entity.ItemPedido;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.exception.pedidoException.PedidoNotFoundException;
import br.com.xablau.pedidos.api.repository.ItemPedidoRepository;
import br.com.xablau.pedidos.api.repository.PedidoRepository;
import br.com.xablau.pedidos.api.services.impl.PedidoServices;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class PedidoServicesImpl implements PedidoServices {


    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoServicesImpl(PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }


    @Override
    public Pedido findById(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não existe!", HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido update(UUID id, PedidoDto pedidoDto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não existe!", HttpStatus.NOT_FOUND, "NOT FOUND"));
        BeanUtils.copyProperties(pedidoDto, pedido);
        return pedido;
    }

    @Override
    public String deleteById(UUID id) {
        if (!pedidoRepository.existsById(id)){
            throw new PedidoNotFoundException("Pedido não existe!", HttpStatus.NOT_FOUND, "NOT FOUND");
        }
        return "Pedido deletado com sucesso!";
    }

    @Override
    public Pedido save(PedidoDto pedidoDto) {
        Pedido pedido = converDtoParaPedido(pedidoDto);

        List<ItemPedido> itemPedidos = pedidoDto.getItemPedidoDtos().stream().map(this::converterDtoParaItemPedido).toList();

        pedido.setItemPedidoList(itemPedidos);

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);

        return pedido;
    }

    @Override
    public List<ItemPedido> itensDoPedidoById(UUID id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não existe!", HttpStatus.NOT_FOUND, "NOT FOUND"));

        return pedido.getItemPedidoList();
    }

    @Override
    public ItemPedido converterDtoParaItemPedido(ItemPedidoDto itemPedidoDto) {
        ItemPedido itemPedido = new ItemPedido();
        BeanUtils.copyProperties(itemPedidoDto, itemPedido);

        return itemPedido;
    }

    @Override
    public Pedido converDtoParaPedido(PedidoDto pedidoDto) {
        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(pedidoDto, pedido);
        return pedido;
    }
}
