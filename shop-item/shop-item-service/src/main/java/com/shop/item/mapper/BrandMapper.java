package com.shop.item.mapper;

import com.shop.item.pojo.Brand;
import com.shop.item.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Insert(value = "insert into tb_category_brand(category_id,brand_id) values(#{id},#{cid})")
    void insertRelation(@Param(value = "id")Long id,@Param(value = "cid")Long cid);

    @Select(value = "SELECT\n" +
            "\tb.*\n" +
            "FROM\n" +
            "\ttb_brand b\n" +
            "LEFT JOIN tb_category_brand c ON b.id = c.brand_id\n" +
            "WHERE\n" +
            "\tc.category_id = #{cid}")
    List<Brand> findBrandsByCid(@Param(value = "cid")Long cid);
}
