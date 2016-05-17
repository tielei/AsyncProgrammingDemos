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

/**
 * Created by Tielei Zhang on 16/5/17.
 */
public interface ImageLoaderListener {
    /**
     * 图片加载成功回调.
     * @param url 图片地址
     * @param bitmap 下载到的Bitmap对象.
     * @param contextData 上下文数据.
     */
    void imageLoadSuccess(String url, Bitmap bitmap, Object contextData);
    /**
     * 图片加载失败回调.
     * @param url 图片地址
     * @param errorCode 错误码.
     * @param errorMessage 错误信息简短描述. 供调用者理解错误原因.
     * @param contextData 上下文数据.
     */
    void imageLoadFailed(String url, int errorCode, String errorMessage, Object contextData);
}
