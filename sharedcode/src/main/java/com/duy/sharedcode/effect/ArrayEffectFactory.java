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

import android.util.Pair;

import com.duy.sharedcode.codec.Encoder;

import java.util.ArrayList;

/**
 * Created by Duy on 13-Jul-17.
 */

public class ArrayEffectFactory {
    public static ArrayList<Pair<String, String>> createLeftRightPair() {
        ArrayList<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("⫷", "⫸"));
        list.add(new Pair<>("╰", "╯"));
        list.add(new Pair<>("╭", "╮"));
        list.add(new Pair<>("╟", "╢"));
        list.add(new Pair<>("╚", "╝"));
        list.add(new Pair<>("╔", "╗"));
        list.add(new Pair<>("⚞", "⚟"));
        list.add(new Pair<>("⟅", "⟆"));
        list.add(new Pair<>("⟦", "⟧"));
        list.add(new Pair<>("☾", "☽"));
        list.add(new Pair<>("【", "】"));
        list.add(new Pair<>("〔", "〕"));
        list.add(new Pair<>("《", "》"));
        list.add(new Pair<>("〘", "〙"));
        list.add(new Pair<>("『", "』"));
        return list;
    }

    public ArrayList<Encoder> getEncoders() {
        ArrayList<Encoder> encoders = new ArrayList<>();
        ArrayList<Pair<String, String>> leftRightPair = createLeftRightPair();
        for (Pair<String, String> pair : leftRightPair) {
            encoders.add(new LeftRightStyle(pair.first, pair.second));
        }
        return encoders;
    }

}
