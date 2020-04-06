package com.shop.item.service;

import com.shop.common.pojo.PageResult;
import com.shop.item.pojo.Brand;
import com.shop.item.pojo.Category;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BrandService {

    /**
     * 分页查询 可改为dto封装请求参数
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    PageResult<Brand> pageList(String key, Integer page, Integer rows, String sortBy, boolean desc);

    void saveBrand(Brand brand,List<Long> cid);

}
