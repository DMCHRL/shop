package com.shop.common.config.rabbitmq;

public enum RabbitQueueEnum {
    SHOP_SAVE_INDEX_QUEUE("SHOP.SAVE.INDEX.QUEUE");

    private final String name;

    RabbitQueueEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
