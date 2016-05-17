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
package com.zhangtielei.demos.async.programming.callback.download.v3;

/**
 * Created by Tielei Zhang on 16/5/2.
 * 下载器接口定义(可以传递上下文参数).
 */
public interface Downloader {
    /**
     * 设置回调监听器.
     * @param listener
     */
    void setListener(DownloadListener listener);
    /**
     * 启动资源的下载.
     * @param url 要下载的资源地址.
     * @param localPath 资源下载后要存储的本地位置.
     * @param contextData 上下文数据, 在回调接口中会透传回去.可以是任何类型.
     */
    void startDownload(String url, String localPath, Object contextData);
}
