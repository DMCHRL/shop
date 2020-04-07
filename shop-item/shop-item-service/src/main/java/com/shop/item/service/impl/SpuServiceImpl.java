package com.shop.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.pojo.PageResult;
import com.shop.item.convert.SpuConvert;
import com.shop.item.dto.Goods;
import com.shop.item.mapper.SkuMapper;
import com.shop.item.mapper.SpuDetailMapper;
import com.shop.item.mapper.SpuMapper;
import com.shop.item.pojo.Sku;
import com.shop.item.pojo.Spu;
import com.shop.item.pojo.SpuDetail;
import com.shop.item.service.SpuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;


    @Override
    public PageResult<Spu> pageList(String key, Boolean saleable, int page, int rows) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        criteria.andEqualTo("saleable",saleable);
        PageHelper.startPage(page,rows);
        List<Spu> spus = spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);
        return PageResult.build(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void saveGoods(Goods goods) {
        Spu spu = SpuConvert.INSTANCE.goods2Spu(goods);
        Date date = new Date();
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(date).setLastUpdateTime(date);
        spuMapper.insert(spu);
        List<Sku> skus = goods.getSkus();
        skus.forEach( t -> {
            t.setSpuId(spu.getId()).setCreateTime(date).setLastUpdateTime(date);
            skuMapper.insert(t);
        });
        SpuDetail spuDetail = goods.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);
    }
}
