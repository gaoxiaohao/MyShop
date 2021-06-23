package com.gxh.shop.config;

/**
 * @author gxh
 */
public class AliPayConfig {


    /**
     * 应用ID,app_id，上面截图中有提到
     */
    public static String app_id = "******";

    /**
     * // 商户私钥，您的PKCS8格式RSA2私钥
     */
    public static String merchant_private_key = "******+xRdLL8iUeuacRPNq1L34e/aDBCtRmJkpqUbtJ5GdKdyu+oQyX/vCW0p4bsrovZtFqA2E/xKDZ6j0e52SZeyfsRW8a+kI8r8h3vrtVLLSquurpLS+WaAAdNk1ddjzBWbvRrRXb8t0lDcsXCIkmZypeCg12sAfDofCnQv+JyQzLgbAJCPOhaLKwBUdOLbsaZrhxdUeCASZNiTvU/giHt+CGvvWWAOzxBE42GAUPpDztFVuBKvQCP5VoO1mfqVo1LHK8VyDQ5XfMLBMnitiWJHFObkyWTW+Baj+E/WMS5nP+4u3Al6sQgGPvhibMdQelmBrM2CrAgMBAAECggEALmTdDz/P+22xCdxeLrVcNCgGYDMri79Kh8Ya9+OEqlP2LLGPqp4f5ARGdQALT/6Cc3x7sNZ6/fRGK2LMRuwHDafEQgx5RL7ary1Nn3q1qilNhfKUnWvQtFQ1w572veDkEdVdhmN7rEdjBptjDXqKnRx+e6sWvtgz6OTfxVuk+Dki8cxJIveYS9mp7xK1ZoVwFVDs38LcvBOSBXyujg70MVgFdIiS911CE27Yg489AwfRzym/U+hZ+ZAxFSOPqUL1Vjo8VhD+2ipGr8sqkC++7m1ZG0TdjD1NMESKHokU+KHfgwHC5dxhYFsxIy2CpqvgiQ0/Mi5JyiLt3MNKsYJKaQKBgQDtPZRY7XV5NDZA5b4uZa/dFU4EuwStQW53ByOdVttFFQUYxF+JzTzFUAArktfnEPo8ftJfihJCGYSOzpXc8xv695coRvPGLkl+kQ1r3k+G0bXoAm8KiOaZAhwt72HYNkG1ZFuNPX2CXI7gaRkuXnUJ3UlIv1p+XaW17BV7ptM0fQKBgQCxOVoPpY/Y/PCLkRM+2BzRoWChBmbXAoS05QA9o7sPP/KUQ5nkxM2STufuL9fA0RTBCiqBzPqcOzf5P7qwjjtqim6s8TCzhHfKn0dbq805yv5JuXCW8HeKqnD8nwacYys0VrcfOncD7daeEomePR+kzh4DUGOogVKl902c1gG6RwKBgQDUI8uClZwFLL4S3B1qyb0vmbxxGHa/IpJP109ug1uBc6FiGz8/LwpXegkc7asoURMig1lE22cmOkFBndN+htmPWlSNTJzxqzRG/BgfS+SbLu1VsM9wgSiKy8s8/CDnQOUS+dGwA7s2leOhIfnFb+uCTaEZbxrRgpYKjjhLoMqd1QKBgH82ftpKaqZEclqMqbbrpEwpvdX4ZWujKCU9WF3moIjCy1r354jaaAHE2CEWgJWsv2wN4xNHUwFAVN56i4Teo/HHKsjDXMUPqnlsuekoumjouH2tOg9uzzBfRjlf4Xvyh3nLYSluay4L1ImlHtnSYFuJhOu2EcNMCUqO6UxSJe1TAoGBALu3vP65kxAsChrLlApUWQmkAMEedxjt/+8SuqLd0uuImCtXxHrmPRpZgb3sWdbHnXXEmRvwtBazAe5lDoRKTjC8PXxo4NPKBCsSSQqINmf7O1NV7mUH2+nuJf8fh+jy/Yq0UnOWEZmbMYxbtFWdunB2kiRpF3AWCAE+fToOqbtr";

    /**
     * // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/appDaily.htm 对应APPID下的支付宝公钥。
     */
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgC6WwNZuit3zlyBvT447nniu5LpfYckplmL+/c6Nr0RqLpxUfdyLGW9Ha2h+mNE8xixkRCziyWh74poYpAiWf93uQ/sxSt7m9EZhNcY1QYUSYm0VYWgCsaNAPve/YWB/8AxgI9Bo6IjJ5zL1D+DAgPtTcrABo0NgbS0y55OYTS1EBYhjHDpARUjK7PWCnUobsfJq2N0FhI7UreJsHIfP+fKp0aQmFRC/0BCwZ7RKAmMWakNxjhncOZiL2r+W63SqVFPSvedYHO0Hx8Qa2rhCJeLkULYGE5FsiT5JhH2zYvLSPi1uQj7ImtUvDczN1qIEhmkjMh42UTB8Xj62X/P+kQIDAQAB";

    /**
     * // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问.如果只是测试使用,那么设置成自己项目启动后可以访问到的一个路径,作为支付宝发送通知的路径(有什么用暂时没发现)
     */
    public static String notify_url = "http://localhost:8081/order/alipay-callback-notify-url";

    /**
     * // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问.如果只是测试使用,那么设置成自己项目启动后可以访问到的一个路径.是支付正常完成后,会访问的路径.
     */
    public static String return_url = "http://localhost:8081/order/alipay-callback-return-sult";
   
    public static String sign_type = "RSA2";
    /**
     *
     */
    public static String charset = "utf-8";
    /**
     *
     */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

}
