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

package com.duy.text.converter.view;

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
