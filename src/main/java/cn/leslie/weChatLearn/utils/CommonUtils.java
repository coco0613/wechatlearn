package cn.leslie.weChatLearn.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;

public class CommonUtils {
    /**
     * 生成32为随机字符串
     * @return
     */
    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, 32);
    }

//    /**
//     * MD5工具类
//     * @param data
//     * @return
//     */
//    public static String MD5(String data){
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            byte[] bytes = messageDigest.digest(data.getBytes("UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            for (byte item: bytes) {
//                sb.append(Integer.toHexString(item & 0xFF | 0x100).substring(1, 3));
//            }
//            return sb.toString().toUpperCase();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    /**
     * md5常用工具类
     * @param data
     * @return
     */
    public static String MD5(String data){
        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte [] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }



}
