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
import android.view.Menu;
import android.view.MenuItem;
import com.zhangtielei.demos.async.programming.R;
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
 * 展示两个请求同时发生的异步处理过程.
 */
public class MultiRequestsDemoActivity extends AppCompatActivity {
    private HttpService httpService = new MockHttpService();
    /**
     * 缓存各个请求结果的Map
     */
    private Map<String, Object> httpResults = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_requests_demo);

        //同时发起两个异步请求
        httpService.doRequest("http://...", new HttpRequest1(),
                new HttpListener<HttpRequest1, HttpResponse1>() {
                    @Override
                    public void doResult(String apiUrl,
                                         HttpRequest1 request,
                                         HttpResult<HttpResponse1> result,
                                         Object contextData) {
                        //将请求结果缓存下来
                        httpResults.put("request-1", result);
                        if (checkAllHttpResultsReady()) {
                            HttpResult<HttpResponse1> result1 = result;
                            HttpResult<HttpResponse2> result2 = (HttpResult<HttpResponse2>) httpResults.get("request-2");
                            if (checkAllHttpResultsSuccess()) {
                                processHttpResponses(result1.getResponse(), result2.getResponse());
                            }
                            else {
                                processHttpErrorResults(result1, result2);
                            }
                        }
                    }
                },
                null);
        httpService.doRequest("http://...", new HttpRequest2(),
                new HttpListener<HttpRequest2, HttpResponse2>() {
                    @Override
                    public void doResult(String apiUrl,
                                         HttpRequest2 request,
                                         HttpResult<HttpResponse2> result,
                                         Object contextData) {
                        //将请求结果缓存下来
                        httpResults.put("request-2", result);
                        if (checkAllHttpResultsReady()) {
                            HttpResult<HttpResponse1> result1 = (HttpResult<HttpResponse1>) httpResults.get("request-1");
                            HttpResult<HttpResponse2> result2 = result;
                            if (checkAllHttpResultsSuccess()) {
                                processHttpResponses(result1.getResponse(), result2.getResponse());
                            }
                            else {
                                processHttpErrorResults(result1, result2);
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

    private void processHttpResponses(HttpResponse1 response1, HttpResponse2 response2) {
        //TODO: 更新UI, 展示请求结果. 省略此处代码
    }

    private void processHttpErrorResults(HttpResult<HttpResponse1> result1, HttpResult<HttpResponse2> result2) {
        //TODO: 更新UI,展示错误. 省略此处代码
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_requests_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
