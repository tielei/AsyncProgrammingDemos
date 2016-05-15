package com.zhangtielei.demos.async.programming.callback.download.v3_simplified;


/**
 * Created by charleszhang on 16/5/11.
 * 下载器接口定义(可以传递上下文参数).
 */
public interface Downloader {
    /**
     * 启动资源的下载.
     * @param url 要下载的资源地址.
     * @param localPath 资源下载后要存储的本地位置.
     * @param contextData 上下文数据, 在回调接口中会透传回去.可以是任何类型.
     * @param listener 回调接口实例.
     */
    void startDownload(String url, String localPath, Object contextData, DownloadListener listener);
}
