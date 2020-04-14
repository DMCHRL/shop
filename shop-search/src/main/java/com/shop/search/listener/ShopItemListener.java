package com.shop.search.listener;

import com.shop.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopItemListener {

    @Autowired
    private SearchService searchService;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "SHOP.SAVE.INDEX.QUEUE",durable = "true"),
            exchange = @Exchange(
                    value = "SHEP.ITEM.EXCHANGE",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"ITEM.SAVE"}
    ))
    public void addGoodsIndex(Long id)throws Exception{
        System.out.println("============================进入更新索引================================");
        if (id == null){
            return;
        }
        searchService.updateGoodsIndex(id);
        System.out.println("============================更新索引成功================================");
    }
}
