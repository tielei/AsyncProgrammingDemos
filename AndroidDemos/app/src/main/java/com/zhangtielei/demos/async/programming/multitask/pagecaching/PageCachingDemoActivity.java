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

package com.zhangtielei.demos.async.programming.multitask.pagecaching;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.common.AsyncCallback;
import com.zhangtielei.demos.async.programming.common.utils.TextLogUtil;
import com.zhangtielei.demos.async.programming.multitask.http.HttpListener;
import com.zhangtielei.demos.async.programming.multitask.http.HttpResult;
import com.zhangtielei.demos.async.programming.multitask.http.HttpService;
import com.zhangtielei.demos.async.programming.multitask.http.mock.MockHttpService;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.localcache.LocalDataCache;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.localcache.mock.MockLocalDataCache;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.model.HttpRequest;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.model.HttpResponse;

/**
 * 演示Page Caching的异步处理过程.
 *
 * Page Caching策略: 页面打开后先展示本地缓存数据, 然后API请求返回数据后, 再重新刷新页面.
 */
public class PageCachingDemoActivity extends AppCompatActivity {
    private TextView description;
    private TextView logTextView;

    private HttpService httpService = new MockHttpService();
    private LocalDataCache localDataCache = new MockLocalDataCache();
    /**
     * 从Http请求到的数据是否已经返回
     */
    private boolean dataFromHttpReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        description = (TextView) findViewById(R.id.description);
        logTextView = (TextView) findViewById(R.id.log_display);

        description.setText(R.string.pagecaching_demo_description);

        //同时发起本地数据请求和远程Http请求
        final String userId = "xxx";
        TextLogUtil.println(logTextView, "Start requesting local data cache...");
        localDataCache.getCachingData(userId, new AsyncCallback<HttpResponse>() {
            @Override
            public void onResult(HttpResponse data) {
                TextLogUtil.println(logTextView, "Data from local data cache: " + data);

                if (data != null && !dataFromHttpReady) {
                    //缓存有旧数据 & 远程Http请求还没返回,先显示旧数据
                    processData(data);
                }
                else if (data != null && dataFromHttpReady) {
                    //只是打印一行日志
                    TextLogUtil.println(logTextView, "Data from local data cache is ignored.");
                }
            }
        });
        TextLogUtil.println(logTextView, "Start requesting HTTP...");
        httpService.doRequest("http://...", new HttpRequest(),
                new HttpListener<HttpRequest, HttpResponse>() {
                    @Override
                    public void onResult(String apiUrl,
                                         HttpRequest request,
                                         HttpResult<HttpResponse> result,
                                         Object contextData) {
                        TextLogUtil.println(logTextView, "Data from HTTP: " + result.getResponse());
                        if (result.getErrorCode() == HttpResult.SUCCESS) {
                            dataFromHttpReady = true;
                            processData(result.getResponse());
                            //从Http拉到最新数据, 更新本地缓存
                            localDataCache.putCachingData(userId, result.getResponse(), null);
                        }
                        else {
                            processError(result.getErrorCode());
                        }
                    }
                },
                null);
    }


    private void processData(HttpResponse data) {
        TextLogUtil.println(logTextView, "Got data success: " + data);
    }

    private void processError(int errorCode) {
        TextLogUtil.println(logTextView, "Got data failed. errorCode: " + errorCode);
    }
}
