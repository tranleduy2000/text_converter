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