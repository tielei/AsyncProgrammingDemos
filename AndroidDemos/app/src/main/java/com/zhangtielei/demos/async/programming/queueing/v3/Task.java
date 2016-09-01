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

package com.zhangtielei.demos.async.programming.queueing.v3;

import rx.Observable;

/**
 * Created by Tielei Zhang on 16/9/1.
 *
 * 供任务队列执行的异步任务接口定义.
 *
 * 第三个版本: 不再使用TaskListener传递回调, 而是使用Observable.
 *
 * @param <R> 异步任务执行完要返回的数据类型.
 */
public interface Task <R> {
    /**
     * 唯一标识当前任务的ID
     * @return
     */
    String getTaskId();

    /**
     *
     * 启动任务.
     *
     * 注: start方法需在主线程上执行.
     *
     * @return 一个Observable. 调用者通过这个Observable获取异步任务执行结果.
     */
    Observable<R> start();
}
