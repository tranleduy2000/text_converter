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

package com.duy.text.converter.core.stylish;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Duy on 06-Jul-17.
 */

public class StylistGenerator {

    private static final String PREF_NAME = "stylish_position.xml";
    private ArrayList<Style> mEncoders;

    public StylistGenerator() {
        this(null);
    }

    /**
     * @param context - if context not null, sort styles
     */
    public StylistGenerator(@Nullable Context context) {
        long time = System.currentTimeMillis();
        mEncoders = StylistFactory.makeStyle();
        if (context != null) {
            //init position, from 0 to mEncoders.size() - 1
            final HashMap<Style, Integer> position = new HashMap<>();
            for (int i = 0; i < mEncoders.size(); i++) {
                position.put(mEncoders.get(i), i);
            }

            //get data from SharedPreferences
            SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            for (int i = 0; i < mEncoders.size(); i++) {
                int newPos = pref.getInt(i + "", -1);
                if (newPos == -1) {
                    pref.edit().putInt(i + "", i).apply();
                    newPos = i;
                }
                position.put(mEncoders.get(i), newPos);
            }

            //sort mEncoders
            Collections.sort(mEncoders, new Comparator<Style>() {
                @Override
                public int compare(Style o1, Style o2) {
                    return position.get(o1).compareTo(position.get(o2));
                }
            });
        }
        System.out.println("time = " + (System.currentTimeMillis() - time));
    }

    public static void swapPosition(Context context, int fromPosition, int toPosition) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int pos1 = pref.getInt(fromPosition + "", fromPosition);
        int pos2 = pref.getInt(toPosition + "", toPosition);

        SharedPreferences.Editor editor = pref.edit();
        //swap it
        editor.putInt(fromPosition + "", pos2);
        editor.putInt(toPosition + "", pos1);
        editor.apply();
    }

    public ArrayList<String> generate(String input) {
        ArrayList<String> result = new ArrayList<>();
        for (Style style : mEncoders) {
            String encode = style.generate(input);
            result.add(encode);
        }
        return result;
    }
}
