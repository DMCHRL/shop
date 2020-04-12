package com.shop.search;

import com.shop.item.bo.GoodsSearchBO;
import com.shop.search.client.GoodsClient;
import com.shop.search.pojo.GoodsSearch;
import com.shop.search.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShopSearchTest {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void addIndex(){
        elasticsearchTemplate.createIndex(GoodsSearch.class);
    }

    @Test
    public void addValue(){
        List<GoodsSearch> goodsSearchList = new ArrayList<>();
        List<GoodsSearchBO> allGoodsSearch = goodsClient.getAllGoodsSearch();
        allGoodsSearch.forEach( t -> {
            GoodsSearch goodsSearch = new GoodsSearch();
            goodsSearch.setId(t.getId());
            goodsSearch.setAll(t.getAll());
            goodsSearch.setSubTitle(t.getSubTitle());
            goodsSearch.setCid1(t.getCid1());
            goodsSearch.setCid2(t.getCid2());
            goodsSearch.setCid3(t.getCid3());
            goodsSearch.setBrandId(t.getBrandId());
            goodsSearch.setCreateTime(t.getCreateTime());
            goodsSearch.setPrice(t.getPrice());
            goodsSearch.setSkus(t.getSkus());
            goodsSearch.setSpecs(t.getSpecs());
            goodsSearchList.add(goodsSearch);
        });
        goodsRepository.saveAll(goodsSearchList);
    }
}
