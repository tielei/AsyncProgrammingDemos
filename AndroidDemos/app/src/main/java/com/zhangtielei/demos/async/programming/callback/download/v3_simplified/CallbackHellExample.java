package com.zhangtielei.demos.async.programming.callback.download.v3_simplified;

/**
 * Created by charleszhang on 16/5/11.
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
