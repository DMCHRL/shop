package com.shop.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private Long total;
    private Integer totalPage;
    private List<T> items;

    public static <T> PageResult<T> build( Long total,Integer totalPage,List<T> result){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotalPage(totalPage);
        pageResult.setTotal(total);
        pageResult.setItems(result);
        return pageResult;
    }
}
