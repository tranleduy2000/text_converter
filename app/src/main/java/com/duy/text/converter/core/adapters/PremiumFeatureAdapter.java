/*
 * Copyright (c) 2018 by Tran Le Duy
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

package com.duy.text.converter.core.adapters;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.duy.text.converter.R;

import java.util.ArrayList;

public class PremiumFeatureAdapter extends RecyclerView.Adapter<PremiumFeatureAdapter.ViewHolder> {
    private ArrayList<FeatureItem> featureItems = new ArrayList<>();
    private Context context;

    public PremiumFeatureAdapter(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        featureItems.add(new FeatureItem(R.string.pro_feature_remove_ads, R.drawable.img_remove_ads));
        featureItems.add(new FeatureItem(R.string.pro_feature_encode_decode_menu, R.drawable.img_process_text_menu));
        featureItems.add(new FeatureItem(R.string.pro_feature_floating_codec, R.drawable.img_floating_codec));
        featureItems.add(new FeatureItem(R.string.pro_feature_floating_stylish, R.drawable.img_floating_stylish));
        featureItems.add(new FeatureItem(R.string.pro_feature_codec_all, R.drawable.img_codec_all));
        featureItems.add(new FeatureItem(R.string.pro_feature_codec_text_file, R.drawable.img_codec_file));
        featureItems.add(new FeatureItem(R.string.pro_feature_theme, R.drawable.img_themes));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_feature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeatureItem featureItem = featureItems.get(position);
        holder.txtTitle.setText(context.getString(featureItem.titleId));
        if (featureItem.imgId != 0) {
            Glide.with(context).load(featureItem.imgId)
                    .apply(new RequestOptions().fitCenter())
                    .into(holder.imgPreview);
        }
    }

    @Override
    public int getItemCount() {
        return featureItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        ImageView imgPreview;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            imgPreview = itemView.findViewById(R.id.img_preview);
        }
    }

    private class FeatureItem {
        @StringRes
        int titleId;
        @DrawableRes
        int imgId;

        public FeatureItem(int titleId, int imgId) {
            this.titleId = titleId;
            this.imgId = imgId;
        }
    }
}