package br.com.xablau.pedidos.api.services;

import br.com.xablau.pedidos.api.entity.Cliente;
import br.com.xablau.pedidos.api.entity.ItemPedido;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.entity.dtos.ClienteDto;
import br.com.xablau.pedidos.api.entity.dtos.ItemPedidoDto;
import br.com.xablau.pedidos.api.entity.dtos.PedidoDto;
import br.com.xablau.pedidos.api.exception.clienteException.ClienteInsufficientBalanceException;
import br.com.xablau.pedidos.api.exception.pedidoException.PedidoNotFoundException;
import br.com.xablau.pedidos.api.repository.PedidoRepository;
import br.com.xablau.pedidos.api.services.impl.ClienteServices;
import br.com.xablau.pedidos.api.services.impl.ItemPedidoServices;
import br.com.xablau.pedidos.api.services.impl.PedidoServices;
import br.com.xablau.pedidos.api.services.impl.ProdutoServices;
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
    private final ItemPedidoServices itemPedidoServices;
    private final ClienteServices clienteServices;
    private final ProdutoServices produtoServices;

    public PedidoServicesImpl(RabbitTemplate rabbitTemplate, PedidoRepository pedidoRepository, ItemPedidoServices itemPedidoServices, ClienteServices clienteServices, ProdutoServices produtoServices) {
        this.rabbitTemplate = rabbitTemplate;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoServices = itemPedidoServices;
        this.clienteServices = clienteServices;
        this.produtoServices = produtoServices;
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

        List<ItemPedido> itemPedidos = pedidoDto.itemPedidoDtos().stream().map(this::converterDtoParaItemPedido).toList();

        for (ItemPedido itens: itemPedidos){
            itens.setPedido(pedido);
            itens.setValorTotal(itens.calculoValorTotal());
        }

        pedido.setItemPedidoList(itemPedidos);
        if (pedido.getCliente().getSaldoAplicativo() < pedido.calcularValorTotal()){
            rabbitTemplate.convertAndSend(exchangeName, "clienteSaldoInsuficiente", transformarClienteEmDto(pedido.getCliente()));
            throw new ClienteInsufficientBalanceException("Saldo Insuficiente", HttpStatus.BAD_REQUEST, "BAD REQUEST");
        }

        pedido.setValorTotal(pedido.calcularValorTotal());

        pedido.getCliente().subtrairSaldo(pedido.getValorTotal());
        pedidoRepository.save(pedido);
        itemPedidoServices.saveAll(itemPedidos);
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
        itemPedido.setProduto(produtoServices.findById(itemPedidoDto.productId()));
        return itemPedido;
    }

    @Override
    public Pedido converDtoParaPedido(PedidoDto pedidoDto) {
        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(pedidoDto, pedido);
        pedido.setCliente(clienteServices.findById(pedidoDto.clientId()));
        return pedido;
    }

    public ClienteDto transformarClienteEmDto(Cliente cliente){
        return new ClienteDto(cliente);
    }
}
