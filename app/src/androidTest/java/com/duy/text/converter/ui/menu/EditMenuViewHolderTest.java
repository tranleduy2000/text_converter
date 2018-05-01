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