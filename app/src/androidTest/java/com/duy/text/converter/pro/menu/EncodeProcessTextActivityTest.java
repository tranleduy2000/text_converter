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

package com.duy.text.converter.pro.menu;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.duy.text.converter.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Duy on 2/18/2018.
 */
public class EncodeProcessTextActivityTest {
    private static final String TO_BE_ENCODE = "Hello android";
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void encode() {
        Intent intent = new Intent(mRule.getActivity(), EncodeProcessTextActivity.class);
        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, TO_BE_ENCODE);
        mRule.getActivity().startActivity(intent);
    }
}