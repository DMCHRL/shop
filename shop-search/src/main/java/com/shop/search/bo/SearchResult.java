package com.shop.search.bo;

import com.shop.common.pojo.PageResult;
import com.shop.item.pojo.Brand;
import com.shop.search.pojo.GoodsSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult extends PageResult<GoodsSearch> {

    private List<Map<String,Object>> categories;

    private List<Brand> brands;

    private List<Map<String,Object>> specs;

    public SearchResult(Long total, Integer totalPage, List<GoodsSearch> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

}
