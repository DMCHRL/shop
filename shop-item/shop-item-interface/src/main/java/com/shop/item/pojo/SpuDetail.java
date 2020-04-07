package com.shop.item.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_spu_detail")
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SpuDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spuId;
    private String description;
    private String genericSpec;
    private String specialSpec;
    private String packingList;
    private String afterService;

}
