package com.shop.order.service.api;


import com.shop.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "api-gateway", path = "/api/item")
public interface GoodsService extends GoodsApi {
}
