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

package com.zhangtielei.demos.async.programming.callback.emoji.v3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiDownloader;
import com.zhangtielei.demos.async.programming.callback.emoji.EmojiPackage;
import com.zhangtielei.demos.async.programming.common.utils.TextLogUtil;

import java.util.ArrayList;

/**
 * 通过为每一个异步任务创建一个接口实例的方式来传递上下文的表情包下载演示页面.
 */
public class EmojiDownloadDemoActivity extends AppCompatActivity {
    private TextView description;
    private TextView logTextView;

    private EmojiDownloader emojiDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        description = (TextView) findViewById(R.id.description);
        logTextView = (TextView) findViewById(R.id.log_display);

        description.setText(R.string.emoji_download_demo3_description);

        emojiDownloader = new MyEmojiDownloader();
        emojiDownloader.setEmojiDownloadListener(new EmojiDownloader.EmojiDownloadListener() {
            @Override
            public void emojiDownloadSuccess(EmojiPackage emojiPackage) {
                TextLogUtil.println(logTextView, "emoji package (emojiId: " + emojiPackage.emojiId + ") downloaded success!");
            }

            @Override
            public void emojiDownloadFailed(EmojiPackage emojiPackage, int errorCode, String errorMessage) {
                TextLogUtil.println(logTextView, "emoji package (emojiId: " + emojiPackage.emojiId + ") downloaded failed! errorCode: " + errorCode + ", errorMessage: " + errorMessage);
            }

            @Override
            public void emojiDownloadProgress(EmojiPackage emojiPackage, String downloadEmojiUrl) {
                TextLogUtil.println(logTextView, "progress: " + downloadEmojiUrl + " downloaded...");
            }
        });

        //构造要下载的表情包
        EmojiPackage emojiPackage = new EmojiPackage();
        emojiPackage.emojiId = 1003;
        emojiPackage.emojiUrls = new ArrayList<String>();
        emojiPackage.emojiUrls.add("http://zhangtielei.com/demourls/1.png");
        emojiPackage.emojiUrls.add("http://zhangtielei.com/demourls/2.png");
        emojiPackage.emojiUrls.add("http://zhangtielei.com/demourls/3.png");
        emojiPackage.emojiUrls.add("http://zhangtielei.com/demourls/4.png");
        emojiPackage.emojiUrls.add("http://zhangtielei.com/demourls/5.png");
        emojiPackage.emojiUrls.add("http://zhangtielei.com/demourls/6.png");
        emojiPackage.emojiUrls.add("http://zhangtielei.com/demourls/7.png");
        emojiPackage.emojiUrls.add("http://zhangtielei.com/demourls/8.png");

        TextLogUtil.println(logTextView, "Start downloading emoji package (emojiId: " + emojiPackage.emojiId + ") with " + emojiPackage.emojiUrls.size() + " URLs");
        emojiDownloader.startDownloadEmoji(emojiPackage);

    }

}
