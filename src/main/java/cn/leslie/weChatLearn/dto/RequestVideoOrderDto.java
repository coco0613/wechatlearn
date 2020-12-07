package cn.leslie.weChatLearn.dto;

import cn.leslie.weChatLearn.domain.VideoOrder;

/**
 * 用户订单对象
 */
public class RequestVideoOrderDto extends VideoOrder {

    private Integer videoId;
    private Integer userId;

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    private String ip;

    @Override
    public Integer getVideoId() {
        return videoId;
    }

    @Override
    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
