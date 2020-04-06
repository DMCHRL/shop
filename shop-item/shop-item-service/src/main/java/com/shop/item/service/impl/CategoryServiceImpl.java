package com.shop.item.service.impl;

import com.shop.item.mapper.CategoryMapper;
import com.shop.item.pojo.Category;
import com.shop.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getByPid(Long parentId) {
        return categoryMapper.select(new Category().setParentId(parentId));
    }
}
