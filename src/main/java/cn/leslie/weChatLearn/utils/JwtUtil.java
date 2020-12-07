package cn.leslie.weChatLearn.utils;

import cn.leslie.weChatLearn.domain.User;
import cn.leslie.weChatLearn.domain.Video;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtil {
    /**
     * 过期时间
     */
    private static final long EXPIRE = 1000*60*60*24*7;
    /**
     * 秘钥
     */
    private static final String APPSECRET = "leslie";

    /**
     * 生成token
     * @param user
     * @return
     */
    public static String generateToken(User user){
        String token = Jwts.builder().claim("id", user.getId())
                .claim("head_img", user.getHeadImg())
                .claim("name", user.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }

    /**
     * 校检token
     * @param token
     * @return
     */
    public static Claims parseToken(String token){
        final Claims claims = Jwts.parser().setSigningKey(APPSECRET)
                .parseClaimsJws(token).getBody();
        return claims;
    }
}
