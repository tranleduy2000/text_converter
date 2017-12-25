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

import com.duy.common.DLog;
import com.duy.text.converter.R;
import com.duy.text.converter.core.utils.ClipboardUtil;
import com.duy.text.converter.core.utils.ShareManager;

import java.util.ArrayList;
import java.util.Locale;

public class DecodeResultAdapter extends RecyclerView.Adapter<DecodeResultAdapter.ViewHolder> {
    private static final String TAG = "DecodeResultAdapter";
    private final Context context;
    private boolean processText;
    private ArrayList<DecodeItem> mDecodeItems = new ArrayList<>();
    private OnTextSelectedListener listener;

    public DecodeResultAdapter(Context context, boolean processText) {
        this.context = context;
        this.processText = processText;
    }

    public void add(DecodeItem decodeItem) {
        mDecodeItems.add(decodeItem);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resource = processText ? R.layout.list_item_decode_all_process_text : R.layout.list_item_decode_all;
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecodeItem item = mDecodeItems.get(position);
        holder.txtResult.setText(item.getResult());
        holder.txtTitle.setText(item.getName());
        if (item.getMax() > 0) {
            DLog.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
            holder.progressBar.setMax(item.getMax());
            holder.progressBar.setProgress(item.getConfident());

            int percent = (int) ((float) item.getConfident() / (float) item.getMax() * 100f);
            holder.txtTitle.append(String.format(Locale.US, " %d%%", percent));
        }

        final String str = item.getResult();
        if (holder.imgShare != null) {
            holder.imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareManager.share(str, context);
                }
            });
        }
        if (holder.imgCopy != null) {
            holder.imgCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardUtil.setClipboard(context, str);
                }
            });
        }
        if (holder.shareMsg != null) {
            holder.shareMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareManager.shareMessenger(str, context);
                }
            });
        }
        if (holder.imgSelect != null) {
            holder.imgSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onTextSelected(str);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDecodeItems.size();
    }

    public void setListener(OnTextSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnTextSelectedListener {
        void onTextSelected(String text);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtResult, txtTitle;
        ProgressBar progressBar;
        View imgCopy, imgShare, shareMsg, imgSelect;

        ViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            progressBar = itemView.findViewById(R.id.progress_bar);
            txtResult = itemView.findViewById(R.id.txt_result);
            txtTitle = itemView.findViewById(R.id.txt_name);
            imgCopy = itemView.findViewById(R.id.img_copy);
            imgShare = itemView.findViewById(R.id.img_share);
            shareMsg = itemView.findViewById(R.id.img_share_msg);
            imgSelect = itemView.findViewById(R.id.img_select);
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
