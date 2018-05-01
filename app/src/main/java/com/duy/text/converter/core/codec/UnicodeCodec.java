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

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Created by Duy on 2/10/2018.
 */

public class UnicodeCodec extends CodecImpl {
    @NonNull
    @Override
    public String getName(Context context) {
        return "Unicode";
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        setMax(1);
        setConfident(0);
        try {
            String result = StringEscapeUtils.unescapeJava(text);
            incConfident();
            return result;
        } catch (Exception e) {
            return text;
        }
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        setMax(1);
        setConfident(0);
        try {
            String result = StringEscapeUtils.escapeJava(text);
            incConfident();
            return result;
        } catch (Exception e) {
            return text;
        }
    }
}
