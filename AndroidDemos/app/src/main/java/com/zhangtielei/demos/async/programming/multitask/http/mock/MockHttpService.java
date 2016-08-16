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

package com.zhangtielei.demos.async.programming.multitask.http.mock;

import android.os.Handler;
import android.os.Looper;
import com.zhangtielei.demos.async.programming.multitask.http.HttpListener;
import com.zhangtielei.demos.async.programming.multitask.http.HttpResult;
import com.zhangtielei.demos.async.programming.multitask.http.HttpService;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.model.HttpRequest;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.model.HttpResponse;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpRequest1;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpRequest2;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpResponse1;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpResponse2;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tielei Zhang on 16/5/17.
 *
 * HttpService的一个假的实现.
 */
public class MockHttpService implements HttpService {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Random rand = new Random();

    @Override
    public <T, R> void doRequest(final String apiUrl, final T request, final HttpListener<? super T, R> listener, final Object contextData) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //模拟请求执行, 随机执行0~3秒
                try {
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一个返回结果
                Object httpResponse = generateResponse(request);
                final HttpResult<R> result = new HttpResult<R>();
                result.setErrorCode(HttpResult.SUCCESS);
                result.setResponse((R) httpResponse);

                //回调
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            try {
                                listener.onResult(apiUrl, request, result, contextData);
                            }
                            catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    private Object generateResponse(Object httpRequest) {
        //hard code
        if (httpRequest instanceof HttpRequest1) {
            HttpResponse1 response1 = new HttpResponse1();
            response1.setText("Data from HttpRequest1. timestamp: " + new Date());
            return response1;
        }
        else if (httpRequest instanceof HttpRequest2) {
            HttpResponse2 response2 = new HttpResponse2();
            response2.setText("Data from HttpRequest2. timestamp: " + new Date());
            return response2;
        }
        else if (httpRequest instanceof HttpRequest) {
            HttpResponse response = new HttpResponse();
            return response;
        }
        return null;
    }
}
