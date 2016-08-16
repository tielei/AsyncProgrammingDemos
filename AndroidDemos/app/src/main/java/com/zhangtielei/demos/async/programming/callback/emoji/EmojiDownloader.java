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
package com.zhangtielei.demos.async.programming.callback.emoji;

/**
 * Created by Tielei Zhang on 16/5/1.
 * 表情包下载器接口.
 */
public interface EmojiDownloader {
    /**
     * 开始下载指定的表情包
     * @param emojiPackage
     */
    void startDownloadEmoji(EmojiPackage emojiPackage);

    /**
     * 设置监听器.
     * @param listener
     */
    void setEmojiDownloadListener(EmojiDownloadListener listener);

    /**
     * 这里定义回调相关的接口. 不是我们要讨论的重点.
     * 这里的定义用于完整演示.
     */

    interface EmojiDownloadListener {
        /**
         * 表情包下载成功回调.
         * @param emojiPackage
         */
        void emojiDownloadSuccess(EmojiPackage emojiPackage);

        /**
         * 表情包下载失败回调.
         * @param emojiPackage
         * @param errorCode 为简单起见, 这里的值复用{@link com.zhangtielei.demos.async.programming.callback.download.v3.DownloadListener}中的错误码定义.
         * @param errorMessage
         */
        void emojiDownloadFailed(EmojiPackage emojiPackage, int errorCode, String errorMessage);

        /**
         * 表情包下载进度回调. 每次下载完一个图片文件后回调一次.
         * @param emojiPackage
         * @param downloadEmojiUrl 当前刚下载完的图片文件URL
         */
        void emojiDownloadProgress(EmojiPackage emojiPackage, String downloadEmojiUrl);
    }

}
