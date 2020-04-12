package com.shop.item.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class GoodsSearchBO {

    private Long id;
    private String all; //标题 分类 品牌
    private String subTitle;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Long brandId;
    private Date createTime;
    private List<Long> price;
    private String skus;
    private Map<String,Object> specs;
}
