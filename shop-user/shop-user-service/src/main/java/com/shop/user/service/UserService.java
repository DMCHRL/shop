package com.shop.user.service;

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
}
