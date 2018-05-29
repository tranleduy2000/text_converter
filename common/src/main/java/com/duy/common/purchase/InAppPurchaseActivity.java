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

package com.duy.common.purchase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.duy.common.ads.StateActivity;
import com.duy.common.utils.DLog;
import com.google.firebase.analytics.FirebaseAnalytics;

import aidl.util.IabBroadcastReceiver;


/**
 * Created by Duy on 9/14/2017.
 */
public abstract class InAppPurchaseActivity extends StateActivity implements IabBroadcastReceiver.IabBroadcastListener {
    public static final int REQUEST_CODE_UPGRADE = 6032;
    private static final String TAG = "InAppPurchaseActivity";
    private final Handler mHandler = new Handler();
    private FirebaseAnalytics mFirebaseAnalytics;
    private InAppPurchaseHelper mInAppPurchaseHelper;

    @CallSuper
    @Deprecated
    public void updateUiPremium() {
        if (DLog.DEBUG) DLog.d(TAG, "updateUiPremium() called");
        throw new RuntimeException("Deprecated method, use updateUi(boolean premium)");
    }

    public void updateUi(boolean premium) {
        if (DLog.DEBUG) DLog.d(TAG, "updateUi() called with: premium = [" + premium + "]");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mInAppPurchaseHelper = new InAppPurchaseHelper(this);
        mInAppPurchaseHelper.onCreate();
    }

    public void clickUpgrade() {
        mFirebaseAnalytics.logEvent("click_upgrade", new Bundle());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mInAppPurchaseHelper.showDialogUprade();
            }
        }, 50);
    }

    @Override
    public void receivedBroadcast() {
        mInAppPurchaseHelper.receivedBroadcast();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean handled = mInAppPurchaseHelper.onActivityResult(requestCode, resultCode, data);
        if (!handled) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        mInAppPurchaseHelper.onDestroy();
        super.onDestroy();
    }

    public void showDialogUpgrade() {
        mInAppPurchaseHelper.showDialogUprade();
    }

}
