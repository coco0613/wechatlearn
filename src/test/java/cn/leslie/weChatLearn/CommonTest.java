package cn.leslie.weChatLearn;

import cn.leslie.weChatLearn.domain.User;
import cn.leslie.weChatLearn.domain.Video;
import cn.leslie.weChatLearn.mapper.VideoMapper;
import cn.leslie.weChatLearn.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommonTest {

    @Autowired
    private VideoMapper videoMapper;

    @Test
    public void generateToken(){
        User user = new User();
        user.setId(1412);
        user.setName("怪盗基德");
        user.setHeadImg("www.jiDe.com");
        String token = JwtUtil.generateToken(user);
        System.out.println(token);
    }

    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTQxMiwiaGVhZF9pbWciOiJ3d3cuamlEZS5jb20iLCJuYW1lIjoi5oCq55uX5Z-65b63IiwiZXhwIjoxNjA2NzQ4MzE3fQ.a2h9eKohZJvKiFGz_UgUEoRN4s-D36ipX71LLEgwUfI";
        Claims claims = JwtUtil.parseToken(token);
        Integer id = (Integer) claims.get("id");
        String name = (String) claims.get("name");
        String headImg = (String) claims.get("head_img");
        System.out.println(id);
        System.out.println(name);
        System.out.println(headImg);

    }

}
