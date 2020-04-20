package com.shop.controller;

import com.shop.intercepetor.LoginInterceptor;
import com.shop.pojo.Cart;
import com.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        String username = LoginInterceptor.getUsername();
        cart.setUsername(username);
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCart(){
        String username = LoginInterceptor.getUsername();
        return ResponseEntity.ok( cartService.getCart(username));
    }

    @PutMapping
    public ResponseEntity<Void> updateNum( Cart cart){
        String username = LoginInterceptor.getUsername();
        cart.setUsername(username);
        cartService.updateNum(cart);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{skuIds}")
    public ResponseEntity<Void> updateNum( @PathVariable String skuIds){
        String username = LoginInterceptor.getUsername();
        cartService.deleteCart(username,skuIds);
        return ResponseEntity.noContent().build();
    }
}
