package cn.leslie.weChatLearn.excepton;

/**
 * 自定义异常类
 */
public class WeChatLearnException extends RuntimeException{

    /**
     * 异常信息
     */
    private String msg;
    /**
     * 状态码
     */
    private Integer code;

    public WeChatLearnException(){
        super();
    }

    public WeChatLearnException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
