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

package com.zhangtielei.demos.async.programming.queueing.v2.mock;

import android.os.Handler;
import android.os.Looper;
import com.zhangtielei.demos.async.programming.queueing.v2.Task;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tielei Zhang on 16/9/1.
 *
 * 异步任务的一个假的实现.
 */
public class MockAsyncTask implements Task {
    private static long taskIdCounter;

    private String taskId;
    private TaskListener listener;

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Random rand1 = new Random();
    private Random rand2 = new Random();

    public MockAsyncTask() {
        taskId = String.valueOf(++taskIdCounter);
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void start() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //任务随机执行0~3秒
                try {
                    TimeUnit.MILLISECONDS.sleep(rand1.nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟失败情况: 以80%的概率失败
                Exception error = null;
                if (rand2.nextInt(10) < 8) {
                    error = new RuntimeException("runtime error...");
                }

                final Exception finalError = error;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            try {
                                if (finalError == null) {
                                    listener.taskComplete(MockAsyncTask.this);
                                }
                                else {
                                    listener.taskFailed(MockAsyncTask.this, finalError);
                                }
                            }
                            catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        });
    }

    @Override
    public void setListener(TaskListener listener) {
        this.listener = listener;
    }
}
