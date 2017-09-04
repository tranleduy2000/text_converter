package com.xlythe.view.floating;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.util.Log;

/**
 * Creates the shortcut icon
 */
public abstract class CreateShortcutActivity extends Activity {
    private static final String TAG = "CreateShortcutActivity";

    private static final int REQUEST_CODE_WINDOW_OVERLAY_PERMISSION = 10001;

    @DrawableRes
    public abstract int getShortcutIcon();

    public abstract CharSequence getShortcutName();

    public abstract Intent getOpenShortcutActivityIntent();

    public void onCreate(Bundle state) {
        super.onCreate(state);

        if (Intent.ACTION_CREATE_SHORTCUT.equals(getIntent().getAction())) {
            if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
                startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())),
                        REQUEST_CODE_WINDOW_OVERLAY_PERMISSION);
            } else {
                onSuccess();
            }
        } else {
            Log.w(TAG, "CreateShortcutActivity called with unexpected Action " + getIntent().getAction());
            onFailure();
        }
    }

    private void onSuccess() {
        Intent.ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this, getShortcutIcon());

        Intent intent = new Intent();

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, getOpenShortcutActivityIntent());
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getShortcutName());
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void onFailure() {
        setResult(RESULT_CANCELED);
        finish();
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
