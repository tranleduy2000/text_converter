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

import java.util.ArrayList;

/**
 * Created by Duy on 11/21/2017.
 */
public class NatoCodec extends CodecImpl {
    private static final ArrayList<NatoItem> DATA = new ArrayList<>();

    static {
        DATA.add(new NatoItem("A", "Alpha"));
        DATA.add(new NatoItem("B", "Bravo"));
        DATA.add(new NatoItem("C", "Charlie"));
        DATA.add(new NatoItem("D", "Delta"));
        DATA.add(new NatoItem("E", "Echo"));
        DATA.add(new NatoItem("F", "Foxtrot"));
        DATA.add(new NatoItem("G", "Golf"));
        DATA.add(new NatoItem("H", "Hotel"));
        DATA.add(new NatoItem("I", "India"));
        DATA.add(new NatoItem("J", "Juliet"));
        DATA.add(new NatoItem("K", "Kilo"));
        DATA.add(new NatoItem("L", "Lima"));
        DATA.add(new NatoItem("M", "Mike"));
        DATA.add(new NatoItem("N", "November"));
        DATA.add(new NatoItem("O", "Oscar"));
        DATA.add(new NatoItem("P", "Papa"));
        DATA.add(new NatoItem("Q", "Quebec"));
        DATA.add(new NatoItem("R", "Romeo"));
        DATA.add(new NatoItem("S", "Sierra"));
        DATA.add(new NatoItem("T", "Tango"));
        DATA.add(new NatoItem("U", "Uniform"));
        DATA.add(new NatoItem("V", "Victor"));
        DATA.add(new NatoItem("W", "Whiskey"));
        DATA.add(new NatoItem("X", "X-ray"));
        DATA.add(new NatoItem("Y", "Yankee"));
        DATA.add(new NatoItem("Z", "Zulu"));
        DATA.add(new NatoItem("0", "Zero"));
        DATA.add(new NatoItem("1", "One"));
        DATA.add(new NatoItem("2", "Two"));
        DATA.add(new NatoItem("3", "Three"));
        DATA.add(new NatoItem("4", "Four"));
        DATA.add(new NatoItem("5", "Five"));
        DATA.add(new NatoItem("6", "Six"));
        DATA.add(new NatoItem("7", "Seven"));
        DATA.add(new NatoItem("8", "Eight"));
        DATA.add(new NatoItem("9", "Nine"));
        DATA.add(new NatoItem("-", "Dash"));
        DATA.add(new NatoItem(".", "Period"));

        for (int i = 0, dataSize = DATA.size(); i < dataSize; i++) {
            DATA.add(DATA.get(i).makeReverser());
        }
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        StringBuilder result = new StringBuilder();
        String[] args = text.split("\\s+");
        setMax(args);
        for (String arg : args) {
            String value = get(String.valueOf(arg));
            if (value != null) {
                incConfident();
                result.append(value);
            } else {
                result.append(arg);
            }
        }
        return result.toString();
    }

    private String get(String key) {
        for (NatoItem item : DATA) {
            if (item.equals(key)) {
                return item.getValue();
            }
        }
        return null;
    }

    @NonNull
    @Override
    public String getName(Context context) {
        return "Nato";
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        StringBuilder result = new StringBuilder();
        setMax(text.length());
        for (int i = 0; i < text.length(); i++) {
            String value = get(String.valueOf(text.charAt(i)));
            if (value != null) {
                result.append(value);
                incConfident();
            } else {
                result.append(text.charAt(i));
            }
            if (i != text.length() - 1) result.append(" ");
        }
        return result.toString();
    }


    static class NatoItem {
        private String source;
        private String value;

        NatoItem(String source, String value) {
            this.source = source;
            this.value = value;
        }

        String getSource() {
            return source;
        }

        String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof NatoItem) {
                return ((NatoItem) obj).getSource().equalsIgnoreCase(this.getSource());
            }
            if (obj instanceof String) {
                return getSource().equalsIgnoreCase((String) obj);
            }
            return super.equals(obj);
        }

        NatoItem makeReverser() {
            return new NatoItem(value, source);
        }

        @Override
        public String toString() {
            return "NatoItem{" +
                    "source='" + source + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

}
