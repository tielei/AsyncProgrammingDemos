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

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Tielei Zhang on 16/8/31.
 *
 * 基于TSQ (Thread-Safe Queue)的队列实现
 */
public class TSQBasedTaskQueue implements TaskQueue {
    private static final String TAG = "TaskQueue";

    private BlockingQueue<Task> taskBlockingQueue = new LinkedBlockingQueue<Task>();
    private Thread consumerThread;
    private AtomicBoolean stopped = new AtomicBoolean(false);

    private TaskQueueListener listener;

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * 一个任务最多重试次数.
     * 重试次数超过MAX_RETRIES, 任务则最终失败.
     */
    private static final int MAX_RETRIES = 3;

    public TSQBasedTaskQueue() {
        //创建消费者线程
        consumerThread = new Thread() {
            @Override
            public void run() {
                while (!stopped.get() && !Thread.interrupted()) {
                    try {
                        Task task = taskBlockingQueue.take();

                        int retryCount = 0;
                        boolean runSuccess = false;
                        Throwable error = null;
                        while (!stopped.get() && retryCount < MAX_RETRIES && !runSuccess) {
                            try {
                                task.run();
                                //任务成功执行完了
                                runSuccess = true;
                            }
                            catch (Throwable e) {
                                error = e;
                            }
                            retryCount++;
                        }

                        if (runSuccess) {
                            callbackSuccess(task);
                        }
                        else {
                            callbackFailed(task, error);
                        }

                    } catch (InterruptedException e) {
                        //退出了
                    }
                }
                Log.i(TAG, "TSQ thread exit...");
            }
        };
        consumerThread.start();
    }

    @Override
    public void addTask(Task task) {
        try {
            taskBlockingQueue.put(task);
        } catch (InterruptedException e) {
            callbackFailed(task, e);
            Log.e(TAG, "", e);
        }
    }

    @Override
    public void setListener(TaskQueueListener listener) {
        this.listener = listener;
    }

    @Override
    public void destroy() {
        stopped.set(true);
        consumerThread.interrupt();
    }

    private void callbackSuccess(Task task) {
        callbackToListener(task, null);
    }

    private void callbackFailed(Task task, Throwable error) {
        callbackToListener(task, error);
    }

    private void callbackToListener(final Task task, final Throwable error) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (listener != null) {
                        if (error == null) {
                            listener.taskComplete(task);
                        }
                        else {
                            listener.taskFailed(task, error);
                        }
                    }
                }
                catch (Throwable e) {
                    Log.e(TAG, "", e);
                }
            }
        });
    }
}
