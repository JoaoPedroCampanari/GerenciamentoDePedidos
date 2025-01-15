package br.com.xablau.pedidos.api.listener;

import br.com.xablau.dtos.dtos.ClienteDto;
import br.com.xablau.pedidos.api.services.impl.ClienteServices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClienteListener {

    private final ClienteServices clienteServices;

    public ClienteListener(ClienteServices clienteServices) {
        this.clienteServices = clienteServices;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void salvarClienteBD(ClienteDto clienteDto){
        clienteServices.save(clienteDto);
    }
}
