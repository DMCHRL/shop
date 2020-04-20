package com.shop.order.mapper;

import com.shop.order.pojo.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;


@Mapper
public interface OrderStatusMapper extends tk.mybatis.mapper.common.Mapper<OrderStatus>{
}
