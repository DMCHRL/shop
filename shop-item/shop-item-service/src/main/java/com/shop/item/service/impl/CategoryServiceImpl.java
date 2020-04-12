package com.shop.item.service.impl;

import com.shop.item.mapper.CategoryMapper;
import com.shop.item.pojo.Category;
import com.shop.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getByPid(Long parentId) {
        return categoryMapper.select(new Category().setParentId(parentId));
    }

    @Override
    public List<Category> getByCidList(List<Long> cidList) {
        Example example = new Example(Category.class);
        if(!CollectionUtils.isEmpty(cidList)){
            Example.Criteria criteria = example.createCriteria();
            cidList.forEach( t -> {
                criteria.orEqualTo("id" ,t);
            });
        }
        return categoryMapper.selectByExample(example);
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
