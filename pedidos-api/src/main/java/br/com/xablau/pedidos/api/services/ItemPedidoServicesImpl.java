package br.com.xablau.pedidos.api.services;

import br.com.xablau.pedidos.api.entity.dtos.ItemPedidoDto;
import br.com.xablau.pedidos.api.entity.ItemPedido;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.exception.itemPedidoException.ItemPedidoNotFoundException;
import br.com.xablau.pedidos.api.repository.ItemPedidoRepository;
import br.com.xablau.pedidos.api.repository.ProdutoRepository;
import br.com.xablau.pedidos.api.services.impl.ItemPedidoServices;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemPedidoServicesImpl implements ItemPedidoServices {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoServicesImpl(ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public ItemPedido findById(UUID id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido não encontrado!", HttpStatus.NOT_FOUND,  "NOT FOUND"));
    }

    @Override
    public List<ItemPedido> findAll() {
        return itemPedidoRepository.findAll();
    }

    @Override
    public ItemPedido update(UUID id, ItemPedidoDto itemPedidoDto) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido não encontrado!", HttpStatus.NOT_FOUND, "NOT FOUND"));

        BeanUtils.copyProperties(itemPedidoDto, itemPedido);
        return itemPedidoRepository.save(itemPedido);
    }

    @Override
    public String deleteById(UUID id) {
        if (itemPedidoRepository.existsById(id)){
            throw new ItemPedidoNotFoundException("ItemPedido não encontrado!", HttpStatus.NOT_FOUND, "NOT FOUND");
        }
        return "ItemPedido deletado";
    }

    @Override
    public ItemPedido save(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    @Override
    public List<ItemPedido> saveAll(List<ItemPedido> itemPedidos) {
        return itemPedidoRepository.saveAll(itemPedidos);
    }

    @Override
    public Pedido pedidoDoItemById(UUID id) {
        return null;
    }
}
