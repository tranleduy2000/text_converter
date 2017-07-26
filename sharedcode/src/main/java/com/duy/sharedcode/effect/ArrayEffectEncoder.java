/*
 * Copyright (c) 2017 by Tran Le Duy
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

package com.duy.sharedcode.effect;

import com.duy.sharedcode.codec.Encoder;

import java.util.ArrayList;

/**
 * Created by Duy on 06-Jul-17.
 */

public class ArrayEffectEncoder implements Style {


    private ArrayList<Encoder> mEncoders;

    public ArrayEffectEncoder() {
        ArrayEffectFactory factory = new ArrayEffectFactory();
        mEncoders = factory.getEncoders();
    }

    public ArrayList<Encoder> getEncoders() {
        return mEncoders;
    }

    @Override
    public ArrayList<String> generate(String input) {
        ArrayList<String> result = new ArrayList<>();
        for (Encoder encoder : mEncoders) {
            String encode = encoder.encode(input);
            result.add(encode);
        }
        return result;
    }
}
