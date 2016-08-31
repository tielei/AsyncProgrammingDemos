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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.common.utils.TextLogUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TaskQueueDemoActivity extends AppCompatActivity {
    private TextView description;
    private TextView logTextView;

    private TaskQueue taskQueue;

    private long taskIdCounter;
    private Random rand1 = new Random();
    private Random rand2 = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        description = (TextView) findViewById(R.id.description);
        logTextView = (TextView) findViewById(R.id.log_display);

        description.setText(R.string.queueing_demo1_description);

        taskQueue = new TSQBasedTaskQueue();
        taskQueue.setListener(new TaskQueue.TaskQueueListener() {
            @Override
            public void taskComplete(Task task) {
                TextLogUtil.println(logTextView, "Task (" + task.getTaskId() + ") complete");
            }

            @Override
            public void taskFailed(Task task, Throwable cause) {
                TextLogUtil.println(logTextView, "Task (" + task.getTaskId() + ") failed, error message: " + cause.getMessage());
            }
        });

        /**
         * 启动5个任务
         */
        for (int i = 0; i < 5; i++) {
            Task task = new Task() {
                private String taskId = String.valueOf(++taskIdCounter);

                @Override
                public void run() {
                    //任务随机执行0~3秒
                    try {
                        TimeUnit.MILLISECONDS.sleep(rand1.nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //模拟失败情况: 以80%的概率失败
                    if (rand2.nextInt(10) < 8) {
                        throw new RuntimeException("runtime error...");
                    }
                }

                @Override
                public String getTaskId() {
                    return taskId;
                }
            };
            TextLogUtil.println(logTextView, "Add task (" + task.getTaskId() + ") to queue");
            taskQueue.addTask(task);
        }

    }

    @Override
    protected void onDestroy() {
        taskQueue.destroy();
        super.onDestroy();
    }

}
