package cn.leslie.weChatLearn.mapper;

import cn.leslie.weChatLearn.domain.Video;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 持久层接口类
 */
public interface VideoMapper {
    /**
     * 查询所有视屏
     * @return
     */
    List<Video> findAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Video findById(Integer id);

    /**
     * 修改视频信息
     * @param video
     * @return
     */
    int updateVideoInfo(Video video);

    /**
     * 根据id删除视频
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 新增视频
     * @param video
     * @return
     */
    int saveVideo(Video video);

}
