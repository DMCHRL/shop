package com.shop.common.pojo;

import lombok.Data;
import lombok.val;

import java.util.List;

@Data
public class PageResult<T> {

    private Long total;
    private Integer totalPage;
    private List<T> items;

    public static <T> PageResult<T> build(Long total,List<T> result){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setItems(result);
        return pageResult;
    }
}
