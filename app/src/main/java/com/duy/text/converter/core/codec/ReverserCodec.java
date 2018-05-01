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

package com.duy.text.converter.core.codec;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duy.text.converter.core.codec.interfaces.CodecImpl;

/**
 * Created by DUy on 06-Feb-17.
 */

public class ReverserCodec extends CodecImpl {

    /**
     * reverse Text
     * abc def -> fed cba
     */
    private String reverseText(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        setMax(1);
        setConfident(1);
        return reverseText(text);
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        setMax(1);
        setConfident(1);
        return reverseText(text);
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "Reverser";
    }
}
