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

package com.zhangtielei.demos.async.programming.multitask.multilevelcaching;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.common.utils.TextLogUtil;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.mock.MyDemoImageLoader;

/**
 * 一个演示页面: 调用一个带多级缓存的图片下载器, 演示"多个异步任务先后接续执行".
 */
public class ImageLoaderDemoActivity extends AppCompatActivity {
    private TextView description;
    private TextView logTextView;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        description = (TextView) findViewById(R.id.description);
        logTextView = (TextView) findViewById(R.id.log_display);

        description.setText(R.string.imageloader_demo_description);

        imageLoader = new MyDemoImageLoader();
        imageLoader.setListener(new ImageLoaderListener() {
            @Override
            public void imageLoadSuccess(String url, Bitmap bitmap, Object contextData) {
                TextLogUtil.println(logTextView, "Loaded image(" + bitmap + ") from: " + url + ", with context data: " + contextData);
            }

            @Override
            public void imageLoadFailed(String url, int errorCode, String errorMessage, Object contextData) {
                TextLogUtil.println(logTextView, "Load image failed from: " + url + ", with context data: " + contextData + ", errorCode: " + errorCode + ", errorMessage: " + errorMessage);
            }
        });

        //发起请求: 请求一个静态图片
        String imageUrl = "http://zhangtielei.com/demourls/1.png";
        Object contextData = "Here ImageLoaderDemoActivity";
        TextLogUtil.println(logTextView, "Start to download image from: " + imageUrl + ", with request context data: " + contextData);
        imageLoader.startImageLoad(imageUrl, contextData);
    }

}
