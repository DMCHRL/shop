package com.shop.common.config.rabbitmq;

public enum RabbitExchangeEnum {
    SHEP_ITEM_EXCHANGE("SHEP.ITEM.EXCHANGE");

    private String name;

    RabbitExchangeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
