package com.shop.item.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Table(name = "tb_spec_group")
@Data
@Accessors(chain = true)
public class SpecGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private String name;

    @Transient
    private List<SpecParam> params;
}
