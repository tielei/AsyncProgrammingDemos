/*
 * Copyright (C) 2016 Tielei Zhang (zhangtielei.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhangtielei.demos.async.programming.multitask.simultaneousrequests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.common.utils.TextLogUtil;
import com.zhangtielei.demos.async.programming.multitask.http.HttpListener;
import com.zhangtielei.demos.async.programming.multitask.http.HttpResult;
import com.zhangtielei.demos.async.programming.multitask.http.HttpService;
import com.zhangtielei.demos.async.programming.multitask.http.mock.MockHttpService;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpRequest1;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpRequest2;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpResponse1;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpResponse2;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示两个请求同时发生的异步处理过程.
 */
public class MultiRequestsDemoActivity extends AppCompatActivity {
    private TextView description;
    private TextView logTextView;

    private HttpService httpService = new MockHttpService();
    /**
     * 缓存各个请求结果的Map
     */
    private Map<String, Object> httpResults = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        description = (TextView) findViewById(R.id.description);
        logTextView = (TextView) findViewById(R.id.log_display);

        description.setText(R.string.multireques_demo_description);

        //同时发起两个异步请求
        TextLogUtil.println(logTextView, "Start HttpRequest1...");
        httpService.doRequest("http://...", new HttpRequest1(),
                new HttpListener<HttpRequest1, HttpResponse1>() {
                    @Override
                    public void onResult(String apiUrl,
                                         HttpRequest1 request,
                                         HttpResult<HttpResponse1> result,
                                         Object contextData) {
                        TextLogUtil.println(logTextView, "Rx HttpResponse1. Success? " + (result.getErrorCode() == HttpResult.SUCCESS));
                        //将请求结果缓存下来
                        httpResults.put("request-1", result);
                        if (checkAllHttpResultsReady()) {
                            //两个请求都已经结束
                            HttpResult<HttpResponse1> result1 = result;
                            HttpResult<HttpResponse2> result2 = (HttpResult<HttpResponse2>) httpResults.get("request-2");
                            if (checkAllHttpResultsSuccess()) {
                                //两个请求都成功了
                                processData(result1.getResponse(), result2.getResponse());
                            }
                            else {
                                //两个请求并未完全成功, 按失败处理
                                processError(result1.getErrorCode(), result2.getErrorCode());
                            }
                        }
                    }
                },
                null);
        TextLogUtil.println(logTextView, "Start HttpRequest2...");
        httpService.doRequest("http://...", new HttpRequest2(),
                new HttpListener<HttpRequest2, HttpResponse2>() {
                    @Override
                    public void onResult(String apiUrl,
                                         HttpRequest2 request,
                                         HttpResult<HttpResponse2> result,
                                         Object contextData) {
                        TextLogUtil.println(logTextView, "Rx HttpResponse2. Success? " + (result.getErrorCode() == HttpResult.SUCCESS));
                        //将请求结果缓存下来
                        httpResults.put("request-2", result);
                        if (checkAllHttpResultsReady()) {
                            //两个请求都已经结束
                            HttpResult<HttpResponse1> result1 = (HttpResult<HttpResponse1>) httpResults.get("request-1");
                            HttpResult<HttpResponse2> result2 = result;
                            if (checkAllHttpResultsSuccess()) {
                                //两个请求都成功了
                                processData(result1.getResponse(), result2.getResponse());
                            }
                            else {
                                //两个请求并未完全成功, 按失败处理
                                processError(result1.getErrorCode(), result2.getErrorCode());
                            }
                        }
                    }
                },
                null);
    }

    /**
     * 检查是否所有请求都有结果了
     * @return
     */
    private boolean checkAllHttpResultsReady() {
        int requestsCount = 2;
        for (int i = 1; i <= requestsCount; i++) {
            if (httpResults.get("request-" + i) == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查是否所有请求都成功了
     * @return
     */
    private boolean checkAllHttpResultsSuccess() {
        int requestsCount = 2;
        for (int i = 1; i <= requestsCount; i++) {
            HttpResult<?> result = (HttpResult<?>) httpResults.get("request-" + i);
            if (result == null || result.getErrorCode() != HttpResult.SUCCESS) {
                return false;
            }
        }
        return true;
    }

    private void processData(HttpResponse1 data1, HttpResponse2 data2) {
        TextLogUtil.println(logTextView, "Both data ready. HttpResponse1: " + data1.getText() + ", HttpResponse2: " + data2.getText());
    }

    private void processError(int errorCode1, int errorCode2) {
        TextLogUtil.println(logTextView, "Requests failed! errorCode1: " + errorCode1 + ", errorCode2: " + errorCode2);
    }
}
