package cn.leslie.weChatLearn.service;

import cn.leslie.weChatLearn.domain.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Test
    void findAll() {
        List<Video> all = videoService.findAll();
        for (Video video: all) {
            System.out.println(video.getTitle());
        }
    }

    @Test
    void findById() {

        Video video = videoService.findById(2);
        System.out.println(video);
    }

    @Test
    void updateVideoInfo() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void saveVideo() {
    }
}