package com.shop.item.dto;

import com.shop.item.pojo.Sku;
import com.shop.item.pojo.Spu;
import com.shop.item.pojo.SpuDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: liang
 * @Date: 2020/4/7 11:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Goods extends Spu{

    private List<Sku> skus;

    private SpuDetail spuDetail;
}
