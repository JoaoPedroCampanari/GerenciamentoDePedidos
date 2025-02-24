package br.com.xablau.pedidos.api.services;

import br.com.xablau.pedidos.api.entity.dtos.ClienteDto;
import br.com.xablau.pedidos.api.entity.Cliente;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.exception.clienteException.ClienteEmailAlreadyExist;
import br.com.xablau.pedidos.api.exception.clienteException.ClienteNotFoundException;
import br.com.xablau.pedidos.api.repository.ClienteRepository;
import br.com.xablau.pedidos.api.services.impl.ClienteServices;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteServicesImpl implements ClienteServices {

    @Value("${rabbitmq.direct.exchange.name}")
    private String exchangeName;

    private final RabbitTemplate rabbitTemplate;
    private final ClienteRepository clienteRepository;

    public ClienteServicesImpl(RabbitTemplate rabbitTemplate, ClienteRepository clienteRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente findById(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente inexistente", HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente update(UUID id, ClienteDto clienteDto) {
        Cliente clienteUpdate = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente inexistente", HttpStatus.NOT_FOUND, "NOT FOUND"));
        BeanUtils.copyProperties(clienteDto, clienteUpdate);
        return clienteRepository.save(clienteUpdate);
    }

    @Override
    public String deleteById(UUID id) {
        if (!clienteRepository.existsById(id)){
            throw new ClienteNotFoundException("Cliente inexistente", HttpStatus.NOT_FOUND, "NOT FOUND");
        }
        clienteRepository.deleteById(id);
        return "Cliente deletado";
    }

    @Override
    public Cliente save(ClienteDto clienteDto) {
        if (clienteRepository.existsByEmail(clienteDto.email())){
            rabbitTemplate.convertAndSend(exchangeName, "servicesBDEmailErro", clienteDto);
            throw new ClienteEmailAlreadyExist("Email já possui cadastro", HttpStatus.CONFLICT, "CONFLICT");
        }

        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDto, cliente);
        rabbitTemplate.convertAndSend(exchangeName, "servicesBDsucesso", clienteDto);
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Pedido> pedidosClienteById(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente inexistente", HttpStatus.NOT_FOUND, "NOT FOUND"));
        return cliente.getPedidoList();
    }

}
