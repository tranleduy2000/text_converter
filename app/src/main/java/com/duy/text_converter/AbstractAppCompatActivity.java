package com.duy.text_converter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.kobakei.ratethisapp.RateThisApp;

import java.util.Locale;

import teach.duy.com.texttool.BuildConfig;


/**
 * abstract theme for app
 * <p>
 * auto set theme when user changed theme
 * <p>
 * Created by Duy on 19/7/2016
 */
public abstract class AbstractAppCompatActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = "MainActivity";
    public static final String APP_ID = BuildConfig.APPLICATION_ID;
    protected SharedPreferences mPreferences;

    /**
     * set theme and init mHistoryDatabase for history
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setLocale(false);

        //set theme for app
        setTheme(false);
    }

    /**
     * set language
     *
     * @param create
     */

    private void setLocale(boolean create) {
        Locale locale = new Locale("en");
//        String code = mPreferences.getString(getString(R.string.key_pref_lang), "default_lang");
//        if (code.equals("default_lang")) {
//            Log.d(TAG, "setLocale: default");
//            locale = Locale.getDefault();
//        } else {
//            locale = new Locale(code);
//        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        if (create) recreate();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPreferences != null)
            mPreferences.registerOnSharedPreferenceChangeListener(this);
        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
        RateThisApp.setCallback(new RateThisApp.Callback() {
            @Override
            public void onYesClicked() {
                rateApp(null);
            }

            @Override
            public void onNoClicked() {
            }

            @Override
            public void onCancelClicked() {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * set theme for app
     *
     * @param recreate -call method onCreate
     */
    protected void setTheme(boolean recreate) {
//        String name = mPreferences.getString(getResources().getString(R.string.key_pref_theme), "");
//        ThemeEngine themeEngine = new ThemeEngine(getApplicationContext());
//        int themeId = themeEngine.getTheme(name);
//        if (themeId != ThemeEngine.THEME_NOT_FOUND) {
//            super.setTheme(themeId);
//            if (recreate) recreate();
//            Log.d(TAG, "Set theme ok");
//        } else {
//            Log.d(TAG, "Theme not found");
//        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
//        if (s.equals(getResources().getString(R.string.key_pref_theme))) {
//            setTheme(true);
//        } else if (s.equals(getString(R.string.key_pref_lang))) {
//            setLocale(true);
//            Toast.makeText(this, getString(R.string.change_lang_msg), Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreferences != null)
            mPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }


    /**
     * share app
     */
    public void shareApp(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + APP_ID);
        intent.setType("text/plain");
        startActivity(intent);
    }

    /**
     * show dialog with title and messenger
     *
     * @param title - title
     * @param msg   - messenger
     */
    protected void showDialog(String title, String msg) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title).setMessage(msg);
//        builder.setNegativeButton(this.getString(R.string.close), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        builder.create().show();
    }

    /**
     * show dialog with title and messenger
     *
     * @param msg - messenger
     */
    protected void showDialog(String msg) {
        this.showDialog("", msg);
    }


    public void rateApp(View view) {
        Uri uri = Uri.parse("market://details?id=" + APP_ID);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |

                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + APP_ID)));
        }
    }

    public void moreApp(View view) {
        Uri uri = Uri.parse("market://search?q=pub:Trần Lê Duy");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |

                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/search?q=pub:Trần Lê Duy")));
        }
    }

    protected void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


}
