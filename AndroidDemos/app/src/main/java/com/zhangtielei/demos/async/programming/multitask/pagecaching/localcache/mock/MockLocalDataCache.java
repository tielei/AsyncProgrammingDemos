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

package com.zhangtielei.demos.async.programming.multitask.pagecaching.localcache.mock;

import android.os.Handler;
import android.os.Looper;
import com.zhangtielei.demos.async.programming.common.AsyncCallback;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.localcache.LocalDataCache;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.model.HttpResponse;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tielei Zhang on 16/5/17.
 * 本地页面Cache的一个假的实现.
 */
public class MockLocalDataCache implements LocalDataCache {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Random rand = new Random();

    @Override
    public void getCachingData(String key, final AsyncCallback<HttpResponse> callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //模拟请求执行, 随机执行0~1秒
                try {
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //回调
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            try {
                                //模拟命中还是不命中的两种情况: 以80%概率命中
                                HttpResponse httpResponse = (rand.nextInt(10) <= 7) ? new HttpResponse() : null;
                                callback.onResult(httpResponse);
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

    @Override
    public void putCachingData(String key, HttpResponse data, AsyncCallback<Boolean> callback) {
        //TODO:
    }
}
