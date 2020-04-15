package com.shop.sms.constant;

public enum SmsEnum {

    VERIFIED_TEMPLATECODE("SMS_187940891"),
    DEFAULT_SIGNNAME("乐优商城"),
    ;

    private String value;

    SmsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
