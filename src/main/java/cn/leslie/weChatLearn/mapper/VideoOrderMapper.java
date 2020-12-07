package cn.leslie.weChatLearn.mapper;

import cn.leslie.weChatLearn.domain.VideoOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoOrderMapper {

    /**
     * 新增订单
     * @param videoOrder
     * @return
     */
    Integer insertVideoOrder(VideoOrder videoOrder);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    VideoOrder findById(Integer id);

    /**
     * 根据订单号查询订单
     * @param outTradeNo
     * @return
     */
    VideoOrder findByOutTradeNo(String outTradeNo);

    /**
     * 逻辑删除
     * @param id
     * @param userId
     * @return
     */
    Integer del(@Param("id") Integer id, @Param("userId") Integer userId);

    /**
     * 查询我的所有订单
     * @param userId
     * @return
     */
    List<VideoOrder> findAllMyOrder(Integer userId);

    /**
     * 根据订单流水号更新
     * @param videoOrder
     * @return
     */
    Integer updateVideoOrderByOutTradeNo(VideoOrder videoOrder);

}
