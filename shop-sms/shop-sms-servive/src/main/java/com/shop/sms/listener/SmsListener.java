package com.shop.sms.listener;

import com.shop.sms.entity.SendVerifierSms;
import com.shop.sms.service.SmsService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsListener {

    @Autowired
    private SmsService smsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "SHOP.VERIFIED.SMS.QUEUE",durable = "true"),
            exchange = @Exchange(
                    value = "SHEP.SMS.EXCHANGE",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"VERIFIED.SMS"}
    ))
    public void sendVerifierCode(SendVerifierSms sendVerifierSms)throws Exception{
        if (sendVerifierSms == null){
            return;
        }
        smsService.sendVerifiedSms(sendVerifierSms);
    }
}
