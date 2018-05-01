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

import android.support.test.rule.ActivityTestRule;

import com.duy.common.utils.DLog;
import com.duy.text.converter.TestUtils;
import com.duy.text.converter.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Duy on 13-Apr-18.
 */
public class IClipboardTest {
    private static final String TAG = "IClipboardTest";
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void getClipboard() throws Throwable {
        mRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IClipboard clipboard = ClipboardFactory.createClipboardManager(mRule.getActivity());
                String text = "Text";
                clipboard.setClipboard(text);
                CharSequence clipboardContent = clipboard.getClipboard();
                assertThat(text, equalTo(clipboardContent));
            }
        });
    }

    @Test
    public void setClipboard_success() throws Throwable {
        mRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IClipboard clipboard = ClipboardFactory.createClipboardManager(mRule.getActivity());
                String text = "Text";
                boolean result = clipboard.setClipboard(text);
                assertThat(result, equalTo(true));
            }
        });

    }

    @Test
    public void setClipboard_failed() throws Throwable {
        mRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String largeText = TestUtils.duplicateString("LargeText", 500000);
                if (DLog.DEBUG) DLog.d(TAG, "run: size " + largeText.getBytes().length);
                IClipboard clipboard = ClipboardFactory.createClipboardManager(mRule.getActivity());
                boolean result = clipboard.setClipboard(largeText);
                assertThat(result, equalTo(true));
            }
        });
    }

}