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
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.duy.common.utils.DLog;

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
    private static final String TAG = "ApplicationUtil";

    public static void setup(ViewPager viewPager, FragmentManager fragmentManager, Context context) {
        if (viewPager == null) return;
        viewPager.setAdapter(new ApplicationPagerAdapter(fragmentManager, context));
    }

    static ArrayList<ApplicationItem> getAllPackage(Context context) {
        return loadData(context);
    }

    private static ArrayList<ApplicationItem> loadData(Context mContext) {
        ArrayList<ApplicationItem> items = new ArrayList<>();
        try {
            InputStream stream = mContext.getAssets().open("application/packages.json");
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
                items.add(new ApplicationItem(
                        app.getString("name"),
                        app.getString("package"),
                        app.getString("icon_url"),
                        app.getString("wallpaperUrl")));
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
