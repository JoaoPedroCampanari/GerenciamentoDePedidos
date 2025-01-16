package br.com.xablau.pedidos.api.config;


import org.springframework.amqp.core.*;
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

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.direct.exchange.name}")
    private String exchangeDirectName;

    @Value("${rabbitmq.queue.name}")
    private String queueProcessamentoServicesName;

    @Value("${rabbitmq.exchange.dlx.name}")
    private String exchangeDlxName;

    @Value("${rabbitmq.queue.dlq.name}")
    private String queueDlqName;

    @Bean
    public FanoutExchange pedidosDlxExchange() {
        return new FanoutExchange(exchangeDlxName);
    }

    @Bean
    public DirectExchange pedidosExchangeDirect(){
        return new DirectExchange(exchangeDirectName);
    }

    @Bean
    public Queue queueProcessamentoServices(){
        Map<String, Object> argumentos = new HashMap<>();
        argumentos.put("x-dead-letter-exchange", exchangeDlxName);
        return new Queue(queueProcessamentoServicesName, true, false, false, argumentos);
    }

    @Bean
    public Queue notificacaoDlqQueue() {
        return new Queue(queueDlqName);
    }
    @Bean
    public Binding bindingDlxDlq(){
        return BindingBuilder.bind(notificacaoDlqQueue()).to(pedidosDlxExchange());
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queueProcessamentoServices()).to(pedidosExchangeDirect()).with("servicesBD");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
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
