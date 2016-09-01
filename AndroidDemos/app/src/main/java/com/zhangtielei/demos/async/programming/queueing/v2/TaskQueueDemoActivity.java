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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.common.utils.TextLogUtil;
import com.zhangtielei.demos.async.programming.queueing.v2.mock.MockAsyncTask;

/**
 * 演示基于"异步+Callback"的任务队列的运行情况.
 */
public class TaskQueueDemoActivity extends AppCompatActivity {
    private TextView description;
    private TextView logTextView;

    private TaskQueue taskQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        description = (TextView) findViewById(R.id.description);
        logTextView = (TextView) findViewById(R.id.log_display);

        description.setText(R.string.queueing_demo2_description);

        taskQueue = new CallbackBasedTaskQueue();
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
            Task task = new MockAsyncTask();
            TextLogUtil.println(logTextView, "Add task (" + task.getTaskId() + ") to queue");
            taskQueue.addTask(task);
        }

    }

    @Override
    protected void onDestroy() {
        taskQueue.setListener(null);
        taskQueue.destroy();

        super.onDestroy();
    }

}
