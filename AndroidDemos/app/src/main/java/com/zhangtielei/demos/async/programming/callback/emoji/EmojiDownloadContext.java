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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tielei Zhang on 16/5/1.
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
