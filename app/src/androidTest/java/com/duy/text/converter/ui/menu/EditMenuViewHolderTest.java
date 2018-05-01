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

package com.duy.text.converter.ui.menu;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.EditText;

import com.duy.text.converter.R;
import com.duy.text.converter.activities.MainActivity;
import com.duy.text.converter.clipboard.ClipboardFactory;
import com.duy.text.converter.clipboard.IClipboard;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by Duy on 13-Apr-18.
 */
public class EditMenuViewHolderTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private EditMenuViewHolder editMenuViewHolder;

    @Before
    public void setup() {
        View menuView = mRule.getActivity().findViewById(R.id.edit_menu_input);
        EditText editInput = mRule.getActivity().findViewById(R.id.edit_input);
        editMenuViewHolder = new EditMenuViewHolder(menuView, editInput);
        editMenuViewHolder.bind();
    }

    @Test
    public void clear() throws Throwable {
        mRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editMenuViewHolder.clear();
            }
        });
        onView(allOf(withId(R.id.edit_input), isDisplayed()))
                .check(matches(withText("")));
    }

    @Test
    public void paste() throws Throwable {
        final String text = "sample";
        mRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IClipboard clipboard = ClipboardFactory.createClipboardManager(mRule.getActivity());
                clipboard.setClipboard(text);
                editMenuViewHolder.clear();
                editMenuViewHolder.paste();
            }
        });
        onView(allOf(withId(R.id.edit_input), isDisplayed()))
                .check(matches(withText(text)));
    }

    @Test
    public void copy() throws Throwable {
        final String text = "sample";

        mRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IClipboard clipboard = ClipboardFactory.createClipboardManager(mRule.getActivity());
                clipboard.setClipboard(text);
                editMenuViewHolder.clear();
            }
        });

        onView(allOf(withId(R.id.edit_input), isDisplayed()))
                .perform(typeText(text));

        mRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editMenuViewHolder.copy();
                IClipboard clipboard = ClipboardFactory.createClipboardManager(mRule.getActivity());
                assertThat(clipboard.getClipboard().toString(), equalTo(text));
            }
        });
    }

}