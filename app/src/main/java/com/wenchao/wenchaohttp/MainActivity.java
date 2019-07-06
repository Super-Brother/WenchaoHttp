package com.wenchao.wenchaohttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

//    private String url = "https://api.apiopen.top/recommendPoetry";
    private String url = "https://api.apiopen.to/recommend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WCHttp.sendJsonRequest(null, url, TestResponse.class, new IJsonDataTransforListener<TestResponse>() {
            @Override
            public void onSuccess(TestResponse o) {
                Log.e("===>", o.toString());
            }
        });
    }
}
