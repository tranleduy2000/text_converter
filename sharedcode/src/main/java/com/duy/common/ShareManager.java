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

package com.duy.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Duy on 11/13/2017.
 */

public class ShareManager {
    public static void shareText(Context context, String text) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        context.startActivity(intent);
    }

    public static void shareApp(Activity context, String appId) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String url = String.format("http://play.google.com/store/apps/details?id=%s", appId);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setType("text/plain");
        context.startActivity(intent);
    }

    public static void shareThisApp(Activity context) {
        shareApp(context, context.getPackageName());
    }
}
