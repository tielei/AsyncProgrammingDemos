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
package com.zhangtielei.demos.async.programming.callback.emoji.v3;

import com.zhangtielei.demos.async.programming.callback.download.v2.DownloadListener;
import com.zhangtielei.demos.async.programming.callback.download.v2.MyDownloader;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiDownloadContext;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiDownloader;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiPackage;

import java.util.List;

/**
 * Created by charleszhang on 16/5/1.
 * EmojiDownloader的第3个版本的实现: 为每一个异步任务创建一个接口实例.
 */
public class MyEmojiDownloader implements EmojiDownloader {
    @Override
    public void startDownloadEmoji(EmojiPackage emojiPackage) {
        //创建下载上下文数据
        EmojiDownloadContext downloadContext = new EmojiDownloadContext();
        downloadContext.emojiPackage = emojiPackage;
        //为每一次下载创建一个新的Downloader
        final EmojiUrlDownloader downloader = new EmojiUrlDownloader();
        //将上下文数据存到downloader实例中
        downloader.downloadContext = downloadContext;

        downloader.setListener(new DownloadListener() {
            @Override
            public void downloadSuccess(String url, String localPath) {
                EmojiDownloadContext downloadContext = downloader.downloadContext;
                downloadContext.localPathList.add(localPath);
                downloadContext.downloadedEmoji++;
                EmojiPackage emojiPackage = downloadContext.emojiPackage;
                if (downloadContext.downloadedEmoji < emojiPackage.emojiUrls.size()) {
                    //还没下载完, 继续下载下一个表情图片
                    String nextUrl = emojiPackage.emojiUrls.get(downloadContext.downloadedEmoji);
                    downloader.startDownload(nextUrl,
                            getLocalPathForEmoji(emojiPackage, downloadContext.downloadedEmoji));
                }
                else {
                    //已经下载完
                    installEmojiPackageLocally(emojiPackage, downloadContext.localPathList);
                }
            }

            @Override
            public void downloadFailed(String url, int errorCode, String errorMessage) {
                //TODO:
            }

            @Override
            public void downloadProgress(String url, long downloadedSize, long totalSize) {
                //TODO:
            }
        });

        //启动第0个表情图片文件的下载
        downloader.startDownload(emojiPackage.emojiUrls.get(0),
                getLocalPathForEmoji(emojiPackage, 0));
    }

    private static class EmojiUrlDownloader extends MyDownloader {
        public EmojiDownloadContext downloadContext;
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
