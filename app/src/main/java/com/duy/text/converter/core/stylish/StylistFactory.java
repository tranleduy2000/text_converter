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

package com.duy.text.converter.core.stylish;


import com.duy.text.converter.core.codec.ZalgoMiniCodec;
import com.duy.text.converter.core.stylish.model.BlueEffect;
import com.duy.text.converter.core.stylish.model.LeftEffect;
import com.duy.text.converter.core.stylish.model.LeftRightStyle;
import com.duy.text.converter.core.stylish.model.ReplaceEffect;
import com.duy.text.converter.core.stylish.model.RightEffect;
import com.duy.text.converter.core.stylish.model.Style;

import java.util.ArrayList;

/**
 * Created by Duy on 13-Jul-17.
 */

public class StylistFactory {
    private static ArrayList<String> createLeft() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("\u0e56\u06e3\u06dc");
        strings.add("\u2E3E");
        strings.add("\u2E3D");
        strings.add("\u2E3E\u2E3E");
        strings.add("\u2E3D\u2E3D");

        strings.add("☢");
        strings.add("☣");
        strings.add("☠");
        strings.add("⚠");
        strings.add("☤");
        strings.add("⚕");
        strings.add("⚚");
        strings.add("†");
        strings.add("☯");
        strings.add("⚖");
        strings.add("☮");
        strings.add("⚘");
        strings.add("⚔");
        strings.add("☭");
        strings.add("⚒");
        strings.add("⚓");
        strings.add("⚛");
        strings.add("⚜");
        strings.add("⚡");
        strings.add("⚶");
        strings.add("☥");
        strings.add("✠");
        strings.add("✙");
        strings.add("✞");
        strings.add("✟");
        strings.add("✧");
        strings.add("⋆");
        strings.add("★");
        strings.add("☆");
        strings.add("✪");
        strings.add("✫");
        strings.add("✬");
        strings.add("✭");
        strings.add("✮");
        strings.add("✯");
        strings.add("☸");
        strings.add("✵");
        strings.add("❂");
        strings.add("☘");
        strings.add("♡");
        strings.add("♥");
        strings.add("❤");
        strings.add("⚘");
        strings.add("❀");
        strings.add("❃");
        strings.add("❁");
        strings.add("✼");
        strings.add("☀");
        strings.add("✌");
        strings.add("♫");
        strings.add("♪");
        strings.add("☃");
        strings.add("❄");
        strings.add("❅");
        strings.add("❆");
        strings.add("☕");
        strings.add("☂");
        strings.add("❦");
        strings.add("✈");
        strings.add("♕");
        strings.add("♛");
        strings.add("♖");
        strings.add("♜");
        strings.add("☁");
        strings.add("☾");
        return strings;
    }


    private static ArrayList<Pair<String, String>> createLeftRightPair() {
        ArrayList<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("⫷", "⫸"));
        list.add(new Pair<>("╰", "╯"));
        list.add(new Pair<>("╭", "╮"));
        list.add(new Pair<>("╟", "╢"));
        list.add(new Pair<>("╚", "╝"));
        list.add(new Pair<>("╔", "╗"));
        list.add(new Pair<>("⚞", "⚟"));
        list.add(new Pair<>("⟅", "⟆"));
        list.add(new Pair<>("⟦", "⟧"));
        list.add(new Pair<>("☾", "☽"));
        list.add(new Pair<>("【", "】"));
        list.add(new Pair<>("〔", "〕"));
        list.add(new Pair<>("《", "》"));
        list.add(new Pair<>("〘", "〙"));
        list.add(new Pair<>("『", "』"));
        list.add(new Pair<>("┋", "┋"));
        list.add(new Pair<>("[\u0332\u0305", "\u0332\u0305]"));

        return list;
    }


    private static ArrayList<String> createRight() {
        ArrayList<String> list = new ArrayList<>();
        list.add("\u20e0");
        list.add("\u033e");
        list.add("\u035a");
        list.add("\u032b");
        list.add("\u030f");
        list.add("\u0352");
        list.add("\u0310");
        list.add("\u0325");
        list.add("\u0303");
        list.add("\u2665");
        list.add("\u034e");
        list.add("\u033d\u0353");
        list.add("\u031f");
        list.add("\u0359");
        list.add("\u033a");
        list.add("\u0346");
        list.add("\u033e");
        list.add("\u0333");
        list.add("\u0332");
        list.add("\u0338");
        list.add("\u0337");
        list.add("\u0334");
        list.add("\u0336");

        for (char c : ZalgoMiniCodec.ZALGO_UP) list.add(c + "");
        for (char c : ZalgoMiniCodec.ZALGO_DOWN) list.add(c + "");
        for (char c : ZalgoMiniCodec.ZALGO_MID) list.add(c + "");
        return list;
    }

    static ArrayList<Style> makeStyle() {
        ArrayList<Style> encoders = new ArrayList<>();
        //blue text style
        encoders.add(new BlueEffect());
        //replace style
        encoders.addAll(ReplaceEffect.createStyle());

        //left-right style
        ArrayList<Pair<String, String>> leftRightPair = createLeftRightPair();
        for (Pair<String, String> pair : leftRightPair) {
            encoders.add(new LeftRightStyle(pair.first, pair.second));
        }
        //left style
        ArrayList<String> lefts = createLeft();
        for (String s : lefts) encoders.add(new LeftEffect(s));
        //right style
        ArrayList<String> rights = createRight();
        for (String s : rights) encoders.add(new RightEffect(s));
        return encoders;
    }

    /**
     * Created by Duy on 13-Jul-17.
     */

    private static class Pair<F, S> {
        public F first;
        public S second;

        Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}
