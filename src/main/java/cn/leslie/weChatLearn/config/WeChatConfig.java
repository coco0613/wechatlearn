package cn.leslie.weChatLearn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class WeChatConfig implements Serializable {
    /**
     * 公众号id
     */
    @Value("${wxpay.appid}")
    private String appId;
    /**
     * 公众号秘钥
     */
    @Value("${wxpay.appsecret}")
    private String appSecret;
    /**
     * 能力开放平台id
     */
    @Value("${wxopen.appid}")
    private String OpenAppSecret;
    /**
     * 能力开放平台秘钥
     */
    @Value("${wxopen.appsecret}")
    private String OpenAppId;
    /**
     * 开放平台回调地址
     */
    @Value("${wxopen.redirect_url}")
    private String redirectUrl;
    /**
     * 微信开放平台二维码连接
     */
    private final String OPEN_QRCODE_URL="https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
    /**
     * 通过能力开放平台获取access_token地址
     */
    private final String OPEN_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 获取用户个人信息地址
     */
    private final String OPEN_USERINFO_URL="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 商户id
     */
    @Value("${wxpay.mer_id}")
    private String merId;
    /**
     * 支付秘钥（key）
     */
    @Value("${wxpay.key}")
    private String key;
    /**
     * 支付回调地址
     */
    @Value("${wxpay.callback}")
    private String payCallback;

    private final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public String getUNIFIED_ORDER_URL() {
        return UNIFIED_ORDER_URL;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPayCallback() {
        return payCallback;
    }

    public void setPayCallback(String payCallback) {
        this.payCallback = payCallback;
    }

    public String getOPEN_ACCESS_TOKEN_URL() {
        return OPEN_ACCESS_TOKEN_URL;
    }

    public String getOPEN_USERINFO_URL() {
        return OPEN_USERINFO_URL;
    }

    public String getOPEN_QRCODE_URL() {
        return OPEN_QRCODE_URL;
    }

    public String getOpenAppSecret() {
        return OpenAppSecret;
    }

    public void setOpenAppSecret(String openAppSecret) {
        OpenAppSecret = openAppSecret;
    }

    public String getOpenAppId() {
        return OpenAppId;
    }

    public void setOpenAppId(String openAppId) {
        OpenAppId = openAppId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
