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

package com.duy.text.converter.core.fragments;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.duy.text.converter.R;
import com.duy.text.converter.core.activities.MainActivity;
import com.duy.text.converter.core.codec.interfaces.Codec;
import com.duy.text.converter.core.codec.interfaces.CodecMethod;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.duy.text.converter.core.codec.interfaces.CodecMethod.ASCII;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by Duy on 2/10/2018.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CodecFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void binary_encode() throws InterruptedException {
        String toBeEncode = "Hello Android, Binary codec";
        testEncode(CodecMethod.BINARY.getCodec(), toBeEncode);
    }

    @Test
    public void binary_decode() throws InterruptedException {
        String toBeEncode = "01010100101010010";
        testDecode(CodecMethod.BINARY.getCodec(), toBeEncode);
    }

    @Test
    public void ascii_encode() throws InterruptedException {
        testEncode(ASCII.getCodec(), "Hello ASCII");
    }

    @Test
    public void ascii_decode() throws InterruptedException {
        testDecode(ASCII.getCodec(), "104 101 108 108 111");
    }

    private void testEncode(Codec codec, String stringToBeType) throws InterruptedException {
        onView(allOf(withId(R.id.spinner_choose), isDisplayed())).perform(click());
        Thread.sleep(1000);

        onView(withText(codec.getName(mRule.getActivity()))).perform(click());
        Thread.sleep(1000);

        onView(allOf(withId(R.id.edit_input), isDisplayed())).perform(click()).perform(clearText());
        onView(allOf(withId(R.id.edit_input), isDisplayed())).perform(typeText(stringToBeType));
        Thread.sleep(1000);

        onView(allOf(withId(R.id.edit_output), isDisplayed()))
                .check(matches(withText(codec.encode(stringToBeType))));
    }

    private void testDecode(Codec codec, String stringToBeType) throws InterruptedException {
        onView(allOf(withId(R.id.spinner_choose), isDisplayed())).perform(click());
        Thread.sleep(1000);

        onView(withText(codec.getName(mRule.getActivity()))).perform(click());
        Thread.sleep(1000);

        onView(allOf(withId(R.id.edit_output), isDisplayed())).perform(click()).perform(clearText());
        onView(allOf(withId(R.id.edit_output), isDisplayed())).perform(typeText(stringToBeType));
        Thread.sleep(1000);

        onView(allOf(withId(R.id.edit_input), isDisplayed()))
                .check(matches(withText(codec.decode(stringToBeType))));
    }


}