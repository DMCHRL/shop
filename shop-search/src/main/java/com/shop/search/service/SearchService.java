package com.shop.search.service;

import com.shop.common.pojo.PageResult;
import com.shop.search.bo.SearchResult;
import com.shop.search.dto.GoodsSearchDTO;
import com.shop.search.pojo.GoodsSearch;

public interface SearchService  {

    SearchResult search(GoodsSearchDTO goodsSearchDTO);
}
