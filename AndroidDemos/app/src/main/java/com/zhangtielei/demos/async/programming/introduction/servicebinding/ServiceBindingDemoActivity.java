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

package com.zhangtielei.demos.async.programming.introduction.servicebinding;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.common.utils.TextLogUtil;
import com.zhangtielei.demos.async.programming.introduction.servicebinding.service.SomeService;

/**
 * 用于演示最普通的Service绑定和解绑.
 */
public class ServiceBindingDemoActivity extends AppCompatActivity implements SomeService.ServiceListener {
    /**
     * 对于Service的引用
     */
    private SomeService someService;
    /**
     * 指示本Activity是否处于running状态：执行过onResume就变为running状态。
     */
    private boolean running;

    private TextView description;
    private TextView logTextView;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //解除Activity与Service的引用和监听关系
            //...
            if (someService != null) {
                someService.removeListener(ServiceBindingDemoActivity.this);
                someService = null;
            }
            TextLogUtil.println(logTextView, "disconnected to SomeService...");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (running) {
                //建立Activity与Service的引用和监听关系
                //...
                SomeService.ServiceBinder binder = (SomeService.ServiceBinder) service;
                someService = binder.getService();
                someService.addListener(ServiceBindingDemoActivity.this);
                TextLogUtil.println(logTextView, "bound to SomeService...");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_display);

        description = (TextView) findViewById(R.id.description);
        logTextView = (TextView) findViewById(R.id.log_display);

        description.setText(R.string.service_binding_description);

    }

    @Override
    public void onResume() {
        super.onResume();
        running = true;

        Intent intent = new Intent(this, SomeService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        TextLogUtil.println(logTextView, "Requesting bind to SomeService...");
    }

    @Override
    public void onPause() {
        super.onPause();
        running = false;

        //解除Activity与Service的引用和监听关系
        //...
        if (someService != null) {
            someService.removeListener(ServiceBindingDemoActivity.this);
            someService = null;
        }
        unbindService(serviceConnection);
        TextLogUtil.println(logTextView, "unbind to SomeService...");
    }

    @Override
    public void callback() {
        //TODO:
    }
}
