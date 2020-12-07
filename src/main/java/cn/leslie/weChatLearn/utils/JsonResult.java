package cn.leslie.weChatLearn.utils;

/**
 * 结果工具类
 */
public class JsonResult {

    private Integer code;
    private String msg;
    private Object data;

    public JsonResult(){

    }
    public JsonResult(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    // 成功，传入数据
    public static JsonResult buildSuccess() {
        return new JsonResult(0, null, null);
    }

    // 成功，传入数据
    public static JsonResult buildSuccess(Object data) {
        return new JsonResult(0, data, null);
    }

    // 失败，传入描述信息
    public static JsonResult buildError(String msg) {
        return new JsonResult(-1, null, msg);
    }

    // 失败，传入描述信息,状态码
    public static JsonResult buildError(String msg, Integer code) {
        return new JsonResult(code, null, msg);
    }

    // 成功，传入数据,及描述信息
    public static JsonResult buildSuccess(Object data, String msg) {
        return new JsonResult(0, data, msg);
    }

    // 成功，传入数据,及状态码
    public static JsonResult buildSuccess(Object data, int code) {
        return new JsonResult(code, data, null);
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
