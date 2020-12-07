package cn.leslie.weChatLearn.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装http get post
 */
public class HttpClientUtil {

    public static Gson gson = new Gson();

    /**
     * get方法
     * @param url
     * @return
     */
    public static Map<String, Object> doGet(String url){
        Map<String, Object> map = new HashMap<>();
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置连接时长、请求超时、允许自动重定向等
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) //连接超时
                .setConnectionRequestTimeout(5000) //请求超时
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true).build();//允许自动重定向
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        //响应模型
        CloseableHttpResponse response = null;
        try {
            if (response.getStatusLine().getStatusCode() == 200){
                String jsonResult = EntityUtils.toString(response.getEntity());
                map = gson.fromJson(jsonResult, map.getClass());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 封装post
     * @param url
     * @return
     */
    public static String doPost(String url, String data, int timeout) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Context-Type", "text/html; charset=UTF-8");
        if (data != null && data instanceof String){//使用字符串传参数
            StringEntity stringEntity = new StringEntity("UTF-8");
            httpPost.setEntity(stringEntity);
        }
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpEntity);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
