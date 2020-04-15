package com.shop.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.shop.sms.constant.SmsEnum;
import com.shop.sms.entity.SendVerifierSms;
import org.springframework.stereotype.Service;
import com.shop.sms.service.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendVerifiedSms(SendVerifierSms sendVerifierSms) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GFGBKAH69iykk2fCdp9", "K7hB4JUbAGcqQYTrxVUOTLWNmMqVMT");
        IAcsClient client = new DefaultAcsClient(profile);

        String templateParam ="{\"code\":\""+sendVerifierSms.getCode()+"\"}";
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", sendVerifierSms.getPhone());
        request.putQueryParameter("SignName", SmsEnum.DEFAULT_SIGNNAME.getValue());
        request.putQueryParameter("TemplateCode", sendVerifierSms.getTemplateCode().getValue());
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
