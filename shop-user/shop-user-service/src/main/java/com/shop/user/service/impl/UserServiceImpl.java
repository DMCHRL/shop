package com.shop.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.shop.common.config.rabbitmq.RabbitExchangeEnum;
import com.shop.common.config.rabbitmq.RabbitKeyEnum;
import com.shop.user.mapper.UserMapper;
import com.shop.user.service.UserService;
import com.shop.user.pojo.User;
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
    private RabbitTemplate rabbitTemplate;

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
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("template","{\"code\":\""+code+"\"}");
        rabbitTemplate.convertAndSend(RabbitExchangeEnum.SHEP_SMS_EXCHANGE.getName(), RabbitKeyEnum.VERIFIED_SMS.getName(),map);
        redisTemplate.opsForValue().set(phone,code, 5,TimeUnit.MINUTES);

    }
}
