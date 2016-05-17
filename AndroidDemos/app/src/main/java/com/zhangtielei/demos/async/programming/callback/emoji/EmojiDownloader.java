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
     * 这里回调相关的接口, 忽略. 不是我们要讨论的重点.
     */
    //TODO: 回调接口相关定义
}
