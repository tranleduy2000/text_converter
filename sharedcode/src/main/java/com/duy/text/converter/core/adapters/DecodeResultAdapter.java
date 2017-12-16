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

package com.duy.text.converter.core.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.duy.text.converter.R;

import java.util.ArrayList;

public class DecodeResultAdapter extends RecyclerView.Adapter<DecodeResultAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<DecodeItem> mDecodeItems = new ArrayList<>();

    public DecodeResultAdapter(Context context) {
        this.context = context;
    }

    public void add(DecodeItem decodeItem) {
        mDecodeItems.add(decodeItem);
        notifyItemInserted(getItemCount() - 1);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_decode_all,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecodeItem item = mDecodeItems.get(position);
        holder.txtResult.setText(item.getResult());
        holder.txtTitle.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return mDecodeItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtResult, txtTitle;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            progressBar = itemView.findViewById(R.id.progress_bar);
            txtResult = itemView.findViewById(R.id.txt_result);
            txtTitle = itemView.findViewById(R.id.txt_name);
        }

    }

    public static class DecodeItem {
        String name;
        String result;
        int max, confident;

        public DecodeItem(String name, String result) {
            this.name = name;
            this.result = result;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getConfident() {
            return confident;
        }

        public void setConfident(int confident) {
            this.confident = confident;
        }

        public String getResult() {
            return result;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
