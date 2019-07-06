package com.wenchao.wenchaohttp;

/**
 * @author wenchao
 * @date 2019/7/2.
 * @time 22:53
 * description：
 */
public interface IHttpRequest {

    void setUrl(String url);

    void setData(byte[] data);

    void setListener(CallBackListener listener);

    void execute();
}
