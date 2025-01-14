package br.com.xablau.pedidos.api.services;

import br.com.xablau.pedidos.api.entity.Cliente;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.entity.dtos.ClienteDto;
import br.com.xablau.pedidos.api.repository.ClienteRepository;
import br.com.xablau.pedidos.api.services.impl.ClienteServices;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteServicesImpl implements ClienteServices {

    private final ClienteRepository clienteRepository;

    public ClienteServicesImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente findById(UUID id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente inexistente"));
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente update(UUID id, ClienteDto clienteDto) {
        Cliente clienteUpdate = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente inexistente"));
        BeanUtils.copyProperties(clienteDto, clienteUpdate);
        return clienteRepository.save(clienteUpdate);
    }

    @Override
    public String deleteById(UUID id) {
        if (!clienteRepository.existsById(id)){
            throw new RuntimeException("Cliente inexistente");
        }
        clienteRepository.deleteById(id);
        return "Cliente deletado";
    }

    @Override
    public Cliente save(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDto, cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Pedido> pedidosClienteById(UUID id) {

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente inexistente"));

        return cliente.getPedidoList();
    }
}
