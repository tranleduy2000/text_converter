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
import android.content.IntentFilter;
import android.support.annotation.Nullable;

import com.duy.common.BuildConfig;
import com.duy.common.utils.DLog;

import aidl.util.IabBroadcastReceiver;
import aidl.util.IabHelper;
import aidl.util.IabResult;
import aidl.util.Inventory;
import aidl.util.Purchase;

import static aidl.util.IabHelper.OnIabPurchaseFinishedListener;
import static aidl.util.IabHelper.OnIabSetupFinishedListener;
import static aidl.util.IabHelper.QueryInventoryFinishedListener;

/**
 * Created by Duy on 10/5/2017.
 */

public class InAppPurchaseHelper {
    //request code for the purchase flow
    public static final int RC_REQUEST_UPGRADE = 10021;
    private static final String TAG = "InAppPurchaseHelper";
    private InAppPurchaseActivity mActivity;
    private IabBroadcastReceiver mBroadcastReceiver;
    private IabHelper mIabHelper;

    private QueryInventoryFinishedListener mGotInventoryListener = new QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            if (mIabHelper == null) return;
            if (result.isFailure()) {
                iabError(new RuntimeException("Failed to query inventory: " + result));
            }
            checkPurchase(inv);
        }
    };
    private OnIabPurchaseFinishedListener mPurchaseFinishedListener = new OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            if (mIabHelper == null) return;
            if (result.isFailure()) {
                iabError(new RuntimeException("Error purchasing: " + result));
                return;
            }
            checkPurchase(info);
        }
    };

    public InAppPurchaseHelper(InAppPurchaseActivity activity) {
        this.mActivity = activity;
    }

    public void onCreate() {
        try {
            mIabHelper = new IabHelper(mActivity, Premium.BASE64_KEY);
            mIabHelper.enableDebugLogging(BuildConfig.DEBUG);
            mIabHelper.startSetup(new OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        iabError(null);
                        return;
                    }
                    if (mIabHelper == null) return;

                    mBroadcastReceiver = new IabBroadcastReceiver(mActivity);
                    IntentFilter intentFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                    registerReceiver(mBroadcastReceiver, intentFilter);
                    if (DLog.DEBUG) DLog.d(TAG, "onIabSetupFinished: setup success");
                    try {
                        mIabHelper.queryInventoryAsync(mGotInventoryListener);
                    } catch (Exception e) {
                        iabError(e);
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void registerReceiver(IabBroadcastReceiver mBroadcastReceiver, IntentFilter intentFilter) {
        mActivity.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    public void checkPurchase(@Nullable Purchase info) {
        if (DLog.DEBUG) DLog.d(TAG, "checkPurchase() called with: info = [" + info + "]");
        if (info != null) {
            if (info.getSku().equals(Premium.SKU_PREMIUM)) {
                Premium.setPremiumUser(mActivity, true);
                mActivity.updateUi(true);
            } else {
                Premium.setPremiumUser(mActivity, false);
                mActivity.updateUi(false);
            }
        }
    }

    private void checkPurchase(@Nullable Inventory inv) {
        Purchase premiumPurchase;
        if (inv != null) {
            premiumPurchase = inv.getPurchase(Premium.SKU_PREMIUM);
            Premium.setPremiumUser(mActivity, premiumPurchase != null);
            //for testing
            if (BuildConfig.DEBUG) Premium.setPremiumUser(mActivity, false);
            mActivity.updateUi(Premium.isPremiumUser(mActivity));
        }
    }

    public void showDialogUprade() {
        if (!mActivity.isFinishing()) {
            String payload = "";
            try {
                mIabHelper.launchPurchaseFlow(mActivity, Premium.SKU_PREMIUM, RC_REQUEST_UPGRADE,
                        mPurchaseFinishedListener, payload);
            } catch (Exception e) {
                iabError(e);
            }
        }
    }

    public void receivedBroadcast() {
        try {
            mIabHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (Exception e) {
            iabError(e);
        }
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            return mIabHelper.handleActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            return false;
        }
    }

    public void onDestroy() {
        if (mBroadcastReceiver != null) unregisterReceiver(mBroadcastReceiver);
        try {
            if (mIabHelper != null) {
                mIabHelper.disposeWhenFinished();
                mIabHelper = null;
            }
        } catch (Exception e) {
            //iab unregister
        }
    }

    private void unregisterReceiver(IabBroadcastReceiver mBroadcastReceiver) {
        mActivity.unregisterReceiver(mBroadcastReceiver);
    }

    private boolean verifyDeveloperPayload(Purchase premiumPurchase) {
        String developerPayload = premiumPurchase.getDeveloperPayload();
        return true;
    }

    private void iabError(Exception e) {
        if (e != null) {
            e.printStackTrace();
        }
    }
}
