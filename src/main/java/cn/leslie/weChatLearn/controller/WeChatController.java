package cn.leslie.weChatLearn.controller;

import cn.leslie.weChatLearn.config.WeChatConfig;
import cn.leslie.weChatLearn.domain.User;
import cn.leslie.weChatLearn.domain.VideoOrder;
import cn.leslie.weChatLearn.service.UserService;
import cn.leslie.weChatLearn.service.VideoOrderService;
import cn.leslie.weChatLearn.utils.JsonResult;
import cn.leslie.weChatLearn.utils.JwtUtil;
import cn.leslie.weChatLearn.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("api/v1/wechat")
public class WeChatController {
    
    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoOrderService videoOrderService;
    
    /**
     * 拼装扫一扫登录url
     * @return
     */
    @GetMapping("login_url")
    @ResponseBody
    public JsonResult loginUrl(@RequestParam(value = "access_page", required = true) String access_page) throws UnsupportedEncodingException {
        String redirectUrl = weChatConfig.getRedirectUrl();//获取开放平台回调地址
        String callback = URLEncoder.encode(redirectUrl, "GBK");//进行编码
        String qrcodeUrl = String.format(weChatConfig.getOPEN_QRCODE_URL(), weChatConfig.getOpenAppId(), callback, access_page);
        return JsonResult.buildSuccess(qrcodeUrl);
    }

    @GetMapping("user/callback")
    public void wechatUserCallback(@RequestParam(value = "code", required = true) String code,
                                         @RequestParam(value = "state") String state,
                                         HttpServletResponse response) throws IOException {
        System.out.println("code->" +code);
        System.out.println("state->" +state);
        User user = userService.saveWechatUser(code);
        if (user != null){
            //TODO 生成JWT
            String token = JwtUtil.generateToken(user);
                //获取token重定向到当前浏览页面
                response.sendRedirect(state +"?token=" + token +"&name=" + URLEncoder.encode(user.getName(), "UTF-8") +"&head_img=" + user.getHeadImg());
        }
    }

    /**
     * 支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("order/callback")
    public void wechatOrderCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream in = request.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine())!= null) {
            stringBuffer.append(line);
        }
        in.close();
        bufferedReader.close();
        Map<String, String> callbackMap = WXPayUtil.xmlToMap(stringBuffer.toString());
        SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);
        //判断签名是否正确
        if (WXPayUtil.checkSign(sortedMap, weChatConfig.getKey())){
            if ("SUCCESS".equals(sortedMap.get("result_code"))){
                //跟新订单状态
                VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(sortedMap.get("out_trade_no"));
                //判断是否下单
                if (dbVideoOrder.getState() == 0){
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setOutTradeNo(sortedMap.get("out_trade_no"));
                    videoOrder.setNotifyTime(new Date());
                    videoOrder.setState(1);
                    Integer rows = videoOrderService.updateVideoOrderByOutTradeNo(videoOrder);
                    if (rows == 1){ //通知微信订单处理成功
                        response.setContentType("text/xml");
                        response.getWriter().println("success");
                    }
                }
                response.setContentType("text/xml");
                response.getWriter().println("fail");
            }
        }
    }

}
