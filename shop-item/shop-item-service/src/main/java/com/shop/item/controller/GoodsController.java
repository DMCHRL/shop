package com.shop.item.controller;

import com.shop.item.dto.Goods;
import com.shop.item.pojo.Sku;
import com.shop.item.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class GoodsController {

    @Autowired
    private SpuService spuService;

    @PostMapping
    @RequestMapping("goods")
    public ResponseEntity<Void> save(@RequestBody Goods goods){
        spuService.saveGoods(goods);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    @RequestMapping("put")
    public ResponseEntity<Void> update(@RequestBody Goods goods){
        spuService.saveGoods(goods);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> detail(@RequestParam Long id){
        return ResponseEntity.ok( spuService.findBySpuId(id));
    }


}
