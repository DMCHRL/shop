package com.shop.item.service;

import com.shop.common.pojo.PageResult;
import com.shop.item.dto.Goods;
import com.shop.item.pojo.Category;
import com.shop.item.pojo.Spu;

import java.util.List;

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

}
