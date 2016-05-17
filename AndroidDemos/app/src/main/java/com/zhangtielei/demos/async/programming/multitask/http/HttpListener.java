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
package com.zhangtielei.demos.async.programming.multitask.http;

/**
 * Created by Tielei Zhang on 16/5/16.
 * 监听Http服务的监听器接口.
 *
 * @param <T> 请求Model类型
 * @param <R> 响应Model类型
 */
public interface HttpListener <T, R> {
    /**
     * 产生请求结果(成功或失败)时的回调接口.
     * @param apiUrl 请求URL
     * @param request 请求Model
     * @param result 请求结果(包括响应或者错误原因)
     * @param contextData 透传参数
     */
    void onResult(String apiUrl, T request, HttpResult<R> result, Object contextData);
}
