/*
 * Copyright (c) 2017 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.common.ads.apps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.duy.common.BuildConfig;
import com.duy.common.utils.DLog;
import com.duy.common.utils.StoreUtil;
import com.duy.common.views.viewpager.AutoScrollViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Duy on 26-Dec-17.
 */

public class ApplicationHelper {
    private static final String TAG = "ApplicationHelper";

    @Nullable
    public static ApplicationPagerAdapter setup(AutoScrollViewPager viewPager, FragmentManager fragmentManager, Context context) {
        if (viewPager == null) return null;
        DLog.d(TAG, "setup() called");
        ApplicationPagerAdapter adapter = new ApplicationPagerAdapter(fragmentManager, context);
        viewPager.setAdapter(adapter);
        viewPager.startAutoScroll();
        return adapter;
    }

    @NonNull
    static ArrayList<ApplicationItem> getAllPackage(Context context) {
        return loadData(context, false);
    }

    @NonNull
    private static ArrayList<ApplicationItem> loadData(Context context, boolean includeInstalledApp) {
        includeInstalledApp |= BuildConfig.DEBUG;
        ArrayList<ApplicationItem> items = new ArrayList<>();
        try {
            InputStream stream = context.getAssets().open("application/packages.json");
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            byte[] byteArray = buffer.toByteArray();

            String text = new String(byteArray);
            JSONObject jsonObject = new JSONObject(text);
            JSONArray jsonArray = jsonObject.getJSONArray("apps");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject app = jsonArray.getJSONObject(i);
                ApplicationItem applicationItem = new ApplicationItem(
                        app.getString("name"),
                        app.getString("package"),
                        app.getString("icon_url"),
                        app.getString("wall_url"));
                if (StoreUtil.isAppInstalled(context, applicationItem.getApplicationId())) {
                    if (includeInstalledApp) {
                        items.add(applicationItem);
                    }
                } else {
                    items.add(applicationItem);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DLog.d(TAG, "loadData: " + items);
        return items;
    }
}
