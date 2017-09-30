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

package com.duy.text.converter.view.floating;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.duy.text.converter.R;


/**
 * When the shortcut icon is pressed, use this Activity to launch the overlay Service
 */
public abstract class OpenShortcutActivity extends Activity {
    public static final String ACTION_OPEN = FloatingView.ACTION_OPEN;
    private static final int REQUEST_CODE_WINDOW_OVERLAY_PERMISSION = 10001;

    public abstract Intent createServiceIntent();

    public void onCreate(Bundle state) {
        super.onCreate(state);

        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())),
                    REQUEST_CODE_WINDOW_OVERLAY_PERMISSION);
        } else {
            onSuccess();
        }
    }

    private void onSuccess() {
        Intent intent = createServiceIntent();
        if (ACTION_OPEN.equals(getIntent().getAction()) && intent.getAction() == null) {
            intent.setAction(ACTION_OPEN);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        finish();
    }

    private void onFailure() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.blank, R.anim.blank);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_WINDOW_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(this)) {
                onSuccess();
            } else {
                onFailure();
            }
        }
    }
}
