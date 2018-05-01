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

package com.duy.text.converter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.duy.text.converter.R;
import com.duy.text.converter.activities.base.BaseActivity;
import com.duy.text.converter.pro.fragment.CodecFileFragment;

/**
 * Created by Duy on 2/17/2018.
 */

public class CodecFileActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codec_file);
        setupToolbar();
        setTitle(R.string.tab_title_base);

        CodecFileFragment codecFileFragment
                = (CodecFileFragment) getSupportFragmentManager().findFragmentByTag(CodecFileFragment.class.getName());
        if (codecFileFragment == null) {
            codecFileFragment = CodecFileFragment.newInstance();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, codecFileFragment, CodecFileFragment.class.getName());
        fragmentTransaction.commit();
    }
}
