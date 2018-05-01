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

package com.duy.text.converter.pro;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.duy.text.converter.R;
import com.duy.text.converter.activities.MainActivity;
import com.duy.text.converter.core.codec.BinaryCodec;
import com.duy.text.converter.core.codec.interfaces.Codec;
import com.duy.text.converter.core.codec.interfaces.CodecMethod;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Duy on 2/14/2018.
 */
public class CodecAllFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    public static Matcher<? super View> hasData() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item instanceof RecyclerView) {
                    int itemCount = ((RecyclerView) item).getAdapter().getItemCount();
                    return itemCount > 0;
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    @Test
    public void encode() {
        Codec codec = new BinaryCodec();
        String selectionText = codec.getName(mRule.getActivity());
        onView(allOf(withId(R.id.spinner_codec_methods), isDisplayed())).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(selectionText))).perform(click());
        onView(allOf(withId(R.id.spinner_codec_methods), isDisplayed())).check(matches(withSpinnerText(selectionText)));

        onView(allOf(withId(R.id.edit_input), isDisplayed()))
                .perform(click())
                .perform(clearText())
                .perform(typeText("Sample"))
                .perform(closeSoftKeyboard());

        onView(allOf(withId(R.id.img_encode_all), isDisplayed())).perform(click());
        onView(withId(R.id.recycler_view))
                .check(matches(hasData()))
                .check(matches(withDataSize(CodecMethod.values().length)));
    }

    @Test
    public void decode() {
        Codec codec = new BinaryCodec();
        String selectionText = codec.getName(mRule.getActivity());
        onView(allOf(withId(R.id.spinner_codec_methods), isDisplayed())).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(selectionText))).perform(click());
        onView(allOf(withId(R.id.spinner_codec_methods), isDisplayed())).check(matches(withSpinnerText(selectionText)));

        onView(allOf(withId(R.id.edit_output), isDisplayed()))
                .perform(click())
                .perform(clearText())
                .perform(typeText(codec.decode("Sample")))
                .perform(closeSoftKeyboard());

        onView(allOf(withId(R.id.img_decode_all), isDisplayed())).perform(click());
        onView(withId(R.id.recycler_view))
                .check(matches(hasData()))
                .check(matches(withDataSize(CodecMethod.values().length)));
    }

    private TypeSafeMatcher<View> withDataSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                if (item instanceof RecyclerView) {
                    return ((RecyclerView) item).getAdapter().getItemCount() == size;
                }
                return false;
            }
        };
    }
}