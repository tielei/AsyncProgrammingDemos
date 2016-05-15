package com.zhangtielei.demos.async.programming.callback.emoji;

/**
 * Created by charleszhang on 16/5/1.
 * 表情包下载器接口.
 */
public interface EmojiDownloader {
    /**
     * 开始下载指定的表情包
     * @param emojiPackage
     */
    void startDownloadEmoji(EmojiPackage emojiPackage);

    /**
     * 这里回调相关的接口, 忽略. 不是我们要讨论的重点.
     */
    //TODO: 回调接口相关定义
}
