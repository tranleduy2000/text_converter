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

package com.duy.text.converter.core.codec.interfaces;

import android.content.Context;

/**
 * Created by Duy on 16-Dec-17.
 */

public abstract class CodecImpl implements Codec {

    protected int max = 0;
    protected int confident = 0;

    @Override
    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    protected void setMax(char[] arr) {
        setMax(arr.length);
    }

    protected void setMax(Object[] arr) {
        setMax(arr.length);
    }

    @Override
    public int getConfident() {
        return confident;
    }

    public void setConfident(int confident) {
        this.confident = confident;
    }

    public void incConfident() {
        confident++;
    }


}
