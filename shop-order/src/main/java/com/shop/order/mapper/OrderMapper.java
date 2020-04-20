package com.shop.order.mapper;


import com.shop.order.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface OrderMapper extends tk.mybatis.mapper.common.Mapper<Order> {

    List<Order> queryOrderList(
            @Param("userId") Long userId,
            @Param("status") Integer status);
}
