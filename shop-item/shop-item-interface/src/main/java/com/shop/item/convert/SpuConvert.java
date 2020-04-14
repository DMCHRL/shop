package com.shop.item.convert;

import com.shop.item.dto.Goods;
import com.shop.item.pojo.Spu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: liang
 * @Date: 2020/4/7 13:22
 */
@Mapper
public interface SpuConvert {
    SpuConvert INSTANCE= Mappers.getMapper(SpuConvert.class);

    Spu goods2Spu(Goods goods);

    Goods spu2Goods(Spu spu);
}
