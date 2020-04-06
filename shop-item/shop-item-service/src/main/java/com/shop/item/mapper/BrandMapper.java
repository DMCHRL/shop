package com.shop.item.mapper;

import com.shop.item.pojo.Brand;
import com.shop.item.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {

    @Insert(value = "insert into tb_category_brand(category_id,brand_id) values(#{id},#{cid})")
    void insertRelation(@Param(value = "id")Long id,@Param(value = "cid")Long cid);
}
