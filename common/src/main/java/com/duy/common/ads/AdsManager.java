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

package com.duy.common.ads;


import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.duy.common.BuildConfig;
import com.duy.common.R;
import com.duy.common.purchase.Premium;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

/**
 * Created by Duy on 11/10/2017.
 */

public class AdsManager {
    /**
     * Delay time for show full screen ads
     */
    public static final long DELAY_TIME = 4000; //4s
    private static final String TEST_DEVICE_ID = "D2281648CE409430157A9596175BF172";


    /**
     * Create {@link PublisherAdView} and add to container
     *
     * @param context   - android context
     * @param container - parent view for add ad view
     * @return true if ads has been added
     */
    public static boolean addBannerAds(Context context, @Nullable ViewGroup container) {
        if (isPremiumUser(context)) {
            if (container != null) {
                container.setVisibility(View.GONE);
            }
            return false;
        } else {
            if (container == null) return false;
            container.setVisibility(View.VISIBLE);
            PublisherAdView publisherAdView = new PublisherAdView(context);
            publisherAdView.setAdSizes(AdSize.SMART_BANNER, AdSize.FLUID);
            publisherAdView.setAdUnitId(AdConstants.AdUnitId.AD_UNIT_ID_NATIVE_MAIN_320_50);

            PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
            if (BuildConfig.DEBUG) builder.addTestDevice(TEST_DEVICE_ID);

            publisherAdView.loadAd(builder.build());
            container.removeAllViews();
            container.addView(publisherAdView);
        }
        return false;
    }

    @Nullable
    public static PublisherAdView addBannerAds(Context context, @Nullable ViewGroup container, AdSize... adSizes) {
        if (isPremiumUser(context)) {
            if (container != null) {
                container.setVisibility(View.GONE);
            }
            return null;
        } else {
            if (container == null) return null;
            container.setVisibility(View.VISIBLE);
            PublisherAdView publisherAdView = new PublisherAdView(context);
            publisherAdView.setAdSizes(adSizes);
            publisherAdView.setAdUnitId(AdConstants.AdUnitId.AD_UNIT_ID_NATIVE_MAIN_320_50);

            PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
            if (BuildConfig.DEBUG) builder.addTestDevice(TEST_DEVICE_ID);

            publisherAdView.loadAd(builder.build());
            container.removeAllViews();
            container.addView(publisherAdView);
            return publisherAdView;
        }
    }

    @Deprecated
    public static boolean loadBannerAds(Context context, @Nullable ViewGroup container,
                                        @Nullable View adView) {
        return loadBannerAds(context, (View) container, adView);
    }

    private static boolean isPremiumUser(Context context) {
        return Premium.isPremiumUser(context);
    }

    public static void showFullScreenAdsIfRequired(final StateActivity activity) {
        if (Premium.isPremiumUser(activity)) return;
        final InterstitialAd interstitialAd = new InterstitialAd(activity.getApplicationContext());
        interstitialAd.setAdUnitId(AdConstants.AdUnitId.AD_UNIT_ID_INTERSTITIAL);
        AdRequest.Builder request = new AdRequest.Builder();
        if (BuildConfig.DEBUG) {
            request.addTestDevice(TEST_DEVICE_ID);
        }
        interstitialAd.loadAd(request.build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (!activity.isFinishing() && activity.isActivityVisible()
                        && !Premium.isPremiumUser(activity)) {
                    interstitialAd.show();
                }
            }
        });
    }

    public static void hideBannerAds(Context context, @Nullable ViewGroup container) {
        if (container != null) {
            container.setVisibility(View.GONE);
        }
    }

    @Deprecated
    public static boolean loadBannerAds(Context context, @Nullable View container,
                                        @Nullable View adView) {
        return loadAds(context, container, adView);
    }

    public static boolean loadAds(Context context, View view) {
        View container = view.findViewById(R.id.container_ad);
        View adView = view.findViewById(R.id.ad_view);
        return container != null && adView != null && loadAds(context, container, adView);
    }

    public static boolean loadAds(Context context, View container, View adView) {
        if (isPremiumUser(context)) {
            if (container != null) {
                container.setVisibility(View.GONE);
            }
            return false;
        } else {
            if (adView instanceof AdView) {
                AdRequest.Builder builder = new AdRequest.Builder();
                if (BuildConfig.DEBUG) {
                    builder.addTestDevice(TEST_DEVICE_ID);
                }
                ((AdView) adView).loadAd(builder.build());
                return true;
            } else if (adView instanceof PublisherAdView) {
                PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
                if (BuildConfig.DEBUG) {
                    builder.addTestDevice(TEST_DEVICE_ID);
                }
                ((PublisherAdView) adView).loadAd(builder.build());
                return true;
            } else if (adView instanceof NativeExpressAdView) {
                AdRequest.Builder builder = new AdRequest.Builder();
                if (BuildConfig.DEBUG) {
                    builder.addTestDevice(TEST_DEVICE_ID);
                }
                ((NativeExpressAdView) adView).loadAd(builder.build());
                return true;
            }
        }
        return false;
    }

    /**
     * Auto detect banner ad view and load ad
     */
    public static void loadBannerAds(StateActivity activity) {
        if (activity == null) return;
        loadBannerAds(activity, activity.findViewById(R.id.container_ad), activity.findViewById(R.id.ad_view));
    }

}
