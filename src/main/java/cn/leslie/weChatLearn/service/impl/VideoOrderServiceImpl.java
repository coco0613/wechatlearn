package cn.leslie.weChatLearn.service.impl;

import cn.leslie.weChatLearn.config.WeChatConfig;
import cn.leslie.weChatLearn.domain.User;
import cn.leslie.weChatLearn.domain.Video;
import cn.leslie.weChatLearn.domain.VideoOrder;
import cn.leslie.weChatLearn.dto.RequestVideoOrderDto;
import cn.leslie.weChatLearn.mapper.UserMapper;
import cn.leslie.weChatLearn.mapper.VideoMapper;
import cn.leslie.weChatLearn.mapper.VideoOrderMapper;
import cn.leslie.weChatLearn.service.VideoOrderService;
import cn.leslie.weChatLearn.utils.CommonUtils;
import cn.leslie.weChatLearn.utils.HttpClientUtil;
import cn.leslie.weChatLearn.utils.WXPayUtil;
import cn.leslie.weChatLearn.utils.WXPayXmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    private Logger logger= LoggerFactory.getLogger(VideoOrderServiceImpl.class);
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String save(RequestVideoOrderDto orderDto) throws Exception {

        //dataLogger.info("module=video_order`api=save`user_id={}`video_id={}",orderDto.getUserId(),orderDto.getVideoId());
        dataLogger.error("module=video_order`api=save`user_id={}`video_id={}",orderDto.getUserId(),orderDto.getVideoId());

        //查询订单是否存在
        Video dbVideo = videoMapper.findById(orderDto.getVideoId());
        //查询下单人是否存在
        User dbUser = userMapper.findById(orderDto.getUserId());
        //生成订单信息
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setVideoImg(dbVideo.getCoverImg());
        videoOrder.setVideoTitle(dbVideo.getTitle());
        videoOrder.setTotalFee(dbVideo.getPrice());
        videoOrder.setVideoId(dbVideo.getId());
        videoOrder.setCreateTime(new Date());
        //支付状态
        videoOrder.setState(0);
        videoOrder.setUserId(dbUser.getId());
        videoOrder.setNickname(dbUser.getName());
        videoOrder.setHeadImg(dbUser.getHeadImg());

        videoOrder.setDel(0);
        videoOrder.setIp(orderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());
        videoOrderMapper.insertVideoOrder(videoOrder);

        //统一下单
        String codeUrl = placeOrder(videoOrder);
        return codeUrl;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public Integer updateVideoOrderByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }

    /**
     * 统一下单方法
     * @param videoOrder
     * @return
     */
    private String placeOrder(VideoOrder videoOrder) throws Exception {
        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid", weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMerId());
        params.put("nonce_str", CommonUtils.generateUUID());
        params.put("body", videoOrder.getVideoTitle());
        params.put("out_trade_no", videoOrder.getOutTradeNo());
        params.put("total_fee", String.valueOf(videoOrder.getTotalFee()));
        params.put("spbill_create_ip", videoOrder.getIp());
        params.put("notify_url", weChatConfig.getPayCallback());
        params.put("trade_type", "NATIVE");
        //sign签名
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign", sign);
        //map转xml
        String payMapToXml = WXPayUtil.mapToXml(params);
        System.out.println("payMapToXml-->" + payMapToXml);
        //统一下单
        String orderStr = HttpClientUtil.doPost(weChatConfig.getUNIFIED_ORDER_URL(), payMapToXml, 4000);
        if (orderStr == null){
            return null;
        }
        Map<String, String> unifiedOrderMap = WXPayUtil.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());
        //获取codeUrl
        if (unifiedOrderMap != null){
            //由于商户号以及appid不对应,导致生成乱码信息，无法生成code_url
            // <xml>
            //  <return_code><![CDATA[FAIL]]></return_code>
            //  <return_msg><![CDATA[XMLæ ¼å¼éè¯¯]]></return_msg>
            // </xml>
            String codeUrl = unifiedOrderMap.get("code_url");
            return codeUrl;
        }
        return null;
    }


}
