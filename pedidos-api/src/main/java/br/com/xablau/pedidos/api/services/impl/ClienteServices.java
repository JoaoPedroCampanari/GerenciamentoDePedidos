package br.com.xablau.pedidos.api.services.impl;

import br.com.xablau.dtos.dtos.ClienteDto;
import br.com.xablau.pedidos.api.entity.Cliente;
import br.com.xablau.pedidos.api.entity.Pedido;

import java.util.List;
import java.util.UUID;

public interface ClienteServices {

    Cliente findById(UUID id);

    List<Cliente> findAll();

    Cliente update(UUID id, ClienteDto clienteDto);

    String deleteById(UUID id);

    Cliente save(ClienteDto clienteDto);

    List<Pedido> pedidosClienteById(UUID id);

    void enfileirarCliente(ClienteDto clienteDto);
}
