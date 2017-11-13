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

package com.duy.common.services.ads;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

public class AdsManager {

    public static void loadBannerAds(Context context, @Nullable View container, @Nullable View adView) {
        if (!isFree(context)) {
            if (container != null) {
                container.setVisibility(View.GONE);
            }
        } else if (shouldDisplayAds(context)) {
            if (adView instanceof AdView) {
                ((AdView) adView).loadAd(new Builder()
                        .addTestDevice("D2281648CE409430157A9596175BF172").build());
            } else if (adView instanceof PublisherAdView) {
                ((PublisherAdView) adView).loadAd(new PublisherAdRequest.Builder()
                        .addTestDevice("D2281648CE409430157A9596175BF172").build());
            } else if (adView instanceof NativeExpressAdView) {
                ((NativeExpressAdView) adView).loadAd(new Builder()
                        .addTestDevice("D2281648CE409430157A9596175BF172").build());
            }
        }
    }

    private static boolean isFree(Context context) {
        return !context.getPackageName().equalsIgnoreCase("com.duy.text_converter.pro");
    }

    private static boolean shouldDisplayAds(Context context) {
        return true;
    }
}
