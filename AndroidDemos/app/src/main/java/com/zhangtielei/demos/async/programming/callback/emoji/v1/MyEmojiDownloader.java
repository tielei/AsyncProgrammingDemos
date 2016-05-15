package com.zhangtielei.demos.async.programming.callback.emoji.v1;

import com.zhangtielei.demos.async.programming.callback.download.v2.DownloadListener;
import com.zhangtielei.demos.async.programming.callback.download.v2.Downloader;
import com.zhangtielei.demos.async.programming.callback.download.v2.MyDownloader;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiDownloadContext;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiDownloader;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiPackage;

import java.util.List;

/**
 * Created by charleszhang on 16/5/1.
 * EmojiDownloader的第1个版本的实现: 使用全局一份上下文结构.
 */
public class MyEmojiDownloader implements EmojiDownloader, DownloadListener {
    /**
     * 全局保存一份的表情包下载上下文.
     */
    private EmojiDownloadContext downloadContext;
    private Downloader downloader;

    public MyEmojiDownloader() {
        //实例化有一个下载器.
        downloader = new MyDownloader();
        downloader.setListener(this);
    }

    @Override
    public void startDownloadEmoji(EmojiPackage emojiPackage) {
        if (downloadContext == null) {
            //创建下载上下文数据
            downloadContext = new EmojiDownloadContext();
            downloadContext.emojiPackage = emojiPackage;
            //启动第0个表情图片文件的下载
            downloader.startDownload(emojiPackage.emojiUrls.get(0),
                    getLocalPathForEmoji(emojiPackage, 0));
        }
    }

    @Override
    public void downloadSuccess(String url, String localPath) {
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
            downloadContext = null;
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
