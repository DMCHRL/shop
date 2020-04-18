package com.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.shop.client.GoodsClient;
import com.shop.intercepetor.LoginInterceptor;
import com.shop.item.pojo.Sku;
import com.shop.pojo.Cart;
import com.shop.service.CartService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    private static final String KEY_PREFIX = "user:cart:";

    @Override
    public void addCart(Cart cart) {
        //获取用户名
        String username = cart.getUsername();

        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(KEY_PREFIX + username);

        Integer num = cart.getNum();
        String key = cart.getSkuId().toString();
        if (operations.hasKey(key)) {
            //更新数量
            cart = JSON.parseObject(operations.get(key).toString(),Cart.class);
            cart.setNum(cart.getNum() + num);
            operations.put(key, JSON.toJSONString(cart));
        }else{
            //新增
            Sku skuById = goodsClient.findSkuById(cart.getSkuId());
            cart.setUsername(username);
            cart.setTitle(skuById.getTitle());
            cart.setOwnSpec(skuById.getOwnSpec());
            cart.setImage(StringUtils.isBlank(skuById.getImages()) ? "" : StringUtils.split(skuById.getImages(),",")[0]);
            cart.setPrice(skuById.getPrice());
            operations.put(key, JSON.toJSONString(cart));
        }

    }

    @Override
    public List<Cart> getCart(String username) {
        if (!redisTemplate.hasKey(KEY_PREFIX + username)) {
            return null;
        }
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(KEY_PREFIX + username);
        List<Object> cartJson = operations.values();
        if(CollectionUtils.isEmpty(cartJson)){
            return null;
        }
        return cartJson.stream().map(t -> JSON.parseObject(t.toString(), Cart.class)).collect(Collectors.toList());
    }

    @Override
    public void updateNum(Cart cart) {
        String username = cart.getUsername();
        if (!redisTemplate.hasKey(KEY_PREFIX + username)) {
            return ;
        }
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(KEY_PREFIX + username);
        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();
        cart = JSON.parseObject(operations.get(key).toString(), Cart.class);
        cart.setNum( num);
        operations.put(key, JSON.toJSONString(cart));
    }

    @Override
    public void deleteCart(String username, String skuIds) {
        if (!redisTemplate.hasKey(KEY_PREFIX + username)) {
            return ;
        }
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(KEY_PREFIX + username);
        for (String s : skuIds.split(",")) {
            operations.delete(s);
        }
    }
}
