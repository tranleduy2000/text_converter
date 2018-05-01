/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    private ArrayList<Result> mResults;
    private LayoutInflater mInflater;

    //Constructor
    public ResultListAdapter(Context context, int layout, ArrayList<Result> arr) {
        super(context, layout, arr);
        mLayout = layout;
        mResults = arr;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mResults.size();
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(mLayout, parent, false);
        }
        if (mResults.get(position).getChecked()) {
            ImageView imageview = convertView.findViewById(R.id.imageView1);
            imageview.setImageResource(R.drawable.check_mark);
        } else if (mResults.get(position).getEx()) {
            ImageView imageview = convertView.findViewById(R.id.imageView1);
            imageview.setImageResource(R.drawable.red_ex);
        } else {
            ImageView imageview = convertView.findViewById(R.id.imageView1);
            imageview.setImageResource(R.drawable.question_mark);
        }
        TextView name = convertView.findViewById(R.id.textView1);
        name.setText(mResults.get(position).getResult());
        return convertView;
    }

    public void updateList(ArrayList<Result> newItems) {
        mResults = newItems;
        notifyDataSetChanged();
    }
}