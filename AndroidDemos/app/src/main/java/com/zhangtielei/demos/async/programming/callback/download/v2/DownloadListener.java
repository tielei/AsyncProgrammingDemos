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

/**
 * Created by Tielei Zhang on 4/17/16.
 * 下载器回调接口定义(带有详细的错误码定义).
 */
public interface DownloadListener {
    /**
     * 错误码定义
     */
    public static final int SUCCESS = 0;//成功
    public static final int INVALID_PARAMS = 1;//输入参数有误
    public static final int NETWORK_UNAVAILABLE = 2;//网络不可用
    public static final int UNKNOWN_HOST = 3;//域名解析失败
    public static final int CONNECT_TIMEOUT = 4;//连接超时
    public static final int HTTP_STATUS_NOT_OK = 5;//下载请求返回非200
    public static final int SDCARD_NOT_EXISTS = 6;//SD卡不存在(下载的资源没地方存)
    public static final int SD_CARD_NO_SPACE_LEFT = 7;//SD卡空间不足(下载的资源没地方存)
    public static final int READ_ONLY_FILE_SYSTEM = 8;//文件系统只读(下载的资源没地方存)
    public static final int LOCAL_IO_ERROR = 9;//本地SD存取有关的错误
    public static final int UNKNOWN_FAILED = 10;//其它未知错误

    /**
     * 下载成功回调.
     * @param url 资源地址
     * @param localPath 下载后的资源存储位置.
     */
    void downloadSuccess(String url, String localPath);
    /**
     * 下载失败回调.
     * @param url 资源地址
     * @param errorCode 错误码.
     * @param errorMessage 错误信息简短描述. 供调用者理解错误原因.
     */
    void downloadFailed(String url, int errorCode, String errorMessage);

    /**
     * 下载进度回调.
     * @param url 资源地址
     * @param downloadedSize 已下载大小.
     * @param totalSize 资源总大小.
     */
    void downloadProgress(String url, long downloadedSize, long totalSize);
}
