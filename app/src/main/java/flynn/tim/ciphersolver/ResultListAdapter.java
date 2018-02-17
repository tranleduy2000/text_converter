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

package flynn.tim.ciphersolver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duy.text.converter.R;

import java.util.ArrayList;

/**
 * Created by Tim on 3/4/2015.
 */

public class ResultListAdapter extends ArrayAdapter<Result> {

    private int mLayout;
    private ArrayList<Result> mArr;
    private LayoutInflater mInflater;

    //Constructor
    public ResultListAdapter(Context context, int layout, ArrayList<Result> arr) {
        super(context, layout, arr);
        mLayout = layout;
        mArr = arr;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mArr.size();
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(mLayout, parent, false);
        }

        if (mArr.get(position).getChecked()) {
            ImageView imageview = convertView.findViewById(R.id.imageView1);
            imageview.setImageResource(R.drawable.check_mark);
        } else if (mArr.get(position).getEx()) {
            ImageView imageview = convertView.findViewById(R.id.imageView1);
            imageview.setImageResource(R.drawable.red_ex);
        } else {
            ImageView imageview = convertView.findViewById(R.id.imageView1);
            imageview.setImageResource(R.drawable.question_mark);
        }

        //Set the text view
        TextView name = convertView.findViewById(R.id.textView1);
        name.setText(mArr.get(position).getResult());
        //desc.setText("UUID: " + mArr.get(position).getString("UUID"));

        //Return the view
        return convertView;
    }

    public void updateList(ArrayList<Result> newItems) {
        mArr = newItems;
        notifyDataSetChanged();
    }
}