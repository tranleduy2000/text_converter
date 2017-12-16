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

package com.duy.text.converter.core.fragments;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.duy.common.StoreUtil;
import com.duy.text.converter.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;


/**
 * Created by Duy on 07-Jun-17.
 */

public class AdsFragment extends Fragment {
    public static final int INDEX = 1;
    private static final String TAG = "AdsFragment";
    private NativeExpressAdView mAdView;

    public static AdsFragment newInstance() {

        Bundle args = new Bundle();

        AdsFragment fragment = new AdsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ads, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_pro_version).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreUtil.gotoPlayStore(getActivity(), "com.duy.text_converter.pro");
            }
        });
        loadAd(view);
    }

    private void loadAd(View view) {
        final ViewGroup containerAd = view.findViewById(R.id.container_ad);
        containerAd.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    containerAd.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    containerAd.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                mAdView = new NativeExpressAdView(getContext());
                mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                mAdView.setAdUnitId("ca-app-pub-9351804859208340/4981122038");

                containerAd.removeAllViews();
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                containerAd.addView(mAdView, params);

                AdRequest.Builder builder = new AdRequest.Builder().addTestDevice("D2281648CE409430157A9596175BF172");
                mAdView.loadAd(builder.build());
            }
        });
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public float convertPixelsToDp(float px) {
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroyView() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroyView();
    }
}
