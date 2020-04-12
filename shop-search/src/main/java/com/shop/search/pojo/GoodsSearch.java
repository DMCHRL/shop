package com.shop.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: liang
 * @Date: 2020/4/9 14:18
 */
@Document(indexName = "goods",shards = 1,replicas = 0)
@Data
public class GoodsSearch {

    @Id
    private Long id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String all; //标题 分类 品牌
    @Field(type = FieldType.Keyword,index = false)
    private String subTitle;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Long brandId;
    private Date createTime;
    private List<Long> price;
    @Field(type = FieldType.Keyword,index = false)
    private String skus;
    private Map<String,Object> specs;

}
