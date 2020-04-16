package com.shop.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfo {

    private Long id;

    private String username;
}
