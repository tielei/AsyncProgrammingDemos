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
package com.zhangtielei.demos.async.programming.callback.emoji.v4;


import com.zhangtielei.demos.async.programming.callback.download.v3.DownloadListener;
import com.zhangtielei.demos.async.programming.callback.download.v3.Downloader;
import com.zhangtielei.demos.async.programming.callback.download.v3.MyDownloader;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiDownloadContext;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiDownloader;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiPackage;

import java.util.List;

/**
 * Created by Tielei Zhang on 16/5/2.
 * EmojiDownloader的第4个版本的实现: 利用支持上下文传递的异步接口.
 */
public class MyEmojiDownloader implements EmojiDownloader, DownloadListener {
    private Downloader downloader;
    private EmojiDownloadListener listener;

    public MyEmojiDownloader() {
        //实例化有一个下载器.
        downloader = new MyDownloader();
        downloader.setListener(this);
    }

    @Override
    public void startDownloadEmoji(EmojiPackage emojiPackage) {
        //创建下载上下文数据
        EmojiDownloadContext downloadContext = new EmojiDownloadContext();
        downloadContext.emojiPackage = emojiPackage;
        //启动第0个表情图片文件的下载, 上下文参数传递进去
        downloader.startDownload(emojiPackage.emojiUrls.get(0),
                getLocalPathForEmoji(emojiPackage, 0),
                downloadContext);

    }

    @Override
    public void setEmojiDownloadListener(EmojiDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void downloadSuccess(String url, String localPath, Object contextData) {
        //通过回调接口的contextData参数做Down-casting获得上下文参数
        EmojiDownloadContext downloadContext = (EmojiDownloadContext) contextData;

        downloadContext.localPathList.add(localPath);
        downloadContext.downloadedEmoji++;
        EmojiPackage emojiPackage = downloadContext.emojiPackage;
        if (downloadContext.downloadedEmoji < emojiPackage.emojiUrls.size()) {
            //还没下载完, 产生一个进度回调
            try {
                if (listener != null) {
                    listener.emojiDownloadProgress(emojiPackage, url);
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
            //继续下载下一个表情图片
            String nextUrl = emojiPackage.emojiUrls.get(downloadContext.downloadedEmoji);
            downloader.startDownload(nextUrl,
                    getLocalPathForEmoji(emojiPackage, downloadContext.downloadedEmoji),
                    downloadContext);
        }
        else {
            //已经下载完
            installEmojiPackageLocally(emojiPackage, downloadContext.localPathList);

            //成功回调
            try {
                if (listener != null) {
                    listener.emojiDownloadProgress(emojiPackage, url);//最后一次进度回调.
                    listener.emojiDownloadSuccess(emojiPackage);
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void downloadFailed(String url, int errorCode, String errorMessage, Object contextData) {
        //通过回调接口的contextData参数做Down-casting获得上下文参数
        EmojiDownloadContext downloadContext = (EmojiDownloadContext) contextData;
        EmojiPackage emojiPackage = downloadContext.emojiPackage;

        //失败回调
        try {
            if (listener != null) {
                listener.emojiDownloadFailed(emojiPackage, errorCode, errorMessage);
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadProgress(String url, long downloadedSize, long totalSize, Object contextData) {
        //TODO:
    }

    /**
     * 计算表情包中第i个表情图片文件的下载地址.
     * @param emojiPackage
     * @param i
     * @return
     */
    private String getLocalPathForEmoji(EmojiPackage emojiPackage, int i) {
        //TODO:
        return null;
    }

    /**
     * 把表情包安装到本地
     * @param emojiPackage
     * @param localPathList
     */
    private void installEmojiPackageLocally(EmojiPackage emojiPackage, List<String> localPathList) {
        //TODO:
        return;
    }
}
