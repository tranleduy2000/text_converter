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
import static com.duy.text.converter.core.codec.interfaces.CodecMethod.ATBASH;
import static com.duy.text.converter.core.codec.interfaces.CodecMethod.BASE_32;
import static com.duy.text.converter.core.codec.interfaces.CodecMethod.BASE_64;
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
        testEncode(CodecMethod.BINARY.getCodec(), "Hello Android, Binary codec");
    }

    @Test
    public void binary_decode() throws InterruptedException {
        testDecode(CodecMethod.BINARY.getCodec(), "01010100101010010");
    }

    @Test
    public void ascii_encode() throws InterruptedException {
        testEncode(ASCII.getCodec(), "Hello ASCII");
    }

    @Test
    public void ascii_decode() throws InterruptedException {
        testDecode(ASCII.getCodec(), ASCII.getCodec().encode("Hello ascii"));
    }

    @Test
    public void AtbashCodec_encode() throws InterruptedException {
        testEncode(ATBASH.getCodec(), "De ATBASH");
    }

    @Test
    public void AtbashCodec_decode() throws InterruptedException {
        testDecode(ATBASH.getCodec(), ATBASH.getCodec().encode("Hello ATBASH"));
    }

    @Test
    public void Base32Codec_encode() throws InterruptedException {
        testEncode(BASE_32.getCodec(), "De ATBASH");
    }

    @Test
    public void Base32Codec_decode() throws InterruptedException {
        testDecode(BASE_32.getCodec(), BASE_32.getCodec().encode("Hello BASE_32"));
    }

    @Test
    public void Base64Codec_encode() throws InterruptedException {
        testEncode(BASE_64.getCodec(), "Encode BASE_64");
    }

    @Test
    public void Base64Codec_decode() throws InterruptedException {
        testDecode(BASE_64.getCodec(), BASE_64.getCodec().encode("Decode BASE_64"));
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