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
import com.duy.text.converter.core.hashfunction.IHashFunction;
import com.duy.text.converter.core.hashfunction.Md5HashFunction;
import com.duy.text.converter.core.hashfunction.Sha1HashFunction;
import com.duy.text.converter.core.hashfunction.Sha256HashFunction;
import com.duy.text.converter.core.hashfunction.Sha384HashFunction;
import com.duy.text.converter.core.hashfunction.Sha512HashFunction;
import com.duy.text.converter.core.hashfunction.UnixCryptHashFunction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Duy on 2/10/2018.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class HashFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void selectCodecFragment() {
        onView(withText(mRule.getActivity().getString(R.string.tab_title_hash_function))).perform(click());
    }

    @Test
    public void Md5HashFunction_encode() throws InterruptedException {
        testEncode(new Md5HashFunction(), "HashFragmentTest");
    }

    @Test
    public void Sha1HashFunction_encode() throws InterruptedException {
        testEncode(new Sha1HashFunction(), "Sha1HashFunction");
    }

    @Test
    public void Sha256HashFunction_encode() throws InterruptedException {
        testEncode(new Sha256HashFunction(), "Sha256HashFunction");
    }

    @Test
    public void Sha384HashFunction_encode() throws InterruptedException {
        testEncode(new Sha384HashFunction(), "Sha384HashFunction");
    }

    @Test
    public void Sha512HashFunction_encode() throws InterruptedException {
        testEncode(new Sha512HashFunction(), "Sha512HashFunction");
    }

    @Test
    public void UnixCryptHashFunction_encode() throws InterruptedException {
        testEncode(new UnixCryptHashFunction(), "UnixCryptHashFunction");
    }

    private void testEncode(IHashFunction codec, String stringToBeType) throws InterruptedException {
        String selectionText = codec.getName();
        onView(allOf(withId(R.id.spinner_hash_methods), isDisplayed())).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(selectionText))).perform(click());
        onView(allOf(withId(R.id.spinner_hash_methods), isDisplayed())).check(matches(withSpinnerText(selectionText)));

        onView(allOf(withId(R.id.edit_input), isDisplayed()))
                .perform(click())
                .perform(clearText())
                .perform(replaceText(stringToBeType))
                .perform(closeSoftKeyboard());

        onView(allOf(withId(R.id.edit_output), isDisplayed()))
                .check(matches(withText(codec.encode(stringToBeType))));
    }

}