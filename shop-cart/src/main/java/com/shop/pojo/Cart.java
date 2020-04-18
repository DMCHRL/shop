package com.shop.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Cart implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String username;
    private Long skuId;
    private String image;
    private String title;
    private String ownSpec;
    private Long price;
    private Integer num;
}
