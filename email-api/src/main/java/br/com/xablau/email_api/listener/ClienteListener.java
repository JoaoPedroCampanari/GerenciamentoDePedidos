package br.com.xablau.email_api.listener;

import br.com.xablau.dtos.ClienteDto;
import br.com.xablau.email_api.serviceEmail.ClienteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClienteListener {

    private final ClienteService clienteService;

    public ClienteListener(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void enviarNotificacaoClienteCriado(ClienteDto clienteDto){
        clienteService.enviarEmailClienteCriado(clienteDto);
    }

    @RabbitListener(queues = "${rabbitmq.queueEmail.name}")
    public void enviarNotificacaoClienteEmailError(ClienteDto clienteDto){
        clienteService.enviarEmailClienteEmailError(clienteDto);
    }

    @RabbitListener(queues = "${rabbitmq.queuePedidoCriado.name}")
    public void enviarNotificacaoClientePedidoCriado(ClienteDto clienteDto){
        clienteService.enviarEmailClientePedidoCriado(clienteDto);
    }

    @RabbitListener(queues = "${rabbitmq.queueSaldoInsuficiente.name}")
    public void enviarNotificacaoClientePedidoSaldoInsuficiente(ClienteDto clienteDto){
        clienteService.enviarEmailClientePedidoSaldoInsuficiente(clienteDto);
    }

}
