package cn.leslie.weChatLearn.mapper;

import cn.leslie.weChatLearn.domain.User;

/**
 * 用户持久层接口类
 */
public interface UserMapper {
    /**
     * 根据id查询微信用户
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 根据获取的微信openid查询用户
      * @param openid
     * @return
     */
    User findByOpenid(String openid);

    /**
     * 存储微信用户
     * @param user
     * @return
     */
    Integer insertWechatUser(User user);


}
