package com.gxh.shop.controller;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.gxh.shop.api.CommonResult;
import com.gxh.shop.constant.AliYunConstant;
import com.gxh.shop.dto.OssCallbackResult;
import com.gxh.shop.dto.OssPolicyResult;
import com.gxh.shop.service.OssService;
import com.gxh.shop.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author gxh
 */
@Controller
@Slf4j
@Api(tags = "OssController-阿里云上传管理")
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;
    @Autowired
    private RedisService redisService;


    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    public OssPolicyResult policy() {
        return ossService.policy();
    }


    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public OssCallbackResult callback(HttpServletRequest request) {
        return ossService.callback(request);
    }

    @ApiOperation("验证码发送")
    @RequestMapping(value = "/generateAuthCode", method = RequestMethod.GET)
    public CommonResult<String> generateAuthCode(@RequestParam String telephone) throws Exception {

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        AliYunConstant aliYunConstant = new AliYunConstant();
        Client client = aliYunConstant.createClient();
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(telephone);
        request.setSignName("小浩的home");
        request.setTemplateCode("SMS_179295281");
        request.setTemplateParam("{\"name\":\"gxh\", \"code\":\"" + sb.toString() + "\"}");
        SendSmsResponse sendSmsResponse = client.sendSms(request);
        if (sendSmsResponse.getBody().getCode() != null && "OK".equals(sendSmsResponse.getBody().getCode())) {
            log.info("短信发送成功:${}", sendSmsResponse.getBody().getCode());
        }

        redisService.set(telephone, sb.toString(), 50000);
        return CommonResult.success(sb.toString(), "获取验证码成功");
    }


    @ApiOperation("验证码校验")
    @RequestMapping(value = "/verifyAuthCode", method = RequestMethod.GET)
    public CommonResult<String> verifyAuthCode(@RequestParam String code,@RequestParam String phone) throws Exception {
        if (StringUtils.isEmpty(code)) {
            return CommonResult.failed("请输入验证码");
        }
        String realAuthCode = redisService.get(phone).toString();
        boolean result = code.equals(realAuthCode);
        if (result) {
            return CommonResult.success(null, "验证码校验成功");
        } else {
            return CommonResult.failed("验证码不正确");
        }
    }


}
