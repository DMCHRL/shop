package com.shop.user.config;

import com.shop.common.config.rabbitmq.RabbitExchangeEnum;
import com.shop.common.config.rabbitmq.RabbitKeyEnum;
import com.shop.common.config.rabbitmq.RabbitQueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {


    @Bean
    TopicExchange exchange() {
        return new TopicExchange(RabbitExchangeEnum.SHEP_SMS_EXCHANGE.getName());
    }

    @Bean
    public Queue itemFirstQueue() {
        return new Queue(RabbitQueueEnum.SHOP_VERIFIED_SMS_QUEUE.getName());
    }


    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(itemFirstQueue()).to(exchange()).with(RabbitKeyEnum.VERIFIED_SMS.getName());
    }


}
