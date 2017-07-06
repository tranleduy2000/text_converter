/*
 * Copyright (c) 2017 by Tran Le Duy
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

package com.duy.text_converter.utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Duy on 06-Jul-17.
 */

public class ArrayEffectTool {
    private static final String NORMAL;
    private static final String[][] EFFECTS;

    static {
        NORMAL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] array = new String[]{
                "a̶b̶c̶d̶e̶f̶g̶h̶i̶j̶k̶l̶m̶n̶o̶p̶q̶r̶s̶t̶u̶v̶w̶x̶y̶z̶a̶b̶c̶d̶e̶f̶g̶h̶i̶j̶k̶l̶m̶n̶o̶p̶q̶r̶s̶t̶u̶v̶w̶x̶y̶z̶",
                "a̴b̴c̴d̴e̴f̴g̴h̴i̴j̴k̴l̴m̴n̴o̴p̴q̴r̴s̴t̴u̴v̴w̴x̴y̴z̴a̴b̴c̴d̴e̴f̴g̴h̴i̴j̴k̴l̴m̴n̴o̴p̴q̴r̴s̴t̴u̴v̴w̴x̴y̴z̴",
                "a̷b̷c̷d̷e̷f̷g̷h̷i̷j̷k̷l̷m̷n̷o̷p̷q̷r̷s̷t̷u̷v̷w̷x̷y̷z̷a̷b̷c̷d̷e̷f̷g̷h̷i̷j̷k̷l̷m̷n̷o̷p̷q̷r̷s̷t̷u̷v̷w̷x̷y̷z̷",
                "a̲b̲c̲d̲e̲f̲g̲h̲i̲j̲k̲l̲m̲n̲o̲p̲q̲r̲s̲t̲u̲v̲w̲x̲y̲z̲a̲b̲c̲d̲e̲f̲g̲h̲i̲j̲k̲l̲m̲n̲o̲p̲q̲r̲s̲t̲u̲v̲w̲x̲y̲z̲",
                "a̳b̳c̳d̳e̳f̳g̳h̳i̳j̳k̳l̳m̳n̳o̳p̳q̳r̳s̳t̳u̳v̳w̳x̳y̳z̳a̳b̳c̳d̳e̳f̳g̳h̳i̳j̳k̳l̳m̳n̳o̳p̳q̳r̳s̳t̳u̳v̳w̳x̳y̳z̳",
                "[̲̅a̲̅][̲̅b̲̅][̲̅c̲̅][̲̅d̲̅][̲̅e̲̅][̲̅f̲̅][̲̅g̲̅][̲̅h̲̅][̲̅i̲̅][̲̅j̲̅][̲̅k̲̅][̲̅l̲̅][̲̅m̲̅][̲̅n̲̅][̲̅o̲̅][̲̅p̲̅][̲̅q̲̅][̲̅r̲̅][̲̅s̲̅][̲̅t̲̅][̲̅u̲̅][̲̅v̲̅][̲̅w̲̅][̲̅x̲̅][̲̅y̲̅][̲̅z̲̅][̲̅a̲̅][̲̅b̲̅][̲̅c̲̅][̲̅d̲̅][̲̅e̲̅][̲̅f̲̅][̲̅g̲̅][̲̅h̲̅][̲̅i̲̅][̲̅j̲̅][̲̅k̲̅][̲̅l̲̅][̲̅m̲̅][̲̅n̲̅][̲̅o̲̅][̲̅p̲̅][̲̅q̲̅][̲̅r̲̅][̲̅s̲̅][̲̅t̲̅][̲̅u̲̅][̲̅v̲̅][̲̅w̲̅][̲̅x̲̅][̲̅y̲̅][̲̅z̲̅]",
                "a̾b̾c̾d̾e̾f̾g̾h̾i̾j̾k̾l̾m̾n̾o̾p̾q̾r̾s̾t̾u̾v̾w̾x̾y̾z̾a̾b̾c̾d̾e̾f̾g̾h̾i̾j̾k̾l̾m̾n̾o̾p̾q̾r̾s̾t̾u̾v̾w̾x̾y̾z̾",
                "ḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒ",
                "a͆b͆c͆d͆e͆f͆g͆h͆i͆j͆k͆l͆m͆n͆o͆p͆q͆r͆s͆t͆u͆v͆w͆x͆y͆z͆a͆b͆c͆d͆e͆f͆g͆h͆i͆j͆k͆l͆m͆n͆o͆p͆q͆r͆s͆t͆u͆v͆w͆x͆y͆z͆",
                "a̺b̺c̺d̺e̺f̺g̺h̺i̺j̺k̺l̺m̺n̺o̺p̺q̺r̺s̺t̺u̺v̺w̺x̺y̺z̺a̺b̺c̺d̺e̺f̺g̺h̺i̺j̺k̺l̺m̺n̺o̺p̺q̺r̺s̺t̺u̺v̺w̺x̺y̺z̺",
                "a͙b͙c͙d͙e͙f͙g͙h͙i͙j͙k͙l͙m͙n͙o͙p͙q͙r͙s͙t͙u͙v͙w͙x͙y͙z͙a͙b͙c͙d͙e͙f͙g͙h͙i͙j͙k͙l͙m͙n͙o͙p͙q͙r͙s͙t͙u͙v͙w͙x͙y͙z͙",
                "a̟b̟c̟d̟e̟f̟g̟h̟i̟j̟k̟l̟m̟n̟o̟p̟q̟r̟s̟t̟u̟v̟w̟x̟y̟z̟a̟b̟c̟d̟e̟f̟g̟h̟i̟j̟k̟l̟m̟n̟o̟p̟q̟r̟s̟t̟u̟v̟w̟x̟y̟z̟",
                "a͓̽b͓̽c͓̽d͓̽e͓̽f͓̽g͓̽h͓̽i͓̽j͓̽k͓̽l͓̽m͓̽n͓̽o͓̽p͓̽q͓̽r͓̽s͓̽t͓̽u͓̽v͓̽w͓̽x͓̽y͓̽z͓̽a͓̽b͓̽c͓̽d͓̽e͓̽f͓̽g͓̽h͓̽i͓̽j͓̽k͓̽l͓̽m͓̽n͓̽o͓̽p͓̽q͓̽r͓̽s͓̽t͓̽u͓̽v͓̽w͓̽x͓̽y͓̽z͓̽",
                "a͎b͎c͎d͎e͎f͎g͎h͎i͎j͎k͎l͎m͎n͎o͎p͎q͎r͎s͎t͎u͎v͎w͎x͎y͎z͎a͎b͎c͎d͎e͎f͎g͎h͎i͎j͎k͎l͎m͎n͎o͎p͎q͎r͎s͎t͎u͎v͎w͎x͎y͎z͎",
                "a♥b♥c♥d♥e♥f♥g♥h♥i♥j♥k♥l♥m♥n♥o♥p♥q♥r♥s♥t♥u♥v♥w♥x♥y♥z♥a♥b♥c♥d♥e♥f♥g♥h♥i♥j♥k♥l♥m♥n♥o♥p♥q♥r♥s♥t♥u♥v♥w♥x♥y♥z♥",
                "『a』『b』『c』『d』『e』『f』『g』『h』『i』『j』『k』『l』『m』『n』『o』『p』『q』『r』『s』『t』『u』『v』『w』『x』『y』『z』『a』『b』『c』『d』『e』『f』『g』『h』『i』『j』『k』『l』『m』『n』『o』『p』『q』『r』『s』『t』『u』『v』『w』『x』『y』『z』"};

        EFFECTS = new String[array.length][array[0].length()];
        int length = NORMAL.length();
        for (int i = 0; i < EFFECTS.length; i++) {
            String[] entry = EFFECTS[i];
            int step = array[i].length() / length;
            System.out.println(step + " " + NORMAL.length());
            for (int j = 0; j < length; j++) {
                entry[j] = array[i].substring(j * step, (j + 1) * step);
            }
        }
    }

    public static StringBuilder convert(String text, String[] data) {
        StringBuilder result = new StringBuilder();
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int index = NORMAL.indexOf(letter);
            try {
                result.append((index != -1) ? data[index] : letter);
            } catch (Exception e) {
                result.append(letter);
            }
        }
        return result;
    }

    public static ArrayList<String> convert(String text) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String[] effect : EFFECTS) {
            arrayList.add(convert(text, effect).toString());
        }
        return arrayList;
    }


    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(EFFECTS));
    }
}
