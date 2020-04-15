package com.shop.sms.entity;

import com.shop.sms.constant.SmsEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SendVerifierSms implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phone;

    private SmsEnum  templateCode;

    private String code;

}
