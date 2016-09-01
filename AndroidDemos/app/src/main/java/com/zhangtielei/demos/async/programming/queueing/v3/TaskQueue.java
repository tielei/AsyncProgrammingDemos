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
 * 任务队列接口.
 *
 * 第三版: 对应第三版的Task接口, 并且舍弃TaskQueueListener.
 */
public interface TaskQueue {
    /**
     * 向队列中添加一个任务.
     *
     * @param task
     * @param <R> 异步任务执行完要返回的数据类型.
     * @return 一个Observable. 调用者通过这个Observable获取异步任务执行结果.
     */
    <R> Observable<R> addTask(Task<R> task);

    /**
     * 销毁队列.
     * 注: 队列在最后不用的时候, 应该主动销毁它.
     */
    void destroy();
}
