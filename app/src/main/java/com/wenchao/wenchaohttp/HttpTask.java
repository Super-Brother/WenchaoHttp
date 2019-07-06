package com.wenchao.wenchaohttp;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao
 * @date 2019/7/3.
 * @time 13:27
 * description：
 */
public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequest httpRequest;

    public HttpTask(String url, T requestData, IHttpRequest httpRequest, CallBackListener listener) {
        this.httpRequest = httpRequest;

        httpRequest.setUrl(url);
        final String content = JSON.toJSONString(requestData);
        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequest.setListener(listener);
    }

    @Override
    public void run() {
        try {
            this.httpRequest.execute();
        } catch (Exception e) {
            ThreadPoolManager.getInstance().addDelayTask(this);
        }
    }

    private long delayTime;
    private int retryCount;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
