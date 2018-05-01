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

import com.duy.text.converter.R;
import com.duy.text.converter.activities.MainActivity;
import com.duy.text.converter.core.codec.interfaces.CodecMethod;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by Duy on 2/14/2018.
 */
public class FloatingCodecServiceTest {
    private static final String TO_BE_ENCODE = "Sample";
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void encode() {
        onView(withContentDescription(mRule.getActivity().getString(R.string.desc_open_drawer)))
                .perform(click());
        onView(withText(mRule.getActivity().getString(R.string.action_open_floating_codec)))
                .perform(click());

        onView(withId(R.id.fab)).check(matches(isDisplayed())).perform(click());

        onView(allOf(withId(R.id.edit_input), isDisplayed()))
                .perform(click())
                .perform(clearText())
                .perform(typeText(TO_BE_ENCODE))
                .perform(closeSoftKeyboard());

        for (CodecMethod codecMethod : CodecMethod.values()) {
            onView(withText(codecMethod.getCodec().getName(mRule.getActivity())))
                    .perform(click());
            onView(allOf(withId(R.id.edit_output), isDisplayed()))
                    .check(matches(withText(codecMethod.getCodec().encode(TO_BE_ENCODE))));
        }
    }

}