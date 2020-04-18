package com.shop.order.client;

import com.shop.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-service")
public interface UserClient  extends UserApi {
}
