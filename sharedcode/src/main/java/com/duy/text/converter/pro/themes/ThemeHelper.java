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

package com.duy.text.converter.pro.themes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;

import com.duy.text.converter.R;
import com.duy.text.converter.pro.license.Premium;

public class ThemeHelper {
    public static final int[] THEME_IDS = new int[]{
            R.style.AppThemeDark,
//            R.style.AppThemeDark_Theme1,
//            R.style.AppThemeDark_Theme2,
//            R.style.AppThemeDark_Theme3,
//            R.style.AppThemeDark_Theme4,
//            R.style.AppThemeDark_Theme5,
//            R.style.AppThemeDark_Theme6,

            R.style.AppThemeLight_Theme1,
            R.style.AppThemeLight_Theme2,
            R.style.AppThemeLight_Theme3,
            R.style.AppThemeLight_Theme4,
            R.style.AppThemeLight_Theme5,
            R.style.AppThemeLight_Theme6
    };


    public static void setTheme(Context context) {
        String name = getCurrentTheme(context);
        context.setTheme(getThemeByName(name, context));
    }

    public static int getThemeByName(String name, Context context) {
        boolean canUseAdvancedFeature = !Premium.isCrack(context);
        if (!canUseAdvancedFeature) return R.style.AppThemeDark;
        String[] themes = context.getResources().getStringArray(R.array.theme_names);
        for (int i = 0; i < themes.length; i++) {
            if (name.equalsIgnoreCase(themes[i])) {
                return THEME_IDS[i];
            }
        }
        return R.style.AppThemeDark;
    }


    public static String getCurrentTheme(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(context.getString(R.string.pref_key_theme), "");
    }

    public static int getCurrentThemePosition(Context context) {
        int res = getThemeByName(getCurrentTheme(context), context);
        for (int i = 0; i < THEME_IDS.length; i++) {
            if (res == THEME_IDS[i]) {
                return i;
            }
        }
        return 0;
    }

    @NonNull
    public static LayoutInflater wrap(@NonNull Context context) {
        String currentTheme = ThemeHelper.getCurrentTheme(context);
        int themeByName = ThemeHelper.getThemeByName(currentTheme, context);
        if (themeByName != -1) {
            ContextThemeWrapper newContext = new ContextThemeWrapper(context, themeByName);
            return LayoutInflater.from(newContext);
        }
        return LayoutInflater.from(context);
    }
}
