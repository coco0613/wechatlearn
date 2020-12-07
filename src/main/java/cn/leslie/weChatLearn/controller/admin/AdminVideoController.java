package cn.leslie.weChatLearn.controller.admin;

import cn.leslie.weChatLearn.domain.Video;
import cn.leslie.weChatLearn.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/api/v1/video")
public class AdminVideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 修改视频信息
     * @param video
     * @return
     */
    @PutMapping("updateVideoInfo")
    public Object updateVideoInfo(@RequestBody Video video){
        return videoService.updateVideoInfo(video);
    }

    /**
     * 根据id删除视频
     * @param id
     * @return
     */
    @DeleteMapping("deleteById")
    public Object deleteById(@RequestParam(value = "id", required = true) Integer id){
        return videoService.deleteById(id);
    }

    /**
     * 新新增视频数据
     * @param video
     * @return
     */
    @PostMapping("saveVideo")
    public Object saveVideo(@RequestBody Video video){
        Integer rows = videoService.saveVideo(video);
        System.err.println("返回保存对象的id-->" + video.getId());
        return rows;
    }
}
