package com.wenchao.wenchaohttp;

/**
 * @author wenchao
 * @date 2019/7/3.
 * @time 14:08
 * description：
 */
public class WCHttp {

    public static <T, M> void sendJsonRequest(T requestData, String url, Class<M> response, IJsonDataTransforListener listener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallBackListener callBackListener = new JsonCallBackListener<>(response, listener);
        HttpTask httpTask = new HttpTask(url, requestData, httpRequest, callBackListener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
