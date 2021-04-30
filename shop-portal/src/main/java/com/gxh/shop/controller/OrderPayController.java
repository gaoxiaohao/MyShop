package com.gxh.shop.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gxh.shop.config.AliPayConfig;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author gxh
 */
@Controller
@Slf4j
@Api(tags = "OrderPayController")
@RequestMapping("/order/pay")
public class OrderPayController {

    @RequestMapping(value = "/toPay", method = RequestMethod.GET)
    @ResponseBody
    public String toPay(String id, String sumMoney) {
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AliPayConfig.return_url);
        alipayRequest.setNotifyUrl(AliPayConfig.notify_url);
        try {
            alipayRequest.setBizContent("{\"out_trade_no\":\""+ id +"\","
                    + "\"total_amount\":\""+ sumMoney +"\","
                    + "\"subject\":\""+ "手机" +"\","
                    + "\"body\":\""+ "iphone12" +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}"
            );
            String result;
            result = alipayClient.pageExecute(alipayRequest).getBody();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("alipay-callback-return-sult")
    @ResponseBody
    public String successResult(HttpServletRequest request, HttpServletResponse response) {
        //可以根据request.getParameterMap()获得到调用接口时传递的一些参数去做后续的业务处理
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] outTradeNos = parameterMap.get("out_trade_no");
        String id = outTradeNos[0];

        return "支付成功";
    }
}
