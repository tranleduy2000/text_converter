package teach.duy.com.texttool;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Duy on 05-May-17.
 */

public class Settings {
    private static final String KEY = "text_data";

    public static void setTextData(Context context, String text) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(KEY, text).apply();
    }

    public static String getTextData(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(KEY, "");
    }
}
