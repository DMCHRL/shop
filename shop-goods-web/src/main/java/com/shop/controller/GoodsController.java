package com.shop.controller;

import com.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){
        Map<String, Object> goodsDetails = goodsService.getGoodsDetails(id);
        model.addAllAttributes(goodsDetails);
        return "item";
    }
}
