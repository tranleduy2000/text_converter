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
    private static final int[] THEME_IDS = new int[]{
            R.style.AppThemeDark,
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
        boolean canUseAdvancedFeature = Premium.isPremium(context);
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
