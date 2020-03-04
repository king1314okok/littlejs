package com.oudake.core.init;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangyi
 */
@Configuration
public class RabbitConfig {

    @Value("${core-queue-name}")
    private String coreQueueName;
    @Value("${mail-queue-name}")
    private String mailQueueName;
    @Value("${exchange-name}")
    private String exchangeName;

    @Bean(name = "core-queue")
    public Queue coreQueue() {
        return new Queue(coreQueueName);
    }

    @Bean(name = "mail-queue")
    public Queue mailQueue() {
        return new Queue(mailQueueName);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Binding coreBinding() {
        return BindingBuilder.bind(coreQueue()).to(defaultExchange()).with(coreQueueName);
    }

    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(mailQueue()).to(defaultExchange()).with(mailQueueName);
    }

    /**
     * 发送json转换
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
