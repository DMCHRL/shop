package com.shop.common.constant;

public enum BusinessEnum {
    REGISTER("REGISTER")
    ;
    private String value;

    BusinessEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
