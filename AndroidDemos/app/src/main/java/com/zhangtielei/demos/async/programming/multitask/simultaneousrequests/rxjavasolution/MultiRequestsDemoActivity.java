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

package com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.rxjavasolution;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.common.utils.TextLogUtil;
import com.zhangtielei.demos.async.programming.multitask.http.HttpListener;
import com.zhangtielei.demos.async.programming.multitask.http.HttpResult;
import com.zhangtielei.demos.async.programming.multitask.http.HttpService;
import com.zhangtielei.demos.async.programming.multitask.http.mock.MockHttpService;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpRequest1;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpRequest2;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpResponse1;
import com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.model.HttpResponse2;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.AsyncOnSubscribe;

/**
 * 演示如何使用RxJava merge操作来处理两个请求同时发生的情况.
 */
public class MultiRequestsDemoActivity extends AppCompatActivity {
    private HttpService httpService = new MockHttpService();

    private TextView description;
    private TextView logTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        description = (TextView) findViewById(R.id.description);
        logTextView = (TextView) findViewById(R.id.log_display);

        description.setText(R.string.multirequest_rxjava_demo_description);

        /**
         * 先根据AsyncOnSubscribe机制将两次请求封装成两个Observable
         */

        Observable<HttpResponse1> request1 = Observable.create(new AsyncOnSubscribe<Integer, HttpResponse1>() {
            @Override
            protected Integer generateState() {
                return 0;
            }

            @Override
            protected Integer next(Integer state, long requested, Observer<Observable<? extends HttpResponse1>> observer) {
                final Observable<HttpResponse1> asyncObservable = Observable.create(new Observable.OnSubscribe<HttpResponse1>() {
                    @Override
                    public void call(final Subscriber<? super HttpResponse1> subscriber) {
                        //启动第一个异步请求
                        httpService.doRequest("http://...", new HttpRequest1(),
                                new HttpListener<HttpRequest1, HttpResponse1>() {
                                    @Override
                                    public void onResult(String apiUrl, HttpRequest1 request, HttpResult<HttpResponse1> result, Object contextData) {
                                        TextLogUtil.println(logTextView, "Rx HttpResponse1. Success? " + (result.getErrorCode() == HttpResult.SUCCESS));
                                        //第一个异步请求结束, 向asyncObservable中发送结果
                                        if (result.getErrorCode() == HttpResult.SUCCESS) {
                                            subscriber.onNext(result.getResponse());
                                            subscriber.onCompleted();
                                        }
                                        else {
                                            subscriber.onError(new Exception("request1 failed"));
                                        }
                                    }
                                },
                                null);
                    }
                });
                observer.onNext(asyncObservable);
                observer.onCompleted();
                return 1;
            }
        });

        Observable<HttpResponse2> request2 = Observable.create(new AsyncOnSubscribe<Integer, HttpResponse2>() {
            @Override
            protected Integer generateState() {
                return 0;
            }

            @Override
            protected Integer next(Integer state, long requested, Observer<Observable<? extends HttpResponse2>> observer) {
                final Observable<HttpResponse2> asyncObservable = Observable.create(new Observable.OnSubscribe<HttpResponse2>() {
                    @Override
                    public void call(final Subscriber<? super HttpResponse2> subscriber) {
                        //启动第二个异步请求
                        httpService.doRequest("http://...", new HttpRequest2(),
                                new HttpListener<HttpRequest2, HttpResponse2>() {
                                    @Override
                                    public void onResult(String apiUrl, HttpRequest2 request, HttpResult<HttpResponse2> result, Object contextData) {
                                        TextLogUtil.println(logTextView, "Rx HttpResponse2. Success? " + (result.getErrorCode() == HttpResult.SUCCESS));
                                        //第二个异步请求结束, 向asyncObservable中发送结果
                                        if (result.getErrorCode() == HttpResult.SUCCESS) {
                                            subscriber.onNext(result.getResponse());
                                            subscriber.onCompleted();
                                        }
                                        else {
                                            subscriber.onError(new Exception("reques2 failed"));
                                        }
                                    }
                                },
                                null);
                    }
                });
                observer.onNext(asyncObservable);
                observer.onCompleted();
                return 1;
            }
        });

        TextLogUtil.println(logTextView, "Start request1 & request2...");

        //把两个Observable表示的request用merge连接起来
        Observable.merge(request1, request2)
                .subscribe(new Subscriber<Object>() {
                    private HttpResponse1 response1;
                    private HttpResponse2 response2;

                    @Override
                    public void onNext(Object response) {
                        if (response instanceof HttpResponse1) {
                            response1 = (HttpResponse1) response;
                        }
                        else if (response instanceof HttpResponse2) {
                            response2 = (HttpResponse2) response;
                        }
                    }

                    @Override
                    public void onCompleted() {
                        processData(response1, response2);
                    }

                    @Override
                    public void onError(Throwable e) {
                        processError(e);
                    }
                });
    }

    private void processData(HttpResponse1 data1, HttpResponse2 data2) {
        //更新UI, 展示请求结果.
        TextLogUtil.println(logTextView, "Both data ready. HttpResponse1: " + data1.getText() + ", HttpResponse2: " + data2.getText());
    }

    private void processError(Throwable e) {
        //更新UI,展示错误.
        TextLogUtil.println(logTextView, "Requests failed! errorMessage: " + e.getMessage());
    }
}