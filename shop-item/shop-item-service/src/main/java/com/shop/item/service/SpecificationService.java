package com.shop.item.service;

import com.shop.item.pojo.SpecGroup;

import java.util.List;

public interface SpecificationService {

    List<SpecGroup> groupsByCid(Long cid);

    void saveGroup(SpecGroup specGroup);

    void deleteById(Long id);
}
