package cn.leslie.weChatLearn.service;

import cn.leslie.weChatLearn.domain.Video;

import java.util.List;

/**
 * 视屏业务类接口
 */
public interface VideoService {

    List<Video> findAll();

    Video findById(Integer id);

    int updateVideoInfo(Video video);

    int deleteById(Integer id);

    int saveVideo(Video video);

}
