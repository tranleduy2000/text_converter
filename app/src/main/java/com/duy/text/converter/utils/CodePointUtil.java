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

package com.duy.text.converter.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Duy on 2/14/2018.
 */

public class CodePointUtil {
    @NonNull
    public static Iterable<Integer> codePoints(final String string) {
        return new Iterable<Integer>() {
            @NonNull
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    int nextIndex = 0;

                    public boolean hasNext() {
                        return nextIndex < string.length();
                    }

                    public Integer next() {
                        int result = string.codePointAt(nextIndex);
                        nextIndex += Character.charCount(result);
                        return result;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static ArrayList<Integer> codePointsArr(String text) {
        Iterable<Integer> iterable = codePoints(text);
        ArrayList<Integer> codePoints = new ArrayList<>();
        for (Integer codePoint : iterable) {
            codePoints.add(codePoint);
        }
        return codePoints;
    }
}
