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

package com.zhangtielei.demos.async.programming.queueing.v1;

/**
 * Created by Tielei Zhang on 16/8/31.
 *
 * 供任务队列执行的同步任务接口定义.
 */
public interface Task {
    /**
     * 唯一标识当前任务的ID
     * @return
     */
    String getTaskId();

    /**
     * 同步执行任务.
     * 如果执行出错, 可以在处理过程中按异常抛出; 若没抛异常,则认为是执行成功.
     *
     * 注: run方法在异步线程上执行.
     */
    void run();
}
