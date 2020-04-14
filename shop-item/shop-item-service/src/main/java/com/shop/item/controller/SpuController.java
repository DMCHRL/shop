package com.shop.item.controller;

import com.shop.common.pojo.PageResult;
import com.shop.item.bo.GoodsSearchBO;
import com.shop.item.dto.Goods;
import com.shop.item.pojo.Spu;
import com.shop.item.pojo.SpuDetail;
import com.shop.item.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Spu>> page(@RequestParam(value = "key",required = false) String key,
                                                @RequestParam(value = "saleable",required = false) Boolean saleable,
                                                @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                @RequestParam(value = "rows",defaultValue = "5") Integer rows){
        return ResponseEntity.ok(spuService.pageList(key,saleable,page,rows));
    }

    @GetMapping("getAllGoodsSearch")
    public ResponseEntity<List<GoodsSearchBO>> getAllGoodsSearch(@RequestParam(value = "spuId",required = false)Long spuId){
        return ResponseEntity.ok(spuService.getAllGoodsSearch(spuId));
    }

    @GetMapping("getGoodsDetails")
    public ResponseEntity<Map<String,Object>> getGoodsDetails(@RequestParam(value = "spuId",required = false) Long spuId){
        return ResponseEntity.ok( spuService.getGoodsDetails(spuId));
    }

    @GetMapping("detail/{spuId}")
    public ResponseEntity<SpuDetail> detail(@PathVariable Long spuId){
        return ResponseEntity.ok( spuService.getSpuDetails(spuId));
    }

}
