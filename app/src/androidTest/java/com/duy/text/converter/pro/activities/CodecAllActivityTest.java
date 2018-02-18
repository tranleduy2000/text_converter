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

package com.duy.text.converter.pro.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.duy.text.converter.core.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Duy on 2/18/2018.
 */
public class CodecAllActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void encode() throws Exception {
        Intent intent = new Intent(mRule.getActivity(), CodecAllActivity.class);
        intent.setAction(CodecAllActivity.EXTRA_ACTION_ENCODE);
        intent.putExtra(CodecAllActivity.EXTRA_INPUT, "Sample");
        mRule.getActivity().startActivity(intent);
    }

    @Test
    public void decode() throws Exception {
        Intent intent = new Intent(mRule.getActivity(), CodecAllActivity.class);
        intent.setAction(CodecAllActivity.EXTRA_ACTION_DECODE);
        intent.putExtra(CodecAllActivity.EXTRA_INPUT, "Sample");
        mRule.getActivity().startActivity(intent);
    }

}