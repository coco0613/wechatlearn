package cn.leslie.weChatLearn.controller;

import cn.leslie.weChatLearn.domain.Video;
import cn.leslie.weChatLearn.service.VideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/video")
public class VideoController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private VideoService videoService;

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("findAll")
    public Object findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageHelper.startPage(page,size);
        List<Video> videoList = videoService.findAll();
        PageInfo pageInfo = new PageInfo(videoList);
        Map<String, Object> map = new HashMap<>();
        map.put("total_size", pageInfo.getTotal());
        map.put("page_num", pageInfo.getPageNum());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 根据id查询视屏
     * @param id
     * @return
     */
    @GetMapping("findById")
    public Object findById(@RequestParam(value = "id", required = true) Integer id){
        return videoService.findById(id);
    }

}
