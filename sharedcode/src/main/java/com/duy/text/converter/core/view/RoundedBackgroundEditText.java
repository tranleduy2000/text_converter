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

package com.duy.text.converter.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.duy.text.converter.R;

/**
 * Created by Duy on 16-Dec-17.
 */

public class RoundedBackgroundEditText extends BaseEditText {
    public RoundedBackgroundEditText(Context context) {
        super(context);
        init(context);
    }

    public RoundedBackgroundEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RoundedBackgroundEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public static Drawable createRoundedBackground(Context context) {
        int width = dpToPx(context, 2);
        int color = fetchAccentColor(context);
        GradientDrawable background = new GradientDrawable();
        background.setStroke(width, color);
        background.setCornerRadius(dpToPx(context, 4));
        return background;
    }

    private static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private static int fetchAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    private void init(Context context) {
        setBackgroundDrawable(createRoundedBackground(context));
    }
}
