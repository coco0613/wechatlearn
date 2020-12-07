package cn.leslie.weChatLearn.interceoter;

import cn.leslie.weChatLearn.utils.JsonResult;
import cn.leslie.weChatLearn.utils.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     *  拦截器方法，获取登录人token,id,name等信息
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader("token");
        if (accessToken == null){
            accessToken = request.getParameter("token");
        }
        if (accessToken != null){
            Claims claims = JwtUtil.parseToken(accessToken);
            if(claims != null){
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");
                request.setAttribute("user_id", id);
                request.setAttribute("name", name);
                //普通用户
                return true;
            }
        }
        //将结果返回值前端
        sendMsgToJson(response, JsonResult.buildError("请登录"));
        return false;
    }

    /**
     * 响应json数据格式给前端
     * @param response
     * @param obj
     */
    public static void sendMsgToJson(HttpServletResponse response, Object obj){
        Gson gson = new Gson();
        try {
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.print(gson.toJson(obj));
            printWriter.close();
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
