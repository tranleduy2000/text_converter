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

package com.duy.common.ads.apps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.duy.common.R;
import com.duy.common.utils.StoreUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Duy on 26-Dec-17.
 */

public class ApplicationFragment extends Fragment {
    private static final String KEY_APP_ITEM = "KEY_APP_ITEM";

    public static ApplicationFragment newInstance(ApplicationItem applicationItem) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_APP_ITEM, applicationItem);
        ApplicationFragment fragment = new ApplicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_application, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView wallpaper = view.findViewById(R.id.img_wallpaper);
        ImageView icon = view.findViewById(R.id.img_icon);
        final ApplicationItem applicationItem = (ApplicationItem) getArguments().getSerializable(KEY_APP_ITEM);
        Glide.with(getContext()).load(applicationItem.getIconUrl()).apply(new RequestOptions().centerCrop()).into(icon);
        Glide.with(getContext()).load(applicationItem.getWallpaperUrl()).apply(new RequestOptions().centerCrop()).into(wallpaper);

        TextView txtName = view.findViewById(R.id.txt_name);
        txtName.setText(applicationItem.getName());

        view.findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String applicationId = applicationItem.getApplicationId();
                FirebaseAnalytics.getInstance(getActivity()).logEvent(applicationId, new Bundle());
                StoreUtil.gotoPlayStore(getActivity(), applicationId);
            }
        });
    }

}
