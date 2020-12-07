package cn.leslie.weChatLearn.excepton;

import cn.leslie.weChatLearn.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WeChatLearnExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public JsonResult Handler(Exception e){
        if (e instanceof WeChatLearnException){
            WeChatLearnException weChatLearnException = (WeChatLearnException) e;
            return JsonResult.buildError(weChatLearnException.getMsg(), -1);
        }else {
            return JsonResult.buildError("全局异常，未知错误");
        }
    }

}
