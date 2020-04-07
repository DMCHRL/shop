package com.shop.item.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Table(name = "tb_spec_param")
@Data
@Accessors(chain = true)
public class SpecParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;


}
