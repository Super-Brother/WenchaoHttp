package com.wenchao.wenchaohttp;

import java.util.List;

/**
 * @author wenchao
 * @date 2019/7/3.
 * @time 14:22
 * description：
 */
public class TestResponse {


    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TestResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
