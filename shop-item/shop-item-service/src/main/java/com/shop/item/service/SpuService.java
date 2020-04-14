package com.shop.item.service;

import com.shop.common.pojo.PageResult;
import com.shop.item.bo.GoodsSearchBO;
import com.shop.item.dto.Goods;
import com.shop.item.pojo.Sku;
import com.shop.item.pojo.Spu;
import com.shop.item.pojo.SpuDetail;

import java.util.List;
import java.util.Map;

public interface SpuService {

    /**
     * 分页查询商品(后台)
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    PageResult<Spu> pageList(String key,Boolean saleable,int page,int rows);

    /**
     * 保存商品
     * @param goods
     */
    void saveGoods(Goods goods);

    SpuDetail getSpuDetails(Long supId);

    /**
     * 获取数据库所有商品信息
     * @return
     */
    List<GoodsSearchBO> getAllGoodsSearch(Long id);

    List<Sku> findBySpuId(Long spuId);

    Map<String,Object> getGoodsDetails(Long spuId);

}
