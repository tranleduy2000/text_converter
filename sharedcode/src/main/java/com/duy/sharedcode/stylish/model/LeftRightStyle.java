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

package com.duy.sharedcode.stylish.model;


import android.support.annotation.NonNull;

import com.duy.sharedcode.stylish.Style;

/**
 * Created by Duy on 13-Jul-17.
 */
public class LeftRightStyle implements Style {
    @NonNull
    private String left, right;

    public LeftRightStyle(@NonNull String left, @NonNull String right) {
        this.left = left;
        this.right = right;
    }

    @NonNull
    @Override
    public String generate(@NonNull String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                result.append(" ");
            } else {
                result.append(left).append(text.charAt(i)).append(right);
            }
        }
        return result.toString();
    }
}
