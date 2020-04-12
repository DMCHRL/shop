package com.shop.item.service;

import com.shop.item.pojo.SpecGroup;
import com.shop.item.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {

    List<SpecGroup> groupsByCid(Long cid);

    void saveGroup(SpecGroup specGroup);

    void deleteById(Long id);

    List<SpecParam> paramByGidOrCid(Long gid,Long cid);

    void saveParam(SpecParam specParam);

    void deleteParamById(Long id);


    List<SpecParam> queryParams(Long gid,Long cid,Boolean generic,Boolean searching);

}
