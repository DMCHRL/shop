package com.shop.item.service.impl;

import com.shop.item.mapper.SpecGroupMapper;
import com.shop.item.mapper.SpecParamMapper;
import com.shop.item.pojo.SpecGroup;
import com.shop.item.pojo.SpecParam;
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

    @Override
    public List<SpecParam> paramByGidOrCid(Long gid,Long cid) {
        SpecParam specParam = new SpecParam();
        if(gid != null){
            specParam.setGroupId(gid);
        }
        if(cid != null){
            specParam.setCid(cid);
        }
        return  specParamMapper.select(specParam);
    }

    @Override
    public void saveParam(SpecParam specParam) {
        if(specParam.getId() == null){
            specParamMapper.insert(specParam);
        }else {
            specParamMapper.updateByPrimaryKey(specParam);
        }
    }

    @Override
    public void deleteParamById(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        if(gid != null){
            specParam.setGroupId(gid);
        }
        if(cid != null){
            specParam.setCid(cid);
        }
        if(generic != null){
            specParam.setGeneric(generic);
        }
        if(searching != null){
            specParam.setSearching(searching);
        }
        return  specParamMapper.select(specParam);
    }
}
