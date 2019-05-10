package com.morethan.game.utils;

import java.io.Serializable;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2018-11-15 下午1:46
 */
public class HttpClientResult implements Serializable {

    private static final long serialVersionUID = -3900988408509316585L;

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult() {

    }

    public HttpClientResult(int code) {
        this.code = code;
    }

    public HttpClientResult(int code,String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

