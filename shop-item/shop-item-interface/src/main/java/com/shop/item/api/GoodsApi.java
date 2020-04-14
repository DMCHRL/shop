package com.shop.item.api;

import com.shop.item.bo.GoodsSearchBO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@RequestMapping("spu")
public interface GoodsApi  {

    /**
     * 获取所有商品信息
     * @return
     */
    @GetMapping("getAllGoodsSearch")
    public List<GoodsSearchBO> getAllGoodsSearch(@RequestParam(value = "spuId",required = false)Long spuId);

    @GetMapping("getGoodsDetails")
    public Map<String,Object> getGoodsDetails(@RequestParam(value = "spuId",required = false) Long spuId);

}
