/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.duy.text.converter.clipboard;

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
