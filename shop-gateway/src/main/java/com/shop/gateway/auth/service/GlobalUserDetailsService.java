package com.shop.gateway.auth.service;

import com.shop.gateway.auth.base.GlobalUser;
import com.shop.gateway.auth.client.UserClient;
import com.shop.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class GlobalUserDetailsService implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userClient.findByName(s);
        if(user != null){
            return new GlobalUser(user.getUsername(),user.getPassword(),1);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
