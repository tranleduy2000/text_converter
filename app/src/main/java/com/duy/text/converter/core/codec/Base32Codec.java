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

import org.apache.commons.codec.binary.Base32;

/**
 * Created by Duy on 11-Jul-17.
 */

public class Base32Codec extends CodecImpl {
    private Base32 base32 = new Base32();

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        setMax(1);
        try {
            String result = new String(base32.encode(text.getBytes()));
            setConfident(1);
            return result;
        } catch (Exception e) {
            setConfident(0);
            return text;
        }
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        setMax(1);
        try {
            setConfident(1);
            return new String(base32.decode(text.getBytes()));
        } catch (Exception e) {
            setConfident(0);
            return text;
        }
    }

    @NonNull
    @Override
    public String getName(Context context) {
        return "Base 32";
    }
}
