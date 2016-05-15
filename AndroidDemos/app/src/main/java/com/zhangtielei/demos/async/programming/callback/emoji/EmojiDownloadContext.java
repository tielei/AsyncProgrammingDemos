package com.zhangtielei.demos.async.programming.callback.emoji;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charleszhang on 16/5/1.
 * 表情包下载上下文
 */
public class EmojiDownloadContext {
    /**
     * 当前在下载的表情包
     */
    public EmojiPackage emojiPackage;
    /**
     * 已经下载完的表情图片计数
     */
    public int downloadedEmoji;
    /**
     * 下载到表情包本地地址
     */
    public List<String> localPathList = new ArrayList<String>();
}
