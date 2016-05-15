package com.zhangtielei.demos.async.programming.callback.download.v1;

/**
 * Created by charleszhang on 16/5/2.
 * 下载器回调接口定义(没有详细的错误码定义).
 */
public interface DownloadListener {
    /**
     * 下载结束回调.
     * @param result 下载结果. true表示下载成功, false表示下载失败.
     * @param url 资源地址
     * @param localPath 下载后的资源存储位置. 只有result=true时才有效.
     */
    void downloadFinished(boolean result, String url, String localPath);

    /**
     * 下载进度回调.
     * @param url 资源地址
     * @param downloadedSize 已下载大小.
     * @param totalSize 资源总大小.
     */
    void downloadProgress(String url, long downloadedSize, long totalSize);
}