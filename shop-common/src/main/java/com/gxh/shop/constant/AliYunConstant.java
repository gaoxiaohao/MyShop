package com.gxh.shop.constant;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;

/**
 * @author gxh
 */
public class AliYunConstant {

    String PRODUCT = "Dysmsapi";

    String DOMAIN = "dysmsapi.aliyuncs.com";

    String ACCESSKEYID = "LTAIRY5MouklfnHW";

    String ACCESSKEYSECRTE = "CU2XWWdI5sqd7KYJwJX8C2sOn300YS";

    String OK = "OK";

    String ENDPOINT = "oss-cn-beijing.aliyuncs.com";

    String BUCKETNAME = "gzh0302.oss-cn-beijing.aliyuncs.com";


    String BUCKET_NAME = "gzh0302";


    public  Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(ACCESSKEYID)
                // 您的AccessKey Secret
                .setAccessKeySecret(ACCESSKEYSECRTE);
        // 访问的域名
        config.endpoint = DOMAIN;
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
//    String randomNumber = random();
//
//        System.setProperty("sun.net.client.defaultConnectTimeout", "300000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "300000");
//    IClientProfile profile = DefaultProfile.getProfile("cn-beijing", ACCESSKEYID, ACCESSKEYSECRTE);
//        DefaultProfile.addEndpoint("cn-beijing", PRODUCT, DOMAIN);
//    IAcsClient acsClient = new DefaultAcsClient(profile);
//    SendSmsRequest request = new SendSmsRequest();
//        request.setPhoneNumbers(telephone);
//        request.setSignName("小浩的home");
//        request.setTemplateCode("SMS_179295281");
//        request.setTemplateParam("{\"name\":\"gxh\", \"code\":\"" + randomNumber + "\"}");
//    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//        if (sendSmsResponse.getCode() != null && OK.equals(sendSmsResponse.getCode())) {
//        log.info("发送成功");
//    }
//    boolean isPhone = checkNumber(telephone);
//        if (isPhone) {
//        redisUtils.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, randomNumber);
//        redisUtils.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, 120);
//        return CommonResult.success(randomNumber, "获取验证码成功");
//    }
//        return CommonResult.success("获取验证码失败");
}
