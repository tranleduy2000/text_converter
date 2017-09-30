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

package com.duy.sharedcode.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by DUy on 07-Feb-17.
 */

public class BaseTextView extends android.support.v7.widget.AppCompatTextView {

    public BaseTextView(Context context) {
        super(context);
        setup(context);

    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);

    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context);
    }

    private void setup(Context context) {
//        AssetManager assetManager = context.getAssets();
//        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/RobotoMono-Regular.ttf");
//        setTypeface(typeface);
    }
}
