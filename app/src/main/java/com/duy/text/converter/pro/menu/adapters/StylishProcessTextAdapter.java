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

package com.duy.text.converter.pro.menu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duy.text.converter.R;
import com.duy.text.converter.pro.menu.fragments.OnTextSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class StylishProcessTextAdapter extends RecyclerView.Adapter<StylishProcessTextAdapter.ViewHolder> {
    private static final String TAG = "DecodeResultAdapter";
    private ArrayList<String> mResult = new ArrayList<>();
    private OnTextSelectedListener listener;
    private Context context;

    public StylishProcessTextAdapter(Context context) {
        this.context = context;
    }

    public void add(String item) {
        mResult.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(List<String> items) {
        mResult.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_stylish_process_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String item = mResult.get(position);
        holder.txtResult.setText(item);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onTextSelected(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    public void setListener(OnTextSelectedListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtResult;
        View rootView;

        ViewHolder(View itemView) {
            super(itemView);
            txtResult = itemView.findViewById(R.id.txt_result);
            rootView = itemView.findViewById(R.id.root_view);
        }

    }
}
