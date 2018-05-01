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