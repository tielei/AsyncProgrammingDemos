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

package com.zhangtielei.demos.async.programming.multitask;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.zhangtielei.demos.async.programming.R;
import com.zhangtielei.demos.async.programming.multitask.multilevelcaching.ImageLoaderDemoActivity;
import com.zhangtielei.demos.async.programming.multitask.pagecaching.PageCachingDemoActivity;

/**
 * 多个任务同时执行的演示程序入口列表页面.
 */
public class MultiTaskDemoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        ListView listView = (ListView) findViewById(R.id.item_list);
        Resources resources = getResources();
        String[] textList = new String[] {
                resources.getString(R.string.multitask_demo_1),
                resources.getString(R.string.multitask_demo_2),
                resources.getString(R.string.multitask_demo_3),
                resources.getString(R.string.multitask_demo_4),
        };
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, textList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    {
                        Intent intent = new Intent(MultiTaskDemoListActivity.this, ImageLoaderDemoActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:
                    {
                        Intent intent = new Intent(MultiTaskDemoListActivity.this, com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.MultiRequestsDemoActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:
                    {
                        Intent intent = new Intent(MultiTaskDemoListActivity.this, PageCachingDemoActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3:
                    {
                        Intent intent = new Intent(MultiTaskDemoListActivity.this, com.zhangtielei.demos.async.programming.multitask.simultaneousrequests.rxjavasolution.MultiRequestsDemoActivity.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }

}
