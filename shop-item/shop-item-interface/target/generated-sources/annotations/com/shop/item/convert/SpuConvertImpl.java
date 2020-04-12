package com.shop.item.convert;

import com.shop.item.dto.Goods;
import com.shop.item.pojo.Spu;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-12T10:14:13+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
public class SpuConvertImpl implements SpuConvert {

    @Override
    public Spu goods2Spu(Goods goods) {
        if ( goods == null ) {
            return null;
        }

        Spu spu = new Spu();

        spu.setId( goods.getId() );
        spu.setTitle( goods.getTitle() );
        spu.setSubTitle( goods.getSubTitle() );
        spu.setCid1( goods.getCid1() );
        spu.setCid2( goods.getCid2() );
        spu.setCid3( goods.getCid3() );
        spu.setBrandId( goods.getBrandId() );
        spu.setSaleable( goods.getSaleable() );
        spu.setValid( goods.getValid() );
        spu.setCreateTime( goods.getCreateTime() );
        spu.setLastUpdateTime( goods.getLastUpdateTime() );

        return spu;
    }
}
