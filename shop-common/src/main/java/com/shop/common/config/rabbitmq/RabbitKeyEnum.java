package com.shop.common.config.rabbitmq;

public enum RabbitKeyEnum {
    ITEM_INSERT("ITEM.SAVE"),
    VERIFIED_SMS("VERIFIED.SMS")
    ;

    private String name;

    RabbitKeyEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
