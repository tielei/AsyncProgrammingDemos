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

package com.zhangtielei.demos.async.programming.common.utils;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tielei Zhang on 16/8/16.
 * 一个工具类, 用于在页面上打印日志.
 */
public class TextLogUtil {
    /**
     * 在一个TextView上追加一行日志.
     * @param logTextView
     * @param log
     */
    public static void println(TextView logTextView, CharSequence log) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        String timestamp = sdf.format(new Date());

        CharSequence newText = timestamp + ": " + log;
        CharSequence oldText = logTextView.getText();
        if (oldText == null || oldText.length() <= 0) {
            logTextView.setText(newText);
        }
        else {
            logTextView.setText(oldText + "\n" + newText);
        }
    }
}
