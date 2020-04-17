package com.shop.gateway.auth.controller;

import com.shop.common.pojo.JwtProperties;
import com.shop.common.pojo.UserInfo;
import com.shop.common.utils.CookieUtils;
import com.shop.gateway.auth.service.AuthService;
import com.shop.gateway.auth.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;


    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(@RequestParam(value = "username")String username,
                                         @RequestParam(value = "password")String password,
                                         HttpServletRequest request, HttpServletResponse response){
        String accredit = authService.accredit(username, password);
        CookieUtils.setCookie(request,response,this.jwtProperties.getCookieName(),jwtProperties.getTokenStartHead()+accredit,this.jwtProperties.getExpiration()* 60);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(){
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        return ResponseEntity.ok(new UserInfo().setUsername(currentUserLogin));
    }



}
