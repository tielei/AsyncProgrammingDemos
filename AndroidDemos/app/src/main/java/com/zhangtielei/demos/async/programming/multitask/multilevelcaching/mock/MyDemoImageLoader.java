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

package com.zhangtielei.demos.async.programming.multitask.multilevelcaching.mock;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.zhangtielei.demos.async.programming.callback.download.v3.DownloadListener;
import com.zhangtielei.demos.async.programming.callback.download.v3.Downloader;
import com.zhangtielei.demos.async.programming.callback.download.v3.MyDownloader;
import com.zhangtielei.demos.async.programming.common.AsyncCallback;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.ImageLoader;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.ImageLoaderListener;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.diskcache.ImageDiskCache;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.diskcache.mock.MockImageDiskCache;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.memcache.ImageMemCache;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.memcache.mock.MockImageMemCache;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tielei Zhang on 16/5/18.
 *
 * ImageLoader接口的一个实现, 用于演示.
 */
public class MyDemoImageLoader implements ImageLoader, DownloadListener {
    private ImageLoaderListener listener;
    private ImageMemCache imageMemCache = new MockImageMemCache();
    private ImageDiskCache imageDiskCache = new MockImageDiskCache();
    private Downloader downloader = new MyDownloader();
    private ExecutorService imageDecodingExecutor = Executors.newCachedThreadPool();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private Random random = new Random();

    public MyDemoImageLoader() {
        downloader.setListener(this);
    }

    @Override
    public void setListener(ImageLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    public void startImageLoad(final String url, final Object contextData) {
        //先检查一级缓存: memory cache
        Bitmap bitmap = imageMemCache.getImage(url);
        if (bitmap != null) {
            //memory cache命中, 加载任务提前结束.
            successCallback(url, bitmap, contextData);
            return;
        }

        //检查二级缓存: disk cache
        imageDiskCache.getImage(url, new AsyncCallback<Bitmap>() {
            @Override
            public void onResult(Bitmap bitmap) {
                if (bitmap != null) {
                    //disk cache命中, 加载任务提前结束.
                    imageMemCache.putImage(url, bitmap);
                    successCallback(url, bitmap, contextData);
                }
                else {
                    //两级缓存都没有命中, 调用下载器去下载
                    downloader.startDownload(url, getLocalPath(url), contextData);
                }
            }
        });
    }

    @Override
    public void downloadSuccess(final String url, final String localPath, final Object contextData) {
        //解码图片, 是个耗时操作, 异步来做
        imageDecodingExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = decodeBitmap(new File(localPath));
                //重新调度回主线程
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            imageMemCache.putImage(url, bitmap);
                            imageDiskCache.putImage(url, bitmap, null);
                            successCallback(url, bitmap, contextData);
                        }
                        else {
                            //解码失败
                            failureCallback(url, ImageLoaderListener.BITMAP_DECODE_FAILED, contextData);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void downloadFailed(String url, int errorCode, String errorMessage, Object contextData) {
        //错误码转换
        int imageLoadErrorCode;
        switch (errorCode) {
            case DownloadListener.NETWORK_UNAVAILABLE:
                imageLoadErrorCode = ImageLoaderListener.NETWORK_UNAVAILABLE;
                break;
            case DownloadListener.SDCARD_NOT_EXISTS:
                imageLoadErrorCode = ImageLoaderListener.SDCARD_NOT_EXISTS;
                break;
            case DownloadListener.SD_CARD_NO_SPACE_LEFT:
                imageLoadErrorCode = ImageLoaderListener.SD_CARD_NO_SPACE_LEFT;
                break;
            default:
                imageLoadErrorCode = ImageLoaderListener.DOWNLOAD_FAILED;
                break;
        }
        failureCallback(url, imageLoadErrorCode, contextData);
    }

    @Override
    public void downloadProgress(String url, long downloadedSize, long totalSize, Object contextData) {

    }


    private void successCallback(String url, Bitmap bitmap, Object contextData) {
        if (listener != null) {
            try {
                listener.imageLoadSuccess(url, bitmap, contextData);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void failureCallback(String url, int errorCode, Object contextData) {
        if (listener != null) {
            try {
                listener.imageLoadFailed(url, errorCode, getErrorMessage(errorCode), contextData);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 为每个errorCode对应一个error message
     * @param errorCode
     * @return
     */
    private String getErrorMessage(int errorCode) {
        //TODO:
        return "";
    }

    /**
     * 为每个url分配一个对应的本地存储地址
     * @param url
     * @return
     */
    private String getLocalPath(String url) {
        //TODO:
        return "";
    }

    /**
     * 从本地图片文件中解码出Bitmap.
     * @param file
     * @return 解码失败会返回null; 成功会返回bitmap对象
     */
    private Bitmap decodeBitmap(File file) {
        //模拟: 80%的概率解码成功
        if (random.nextInt(10) <= 7) {
            return Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
        }
        return null;
    }
}
