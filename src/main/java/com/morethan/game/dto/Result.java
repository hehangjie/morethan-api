package com.morethan.game.dto;

/**
 * <pre>
 *
 * 自定义响应结构
 * </pre>
 *
 * @author Aron
 * @date 2017年5月9日
 */
public class Result<T> {
    public final static String HTTPCODE = "200";
    public final static Boolean SUCCESS = true;
    public final static Boolean FAIL = false;
    public final static String MSG_SUCCESS = "操作成功";
    public final static String MSG_FAIL = "操作失败";

    //http响应业务状态碼
    private String status;

    // 响应业务状态 0 成功， 1失败
    private Boolean success;

    // 响应消息
    private String msg;

    // 响应中的数据
    private T result;

    public Result(String status,Boolean success, String msg, T result) {
        this.status = status;
        this.success = success;
        this.msg = msg;
        this.result = result;
    }

    public Result(Boolean success, String msg, T result) {
        this.status = HTTPCODE;
        this.success = success;
        this.msg = msg;
        this.result = result;
    }

    public Result(T result) {
        this.status = HTTPCODE;
        this.success = SUCCESS;
        this.msg = MSG_SUCCESS;
        this.result = result;
    }

    public static <T> Result<T> build(Boolean success, String msg, T result) {
        return new Result<T>(success, msg, result);
    }

    public static <T> Result<T> ok(T result) {
        return new Result<T>(result);
    }

    public static <T> Result<T> ok() {
        return new Result<T>(SUCCESS, MSG_SUCCESS, null);
    }

    public static <T> Result<T> fail() {
        return new Result<T>("405",FAIL, MSG_FAIL, null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<T>("405",FAIL, msg, null);
    }

    public static <T> Result<T> build(Boolean success, String msg) {
        return new Result<T>(success, msg, null);
    }

    public static <T> Result<T> getResult(T t) {
        Result<T> result = new Result<>(t);
        return result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
