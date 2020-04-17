package com.shop.auth;


import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;

public class AuthTest {

    public static void main(String[] args) {
        System.out.println( DigestUtil.md5Hex("123aaa"));
    }
}
