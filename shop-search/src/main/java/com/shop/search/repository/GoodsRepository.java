package com.shop.search.repository;

import com.shop.search.pojo.GoodsSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository extends ElasticsearchRepository<GoodsSearch,Long> {
}
