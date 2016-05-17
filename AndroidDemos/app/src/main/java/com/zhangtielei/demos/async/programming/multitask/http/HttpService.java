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
 *
 * Http服务请求接口.
 */
public interface HttpService {
    /**
     * 发起HTTP请求.
     * @param apiUrl 请求URL
     * @param request 请求参数(用Java Bean表示)
     * @param listener 回调监听器
     * @param contextData 透传参数
     * @param <T> 请求Model类型
     * @param <R> 响应Model类型
     */
    <T, R> void doRequest(String apiUrl, T request, HttpListener<? super T, R> listener, Object contextData);
}
