package com.bijoy.events.hospital_service.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${patient.exchange.name}")
    private String patientExchangeName;
    @Value("${patient.creation.queue.name}")
    private String patientCreationQueueName;
    @Value("${patient.charge.queue.name}")
    private String patientChargeQueueName;
    @Value("${patient.creation.binding}")
    private String patientCreationRoutingKey;
    @Value("${patient.charge.binding}")
    private String patientChargeRoutingKey;
    private String patientChargeToCreationRoutingKey = "patient.*";

    @Bean
    public Queue patientCreationQueue() {
        return new Queue(patientCreationQueueName);
    }

    @Bean
    public Queue patientChargeQueue() {
        return new Queue(patientChargeQueueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(patientExchangeName);
    }

    @Bean
    public Binding patientCreationBinding() {
        return BindingBuilder.bind(patientCreationQueue())
                .to(exchange())
                .with(patientCreationRoutingKey);
    }

    @Bean
    public Binding patientChargeBinding() {
        return BindingBuilder.bind(patientChargeQueue())
                .to(exchange())
                .with(patientChargeRoutingKey);
    }

    @Bean
    public Binding patientChargeToCreationBinding() {
        return BindingBuilder.bind(patientCreationQueue())
                .to(exchange())
                .with(patientChargeToCreationRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
