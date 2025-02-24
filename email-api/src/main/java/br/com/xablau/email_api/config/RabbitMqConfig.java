package br.com.xablau.email_api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.direct.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.name}")
    private String queueNameClienteCriado;

    @Value("${rabbitmq.queueEmail.name}")
    private String queueNameClienteErroEmail;

    @Value("${rabbitmq.queuePedidoCriado.name}")
    private String queuePedidoCriado;

    @Value("${rabbitmq.queueSaldoInsuficiente.name}")
    private String queueSaldoInsuficiente;

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue clienteCriadoqueue(){
        return new Queue(queueNameClienteCriado);
    }

    @Bean
    public Queue clienteEmailErro(){
        return new Queue(queueNameClienteErroEmail);
    }

    @Bean
    public Queue clientePedidoCriado(){
        return new Queue(queuePedidoCriado);
    }

    @Bean public Queue saldoInsuficiente(){
        return new Queue(queueSaldoInsuficiente);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(clienteCriadoqueue()).to(directExchange()).with("servicesBDsucesso");
    }

    @Bean
    public Binding bindingEmailError(){
        return BindingBuilder.bind(clienteEmailErro()).to(directExchange()).with("servicesBDEmailErro");
    }

    @Bean
    public Binding bindingPedidoCriado(){
        return BindingBuilder.bind(clientePedidoCriado()).to(directExchange()).with("pedidoCriadoSucesso");
    }

    @Bean
    public Binding bindingSaldoInsuficiente(){
        return BindingBuilder.bind(saldoInsuficiente()).to(directExchange()).with("clienteSaldoInsuficiente");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> listener(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }
}
