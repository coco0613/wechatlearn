package cn.leslie.weChatLearn.service.impl;

import cn.leslie.weChatLearn.domain.Video;
import cn.leslie.weChatLearn.mapper.VideoMapper;
import cn.leslie.weChatLearn.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(Integer id) {
        dataLogger.info("module=video`api=findById");
        return videoMapper.findById(id);
    }

    @Override
    public int updateVideoInfo(Video video) {
        return videoMapper.updateVideoInfo(video);
    }

    @Override
    public int deleteById(Integer id) {
        return videoMapper.deleteById(id);
    }

    @Override
    public int saveVideo(Video video) {
        return videoMapper.saveVideo(video);
    }
}
