package cn.leslie.weChatLearn.service;

import cn.leslie.weChatLearn.domain.User;

/**
 * 用户业务类接口
 */
public interface UserService {

    User saveWechatUser(String code);

}
