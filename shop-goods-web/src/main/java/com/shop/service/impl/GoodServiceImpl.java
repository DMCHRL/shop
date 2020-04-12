package com.shop.service.impl;

import com.shop.client.GoodsClient;
import com.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoodServiceImpl implements GoodsService {

    @Autowired
    private GoodsClient goodsClient;

    @Override
    public Map<String, Object> getGoodsDetails(Long spuId) {
        return goodsClient.getGoodsDetails(spuId);
    }
}
