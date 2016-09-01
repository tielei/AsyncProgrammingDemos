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

import android.util.Log;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.AsyncOnSubscribe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Tielei Zhang on 16/9/1.
 *
 * 基于RxJava机制的队列实现
 */
public class RxJavaBasedTaskQueue implements TaskQueue {
    private static final String TAG = "TaskQueue";

    /**
     * Task排队的队列. 不需要thread-safe
     */
    private Queue<QueueElement<?>> taskQueue = new LinkedList<QueueElement<?>>();
    private boolean stopped;

    /**
     * 一个任务最多重试次数.
     * 重试次数超过MAX_RETRIES, 任务则最终失败.
     */
    private static final int MAX_RETRIES = 3;

    @Override
    public <R> Observable<R> addTask(Task<R> task) {
        //新任务加入队列
        final QueueElement<R> element = new QueueElement<R>();
        element.task = task;
        //subscriber后面会赋值
        taskQueue.offer(element);

        //把异步任务task转成一个Observable, 用于当前addTask的返回
        Observable<R> taskObservable = Observable.create(new AsyncOnSubscribe<Integer, R>() {
            @Override
            protected Integer generateState() {
                return 0;
            }

            @Override
            protected Integer next(Integer state, long requested, Observer<Observable<? extends R>> observer) {
                Observable<R> asyncObservable = Observable.create(new Observable.OnSubscribe<R>() {
                    @Override
                    public void call(Subscriber<? super R> subscriber) {
                        //得到subscriber了,存入队列元素
                        element.subscriber = subscriber;
                        //这里能启动排队任务了
                        if (taskQueue.size() == 1 && !stopped) {
                            //当前是第一个排队任务, 立即执行它
                            QueueElement element = taskQueue.peek();
                            launchNextTask(element);
                        }
                    }
                });
                observer.onNext(asyncObservable);
                observer.onCompleted();
                return 1;
            }
        });

        return taskObservable;
    }

    @Override
    public void destroy() {
        stopped = true;
    }

    private void launchNextTask(final QueueElement element) {
        //取当前队列头的任务, 但不出队列
        if (element == null || element.task == null || element.subscriber == null) {
            //impossible case
            Log.e(TAG, "impossible: NO task element in queue, unexpected!");
            return;
        }

        Task<?> task = element.task;

        Log.d(TAG, "start task (" + task.getTaskId() + ")");
        task.start().subscribe(new Subscriber<Object>() {
            @Override
            public void onNext(Object data) {
                element.taskResult = data;
            }

            @Override
            public void onCompleted() {
                taskComplete(element);
            }

            @Override
            public void onError(Throwable e) {
                taskFailed(element, e);

            }
        });
    }

    public void taskComplete(QueueElement element) {
        element.runCount++;
        Log.d(TAG, "task (" + element.task.getTaskId() + ") complete");
        finishTask(element, null);
    }

    public void taskFailed(QueueElement element, Throwable error) {
        element.runCount++;
        Task<?> task = element.task;
        if (element.runCount < MAX_RETRIES && !stopped) {
            //可以继续尝试
            Log.d(TAG, "task (" + task.getTaskId() + ") failed, try again. runCount: " + element.runCount);
            launchNextTask(element);
        }
        else {
            //最终失败
            Log.d(TAG, "task (" + task.getTaskId() + ") failed, final failed! runCount: " + element.runCount);
            finishTask(element, error);
        }
    }

    /**
     * 一个任务最终结束(成功或最终失败)后的处理
     * @param element
     * @param error
     */
    private void finishTask(QueueElement element, Throwable error) {
        //回调

        if (error == null) {
            if (element.taskResult != null) {
                element.subscriber.onNext(element.taskResult);
            }
            element.subscriber.onCompleted();
        }
        else {
            element.subscriber.onError(error);
        }

        //出队列
        taskQueue.poll();

        //启动队列下一个任务
        if (taskQueue.size() > 0 && !stopped) {
            QueueElement nextElement = taskQueue.peek();
            launchNextTask(nextElement);
        }
    }

    /**
     * 任务队列里存放的对象类型.
     */
    private static class QueueElement<R> {
        /**
         * 要执行的任务.
         */
        public Task<R> task;
        /**
         * 用于执行完任务回调的Subscriber.
         */
        public Subscriber<? super R> subscriber;
        /**
         * 任务已经执行的次数, 失败超过MAX_RETRIES次就算失败
         */
        public int runCount;
        /**
         * 可能的任务执行结果.
         * 如果任务没有返回结果, 那么这个值为null
         */
        public R taskResult;
    }

}
