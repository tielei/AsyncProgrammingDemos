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

package com.zhangtielei.demos.async.programming.multitask.pagecaching.localcache;

import com.zhangtielei.demos.async.programming.common.AsyncCallback;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.model.HttpResponse;

/**
 * Created by Tielei Zhang on 16/5/17.
 * 本地数据缓存.
 */
public interface LocalDataCache {
    /**
     * 异步获取本地缓存的HttpResponse对象.
     * @param key
     * @param callback 用于返回缓存对象
     */
    void getCachingData(String key, AsyncCallback<HttpResponse> callback);

    /**
     * 保存HttpResponse对象到缓存中.
     * @param key
     * @param data 要保存的HttpResponse对象
     * @param callback 用于返回当前保存操作的结果是成功还是失败.
     */
    void putCachingData(String key, HttpResponse data, AsyncCallback<Boolean> callback);
}
