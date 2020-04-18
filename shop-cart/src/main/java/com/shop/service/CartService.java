package com.shop.service;

import com.shop.pojo.Cart;

import java.util.List;

public interface CartService {

    void addCart(Cart cart);

    List<Cart> getCart(String username);

    void updateNum(Cart cart);

    void deleteCart(String username,String skuIds);

}
