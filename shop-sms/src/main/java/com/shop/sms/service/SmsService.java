package com.shop.sms.service;

public interface SmsService {

    void sendVerifiedSms(String phoneNumbers,String templateCode,String templateParam);
}
