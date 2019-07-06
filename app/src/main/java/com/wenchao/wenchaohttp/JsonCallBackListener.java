package com.wenchao.wenchaohttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author wenchao
 * @date 2019/7/3.
 * @time 13:15
 * description：
 */
public class JsonCallBackListener<T> implements CallBackListener {

    private Class<T> responseClass;
    private IJsonDataTransforListener listener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonCallBackListener(Class<T> responseClass, IJsonDataTransforListener listener) {
        this.responseClass = responseClass;
        this.listener = listener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //将流转换成String
        String response = getContent(inputStream);
        //将string转换成对应的class对象
        final T toClass = JSON.parseObject(response, responseClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(toClass);
            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error=" + e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Error=" + e.toString());
                }
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void onFailure() {
        Log.e("===>","请求失败");
    }
}
