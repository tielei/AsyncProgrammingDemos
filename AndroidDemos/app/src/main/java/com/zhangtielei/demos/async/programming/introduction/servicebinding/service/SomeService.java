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

package com.zhangtielei.demos.async.programming.introduction.servicebinding.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 一个Service例子, 用于演示Service Binding中存在的异步问题.
 */
public class SomeService extends Service {
    private static final String TAG = "SomeService";

    public SomeService() {
    }

    /**
     * 用于OnBind返回的Binder实例
     */
    private ServiceBinder binder = new ServiceBinder();
    private List<ServiceListener> listeners = new ArrayList<ServiceListener>();

    private Random random = new Random();

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        return binder;
    }

    public void addListener(ServiceListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(ServiceListener listener) {
        listeners.remove(listener);
    }

    /**
     * 与此Service通信的Binder类
     */
    public class ServiceBinder extends Binder {
        public SomeService getService() {
            return SomeService.this;
        }
    }

    /**
     * 此Service对外界的回调接口定义.
     */
    public interface ServiceListener {
        void callback();
    }

}
