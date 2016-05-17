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
package com.zhangtielei.demos.async.programming.callback.download.v3_simplified;

/**
 * Created by Tielei Zhang on 16/5/11.
 * 演示多层嵌套导致的callback hell
 */
public class CallbackHellExample {
    /**
     * 连续下载3个文件
     * @param url1
     * @param url2
     * @param url3
     */
    public void downloadThreeFiles(final String url1, final String url2, final String url3) {
        final Downloader downloader = new MyDownloader();
        downloader.startDownload(url1, localPathForUrl(url1), null, new DownloadListener() {
            @Override
            public void downloadFinished(int errorCode, String url, String localPath, Object contextData) {
                if (errorCode != DownloadListener.SUCCESS) {
                    //...错误处理
                }
                else {
                    //下载第二个URL
                    downloader.startDownload(url2, localPathForUrl(url2), null, new DownloadListener() {
                        @Override
                        public void downloadFinished(int errorCode, String url, String localPath, Object contextData) {
                            if (errorCode != DownloadListener.SUCCESS) {
                                //...错误处理
                            }
                            else {
                                //下载第三个URL
                                downloader.startDownload(url3, localPathForUrl(url3), null, new DownloadListener(

                                ) {
                                    @Override
                                    public void downloadFinished(int errorCode, String url, String localPath, Object contextData) {
                                        //...最终结果处理
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private String localPathForUrl(String url) {
        //TODO:
        return null;
    }

}
