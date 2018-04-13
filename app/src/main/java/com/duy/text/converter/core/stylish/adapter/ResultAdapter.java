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

package com.duy.text.converter.core.stylish.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duy.common.utils.DLog;
import com.duy.text.converter.R;
import com.duy.text.converter.core.clipboard.ClipboardUtil;
import com.duy.text.converter.core.utils.ShareManager;

import java.util.ArrayList;
import java.util.Collections;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {
    private static final String TAG = "StyleAdapter";
    private Context context;
    private int layout;
    private LayoutInflater inflater;
    private ArrayList<String> mItems = new ArrayList<>();
    private OnSwapStyleListener mOnSwapStyleListener;

    public ResultAdapter(Context context, @LayoutRes int layout) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.layout = layout;
    }

    public void setOnSwapStyleListener(OnSwapStyleListener onSwapStyleListener) {
        this.mOnSwapStyleListener = onSwapStyleListener;
    }

    public void add(String arg) {
        mItems.add(arg);
        notifyItemInserted(mItems.size() - 1);
    }

    public void setData(ArrayList<String> list) {
        mItems.clear();
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String str = mItems.get(position);
        holder.textView.setText(mItems.get(position));
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
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (DLog.DEBUG)
            DLog.d(TAG, "onItemMove() called with: fromPosition = [" + fromPosition + "], toPosition = [" + toPosition + "]");
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemMoved(int fromPos, int toPos) {
        if (DLog.DEBUG)
            DLog.d(TAG, "onItemMoved() called with: fromPos = [" + fromPos + "], toPos = [" + toPos + "]");
        if (mOnSwapStyleListener != null) {
            mOnSwapStyleListener.onSwap(fromPos, toPos);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View imgCopy, imgShare, shareMsg;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_result);
            imgCopy = itemView.findViewById(R.id.img_copy);
            imgShare = itemView.findViewById(R.id.btn_share);
            shareMsg = itemView.findViewById(R.id.img_share_msg);
        }
    }
}