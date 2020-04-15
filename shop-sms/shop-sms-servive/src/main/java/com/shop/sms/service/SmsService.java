package com.shop.sms.service;

import com.shop.sms.entity.SendVerifierSms;

public interface SmsService {

    void sendVerifiedSms(SendVerifierSms sendVerifierSms);
}
