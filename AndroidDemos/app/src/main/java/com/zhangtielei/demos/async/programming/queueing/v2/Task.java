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

package com.zhangtielei.demos.async.programming.queueing.v2;

/**
 * Created by Tielei Zhang on 16/9/1.
 *
 * 供任务队列执行的异步任务接口定义.
 *
 * 第二个版本: 不再是异步任务, 变成异步任务.
 *
 */
public interface Task {
    /**
     * 唯一标识当前任务的ID
     * @return
     */
    String getTaskId();

    /**
     * 由于任务是异步任务, 那么start方法被调用只是启动任务;
     * 任务完成后会回调TaskListener.
     *
     * 注: start方法在主线上执行.
     */
    void start();

    /**
     * 设置回调监听.
     * @param listener
     */
    void setListener(TaskListener listener);

    /**
     * 异步任务回调接口.
     */
    interface TaskListener {
        /**
         * 当前任务完成的回调.
         * @param task
         */
        void taskComplete(Task task);
        /**
         * 当前任务执行失败的回调.
         * @param task
         * @param cause 失败原因
         */
        void taskFailed(Task task, Throwable cause);
    }

}
