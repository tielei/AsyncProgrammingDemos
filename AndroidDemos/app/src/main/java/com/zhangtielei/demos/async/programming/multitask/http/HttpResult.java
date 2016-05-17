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
 * HttpResult封装Http请求的结果.
 *
 * 当服务器成功响应的时候, errorCode = SUCCESS, 且服务器的响应转换成response;
 * 当服务器未能成功响应的时候, errorCode != SUCCESS, 且response的值无效.
 *
 * @param <R> 响应Model类型
 */
public class HttpResult <R> {
    /**
     * 错误码定义
     */
    public static final int SUCCESS = 0;//成功
    public static final int REQUEST_ENCODING_ERROR = 1;//对请求进行编码发生错误
    public static final int RESPONSE_DECODING_ERROR = 2;//对响应进行解码发生错误
    public static final int NETWORK_UNAVAILABLE = 3;//网络不可用
    public static final int UNKNOWN_HOST = 4;//域名解析失败
    public static final int CONNECT_TIMEOUT = 5;//连接超时
    public static final int HTTP_STATUS_NOT_OK = 6;//下载请求返回非200
    public static final int UNKNOWN_FAILED = 7;//其它未知错误

    private int errorCode;
    private String errorMessage;
    /**
     * response是服务器返回的响应.
     * 只有当errorCode = SUCCESS, response的值才有效.
     */
    private R response;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public R getResponse() {
        return response;
    }

    public void setResponse(R response) {
        this.response = response;
    }
}
