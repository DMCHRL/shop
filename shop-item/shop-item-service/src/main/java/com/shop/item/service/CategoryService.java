package com.shop.item.service;

import com.shop.item.pojo.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 根据pid获取分类
     * @param parentId
     * @return
     */
    List<Category> getByPid(Long parentId);
}
