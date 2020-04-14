package com.shop.item.config;

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
    public Queue itemFirstQueue() {
        return new Queue(RabbitQueueEnum.SHOP_SAVE_INDEX_QUEUE.getName());
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange(RabbitExchangeEnum.SHEP_ITEM_EXCHANGE.getName());
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为ITEM.INSERT
    //这样只要是消息携带的路由键是ITEM.INSERT,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(itemFirstQueue()).to(exchange()).with(RabbitKeyEnum.ITEM_INSERT.getName());
    }


}
