/*
 * Copyright (c) 2018 by Tran Le Duy
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

package com.duy.text.converter.core.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Duy on 13-Apr-18.
 */

public class ClipboardImpl implements IClipboard {
    @NonNull
    private Context mContext;
    @Nullable
    private ClipboardManager mClipboardManager;

    ClipboardImpl(@NonNull Context context) {
        this.mContext = context;
        mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Nullable
    @Override
    public CharSequence getClipboard() {
        try {
            ClipData primaryClip;
            if (mClipboardManager != null) {
                primaryClip = mClipboardManager.getPrimaryClip();
                if (primaryClip != null && primaryClip.getItemCount() > 0) {
                    return primaryClip.getItemAt(0).coerceToText(mContext).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); //null clipboard
        }
        return "";
    }

    @Override
    public boolean setClipboard(@NonNull CharSequence content) {
        ClipData clip = ClipData.newPlainText("Copied Text", content);
        if (mClipboardManager != null) {
            mClipboardManager.setPrimaryClip(clip);
            return true;
        }
        return false;
    }
}
