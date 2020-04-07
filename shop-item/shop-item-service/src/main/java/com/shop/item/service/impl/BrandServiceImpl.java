package com.shop.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.pojo.PageResult;
import com.shop.item.mapper.BrandMapper;
import com.shop.item.mapper.CategoryMapper;
import com.shop.item.pojo.Brand;
import com.shop.item.pojo.Category;
import com.shop.item.service.BrandService;
import com.shop.item.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> pageList(String key, Integer page, Integer rows, String sortBy, boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%");
        }
        PageHelper.startPage(page,rows);
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+" "+(desc?"desc":"asc"));
        }
        List<Brand> result = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(result);
        return PageResult.build(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(Brand brand, List<Long> cid) {
        boolean flag = brandMapper.insert(brand) == 1;
        if (flag){
            cid.forEach( t -> {
                brandMapper.insertRelation(brand.getId(),t);
            });
        }
    }

    @Override
    public List<Brand> findByCid(Long cid) {
        return brandMapper.findBrandsByCid(cid);
    }
}
