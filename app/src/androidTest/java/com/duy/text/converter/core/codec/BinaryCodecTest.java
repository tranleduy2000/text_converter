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

package com.duy.text.converter.core.codec;

import android.support.test.rule.ActivityTestRule;

import com.duy.common.utils.ShareUtil;
import com.duy.text.converter.core.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Duy on 1/6/2018.
 */
public class BinaryCodecTest {
    @Rule
    ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void encode() throws Exception {
    }

    @Test
    public void decode() throws Exception {
    }

    @Test
    public void testBigData() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 100 * 1024; i++) {
            builder.append(" ");
        }
        System.out.println("string length " + builder.length());
        BinaryCodec codec = new BinaryCodec();
        String encoded = codec.encode(builder.toString());

        System.out.println("encode = " + encoded);

        ShareUtil.shareText(mainActivityActivityTestRule.getActivity(), encoded);
    }

}