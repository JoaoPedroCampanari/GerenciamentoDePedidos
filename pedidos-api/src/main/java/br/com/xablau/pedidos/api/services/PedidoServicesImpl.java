package br.com.xablau.pedidos.api.services;

import br.com.xablau.dtos.ClienteDto;
import br.com.xablau.dtos.ItemPedidoDto;
import br.com.xablau.dtos.PedidoDto;
import br.com.xablau.pedidos.api.entity.Cliente;
import br.com.xablau.pedidos.api.entity.ItemPedido;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.exception.clienteException.ClienteNotFoundException;
import br.com.xablau.pedidos.api.exception.pedidoException.PedidoNotFoundException;
import br.com.xablau.pedidos.api.exception.produtoException.ProdutoNotFoundException;
import br.com.xablau.pedidos.api.repository.ClienteRepository;
import br.com.xablau.pedidos.api.repository.ItemPedidoRepository;
import br.com.xablau.pedidos.api.repository.PedidoRepository;
import br.com.xablau.pedidos.api.repository.ProdutoRepository;
import br.com.xablau.pedidos.api.services.impl.PedidoServices;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class PedidoServicesImpl implements PedidoServices {

    @Value("${rabbitmq.direct.exchange.name}")
    private String exchangeName;

    private final RabbitTemplate rabbitTemplate;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoServicesImpl(RabbitTemplate rabbitTemplate, PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
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
        return pedidoRepository.save(pedido);
    }

    @Override
    public String deleteById(UUID id) {
        if (!pedidoRepository.existsById(id)){
            throw new PedidoNotFoundException("Pedido não existe!", HttpStatus.NOT_FOUND, "NOT FOUND");
        }

        pedidoRepository.deleteById(id);
        return "Pedido deletado com sucesso!";
    }

    @Override
    public Pedido save(PedidoDto pedidoDto) {
        Pedido pedido = converDtoParaPedido(pedidoDto);

        List<ItemPedido> itemPedidos = pedidoDto.getItemPedidoDtos().stream().map(this::converterDtoParaItemPedido).toList();

        for (ItemPedido itens: itemPedidos){
            itens.setPedido(pedido);
            itens.setValorTotal(itens.calculoValorTotal());
        }

        pedido.setItemPedidoList(itemPedidos);
        pedido.setValorTotal(pedido.calcularValorTotal());

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);
        rabbitTemplate.convertAndSend(exchangeName, "pedidoCriadoSucesso", transformarClienteEmDto(pedido.getCliente()));

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
        itemPedido.setProduto(produtoRepository.findById(itemPedidoDto.getProductId()).orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado!", HttpStatus.NOT_FOUND, "NOT FOUND")));
        return itemPedido;
    }

    @Override
    public Pedido converDtoParaPedido(PedidoDto pedidoDto) {
        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(pedidoDto, pedido);
        pedido.setCliente(clienteRepository.findById(pedidoDto.getClientId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente inexistente", HttpStatus.NOT_FOUND, "NOT FOUND")));
        return pedido;
    }

    public ClienteDto transformarClienteEmDto(Cliente cliente){
        ClienteDto clienteDto = new ClienteDto();
        BeanUtils.copyProperties(cliente, clienteDto);
        return clienteDto;
    }
}
