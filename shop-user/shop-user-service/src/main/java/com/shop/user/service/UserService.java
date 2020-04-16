package com.shop.user.service;

import com.shop.user.dto.RegisterUser;
import com.shop.user.pojo.User;

public interface UserService {

    /**
     * 检查用户是否合法
     * @param value name or phone
     * @param type 1 name 2 phone
     * @return
     */
    boolean check(String value,Integer type);

    /**
     * 发送验证码
     * @param phone
     */
    void code(String phone);

    Boolean register(RegisterUser registerUser);

    User queryUser(String username,String password);

    User findByName(String username);
}
