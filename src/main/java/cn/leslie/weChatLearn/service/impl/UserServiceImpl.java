package cn.leslie.weChatLearn.service.impl;

import cn.leslie.weChatLearn.config.WeChatConfig;
import cn.leslie.weChatLearn.domain.User;
import cn.leslie.weChatLearn.mapper.UserMapper;
import cn.leslie.weChatLearn.service.UserService;
import cn.leslie.weChatLearn.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveWechatUser(String code) {

        String accessTokenUrl = String.format(weChatConfig.getOPEN_ACCESS_TOKEN_URL(), weChatConfig.getOpenAppId(), weChatConfig.getOpenAppSecret(), code);
        Map<String, Object> baseMap = HttpClientUtil.doGet(accessTokenUrl);
        if (baseMap == null || baseMap.isEmpty()){
            return null;
        }
        String accessToken = (String) baseMap.get("access_token");
        String openid = (String) baseMap.get("openid");
        //根据openid判断用户是否已经存在
        User dbUser = userMapper.findByOpenid(openid);
        if (dbUser != null){
            return dbUser;
        }
        //通过access_token获取用户信息
        String userInfoUrl = String.format(weChatConfig.getOPEN_USERINFO_URL(),accessToken,openid);
        Map<String, Object> userInfoMap = HttpClientUtil.doGet(userInfoUrl);
        if (userInfoMap == null || userInfoMap.isEmpty()){
            return null;
        }
        String nickname = (String) userInfoMap.get("nickname");
        Integer sex = (Integer) userInfoMap.get("sex");
        String province = (String) userInfoMap.get("province");
        String city = (String) userInfoMap.get("city");
        String country = (String) userInfoMap.get("country");
        String headimgurl = (String) userInfoMap.get("headimgurl");
        StringBuffer sb = new StringBuffer(country).append("-").append(province).append("-").append(city);
        String finalAddress = sb.toString();
        //解决中文乱码问题
        try {
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //存储用户信息
        User user = new User();
        user.setName(nickname);
        user.setOpenid(openid);
        user.setHeadImg(headimgurl);
        user.setSex(sex);
        user.setCity(finalAddress);
        user.setCreateTime((Timestamp) new Date());
        //调用存储
        userMapper.insertWechatUser(user);
        return user;
    }
}
