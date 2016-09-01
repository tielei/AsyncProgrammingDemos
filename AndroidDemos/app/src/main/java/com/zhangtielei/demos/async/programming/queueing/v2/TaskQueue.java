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
 * 任务队列接口.
 *
 * 第二版: 对应第二版的Task接口.
 */
public interface TaskQueue {
    /**
     * 向队列中添加一个任务.
     *
     * @param task
     */
    void addTask(Task task);

    /**
     * 设置监听器.
     * @param listener
     */
    void setListener(TaskQueueListener listener);

    /**
     * 销毁队列.
     * 注: 队列在最后不用的时候, 应该主动销毁它.
     */
    void destroy();

    /**
     * 任务队列对外监听接口.
     */
    interface TaskQueueListener {
        /**
         * 任务完成的回调.
         * @param task
         */
        void taskComplete(Task task);
        /**
         * 任务最终失败的回调.
         * @param task
         * @param cause 失败原因
         */
        void taskFailed(Task task, Throwable cause);
    }
}
