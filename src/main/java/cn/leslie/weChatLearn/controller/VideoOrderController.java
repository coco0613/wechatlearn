package cn.leslie.weChatLearn.controller;

import cn.leslie.weChatLearn.domain.Video;
import cn.leslie.weChatLearn.domain.VideoOrder;
import cn.leslie.weChatLearn.dto.RequestVideoOrderDto;
import cn.leslie.weChatLearn.service.VideoOrderService;
import cn.leslie.weChatLearn.service.VideoService;
import cn.leslie.weChatLearn.utils.IpUtil;
import cn.leslie.weChatLearn.utils.JsonResult;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user/api/v1/order")
public class VideoOrderController {

    private Logger logger = LoggerFactory.getLogger(VideoOrderController.class);
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("save")
    public void saveOrder(@RequestParam(value = "video_id") Integer videoId,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {

        Video dbVideo = videoService.findById(videoId);
        //获得ip
        String ip = IpUtil.getIpAddr(request);
        //String ip = "47.115.115.136";
        Integer userId = (Integer) request.getAttribute("user_id");
        //Integer userId = 1;
        RequestVideoOrderDto orderDto = new RequestVideoOrderDto();
        orderDto.setVideoId(videoId);
        orderDto.setUserId(userId);
        orderDto.setIp(ip);
        String codeUrl = videoOrderService.save(orderDto); //乱码导致codeUrl为空，替换正常的appid,openid等微信配置参数即可

        if (codeUrl == null){
            throw new NullPointerException();
        }
        try {
            //生成二维码
            //生成二维码配置
            Map<EncodeHintType, Object> hints = new HashMap<>();
            //设置纠错等级
            // EncodeHintType：指定要使用的纠错程度，例如在QR码（类型为Integer）中
            // ErrorCorrectionLevel：指定纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE, 400, 400);
            //使用字符流将二维码图片刷出至浏览器
            OutputStream out = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
