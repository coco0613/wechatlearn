package cn.leslie.weChatLearn.service;

import cn.leslie.weChatLearn.domain.VideoOrder;
import cn.leslie.weChatLearn.dto.RequestVideoOrderDto;

/**
 * 视频订单业务接口
 */
public interface VideoOrderService {
    /**
     * 订单生成接口
     * @param orderDto
     * @return
     */
    String save (RequestVideoOrderDto orderDto) throws Exception;

    VideoOrder findByOutTradeNo(String outTradeNo);

    Integer updateVideoOrderByOutTradeNo(VideoOrder videoOrder);

}
