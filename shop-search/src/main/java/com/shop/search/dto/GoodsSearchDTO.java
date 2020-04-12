package com.shop.search.dto;

import java.util.List;
import java.util.Map;

public class GoodsSearchDTO {

    private String key;

    private Integer page;

    private Map<String,Object> filter;

    private final static Integer DEFAULT_SIZE = 20;

    private final static Integer DEFAULT_PAGE = 1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        return Math.max(DEFAULT_PAGE,page);
    }

    public Integer getSize() {
      return DEFAULT_SIZE;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
