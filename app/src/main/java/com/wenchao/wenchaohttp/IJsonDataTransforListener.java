package com.wenchao.wenchaohttp;

/**
 * @author wenchao
 * @date 2019/7/3.
 * @time 14:02
 * description：
 */
public interface IJsonDataTransforListener<T> {

    void onSuccess(T t);
}
