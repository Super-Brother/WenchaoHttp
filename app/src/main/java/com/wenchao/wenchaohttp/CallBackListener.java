package com.wenchao.wenchaohttp;

import java.io.InputStream;

/**
 * @author wenchao
 * @date 2019/7/2.
 * @time 22:57
 * description：
 */
public interface CallBackListener {

    void onSuccess(InputStream inputStream);

    void onFailure();
}
