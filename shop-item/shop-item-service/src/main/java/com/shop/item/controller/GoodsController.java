package com.shop.item.controller;

import com.shop.item.dto.Goods;
import com.shop.item.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private SpuService spuService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Goods goods){
        spuService.saveGoods(goods);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
