package com.shop.item.service.impl;

import com.shop.item.mapper.SpecGroupMapper;
import com.shop.item.mapper.SpecParamMapper;
import com.shop.item.pojo.SpecGroup;
import com.shop.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationServiceImpl  implements SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> groupsByCid(Long cid) {
        return specGroupMapper.select(new SpecGroup().setCid(cid));
    }

    @Override
    public void saveGroup(SpecGroup specGroup) {
        if(specGroup.getId() == null){
            specGroupMapper.insert(specGroup);
        }else {
            specGroupMapper.updateByPrimaryKey(specGroup);
        }
    }

    @Override
    public void deleteById(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }
}
