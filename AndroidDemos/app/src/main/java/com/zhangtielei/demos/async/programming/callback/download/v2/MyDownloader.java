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
package com.zhangtielei.demos.async.programming.callback.download.v2;

import android.os.Handler;
import android.os.Looper;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tielei Zhang on 16/5/1.
 * 下载器的实现(stub).
 */
public class MyDownloader implements Downloader {
    private DownloadListener listener;

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Random rand = new Random();

    @Override
    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void startDownload(final String url, final String localPath) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //模拟请求执行, 随机执行0~3秒
                try {
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一个下载成功回调
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (listener != null) {
                                listener.downloadSuccess(url, localPath);
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
}
