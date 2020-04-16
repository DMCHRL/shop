package com.shop.user.api;

import com.shop.user.pojo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

    @PostMapping("queryUser")
     User queryUser(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password);

    @PostMapping("findByName")
     User findByName(@RequestParam(value = "username") String username);
}
