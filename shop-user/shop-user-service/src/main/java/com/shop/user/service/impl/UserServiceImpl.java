package com.shop.user.service.impl;

import com.shop.common.config.rabbitmq.RabbitExchangeEnum;
import com.shop.common.config.rabbitmq.RabbitKeyEnum;
import com.shop.user.mapper.UserMapper;
import com.shop.user.service.UserService;
import com.shop.user.pojo.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        rabbitTemplate.convertAndSend(RabbitExchangeEnum.SHEP_SMS_EXCHANGE.getName(), RabbitKeyEnum.VERIFIED_SMS.getName());

    }
}
