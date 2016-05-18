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

package com.zhangtielei.demos.async.programming.multitask.multilevelcaching.memcache;

import android.graphics.Bitmap;

/**
 * Created by Tielei Zhang on 16/5/17.
 *
 * 图片Memory缓存.
 * 由于是内存操作, 采用同步接口.
 */
public interface ImageMemCache {
    Bitmap getImage(String key);
    boolean putImage(String key, Bitmap bitmap);
}
