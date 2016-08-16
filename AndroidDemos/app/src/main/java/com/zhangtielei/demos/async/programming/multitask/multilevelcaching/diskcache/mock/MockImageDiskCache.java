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

package com.zhangtielei.demos.async.programming.multitask.multilevelcaching.diskcache.mock;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.zhangtielei.demos.async.programming.common.AsyncCallback;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.diskcache.ImageDiskCache;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tielei Zhang on 16/5/17.
 */
public class MockImageDiskCache implements ImageDiskCache {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Random rand = new Random();

    @Override
    public void getImage(String key, final AsyncCallback<Bitmap> callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //模拟请求执行, 随机执行0~1秒
                try {
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一个下载成功回调
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (callback != null) {
                                //TODO: 这里写死返回null, 模拟Disk缓存没有命中的情形
                                callback.onResult(null);
                            }
                        }
                        catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void putImage(String key, Bitmap bitmap, AsyncCallback<Boolean> callback) {
        //TODO:
    }
}
