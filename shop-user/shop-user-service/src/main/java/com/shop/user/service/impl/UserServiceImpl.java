package com.shop.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.shop.common.config.rabbitmq.RabbitExchangeEnum;
import com.shop.common.config.rabbitmq.RabbitKeyEnum;
import com.shop.common.constant.BusinessEnum;
import com.shop.sms.constant.SmsEnum;
import com.shop.sms.entity.SendVerifierSms;
import com.shop.user.mapper.UserMapper;
import com.shop.user.service.UserService;
import com.shop.user.pojo.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public boolean check(String value, Integer type) {
        User user = new User();
        if(type == 1){
            user.setUsername(value);
        }
        if(type == 2){
            user.setPhone(value);
        }

        return userMapper.selectCount(user) == 0;
    }

    @Override
    public void code(String phone) {
        String code = RandomUtil.randomNumbers(6);
        SendVerifierSms sendVerifierSms = new SendVerifierSms();
        sendVerifierSms.setPhone(phone);
        sendVerifierSms.setTemplateCode(SmsEnum.VERIFIED_TEMPLATECODE);
        sendVerifierSms.setCode(code);
        amqpTemplate.convertAndSend(RabbitExchangeEnum.SHEP_SMS_EXCHANGE.getName(), RabbitKeyEnum.VERIFIED_SMS.getName(),sendVerifierSms);
        redisTemplate.opsForValue().set(BusinessEnum.REGISTER+phone,code, 5,TimeUnit.MINUTES);
    }
}
