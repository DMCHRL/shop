package com.shop.gateway.auth.component;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ShopPasswordEncoder implements PasswordEncoder {

    private final static  Digester md5 = new Digester(DigestAlgorithm.MD5);


    @Override
    public String encode(CharSequence charSequence) {
        return md5.digestHex(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return md5.digestHex(charSequence.toString()).equals(s);
    }
}
