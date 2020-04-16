package com.shop.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RegisterUser {

    private String username;
    private String password;
    private String confirmPassword;
    private String phone;
    private String code;
}
