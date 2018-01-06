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

package com.duy.text.converter.core.stylish.model;

import com.duy.text.converter.core.stylish.Style;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Duy on 07-Jun-17.
 */

public class ReplaceEffect implements Style {
    /**
     * original text
     */
    public static final String NORMAL;
    public static final int MAX_LENGTH;
    public static final ArrayList<String> STYLES;
    private static final String CIRCLE = "\u24d0\u24d1\u24d2\u24d3\u24d4\u24d5\u24d6\u24d7\u24d8\u24d9" +
            "\u24da\u24db\u24dc\u24dd\u24de\u24df\u24e0\u24e1\u24e2\u24e3\u24e4\u24e5\u24e6\u24e7\u24e8" +
            "\u24e9_,;\u2a00?!\u2298\u29b8'\u24b6\u24b7\u24b8\u24b9" +
            "\u24ba\u24bb\u24bc\u24bd\u24be\u24bf\u24c0\u24c1\u24c2\u24c3\u24c4\u24c5\u24c6\u24c7\u24c8" +
            "\u24c9\u24ca\u24cb\u24cc\u24cd\u24ce\u24cf0\u2460\u2461\u2462\u2463\u2464\u2465\u2466\u2467\u2468";
    private static final String FULL_WIDTH = "\uff41\uff42\uff43\uff44\uff45\uff46\uff47\uff48" +
            "\uff49\uff4a\uff4b\uff4c\uff4d\uff4e\uff4f\uff50\uff51\uff52\uff53\uff54\uff55" +
            "\uff56\uff57\uff58\uff59\uff5a_\uff0c\uff1b\uff0e\uff1f\uff01\uff0f\\\uff07\uff21" +
            "\uff22\uff23\uff24\uff25\uff26\uff27\uff28\uff29\uff2a\uff2b\uff2c\uff2d\uff2e\uff2f" +
            "\uff30\uff31\uff32\uff33\uff34\uff35\uff36\uff37\uff38\uff39\uff3a\uff10\uff11\uff12" +
            "\uff13\uff14\uff15\uff16\uff17\uff18\uff19";
    private static final String A_CUTE = "\u00e1b\u0107d\u00e9f\u01f5h\u00edj\u1e31\u013a\u1e3f\u0144" +
            "\u0151\u1e55q\u0155\u015bt\u00fav\u1e83x\u04f3\u017a_,;.?!/\\'\u00c1B\u0106D\u00c9F\u01f4" +
            "H\u00edJ\u1e30\u0139\u1e3e\u0143\u0150\u1e54Q\u0154\u015bT\u0170V\u1e82X\u04f2\u01790123456789";
    private static final String CURVY_1 = "\u0e04\u0e52\u0188\u0257\ufec9\u093f\ufeed\u0266" +
            "\u0671\ufedd\u16d5\u026d\u0e53\u0e01\u047b\u03c1\u06f9\u027c\u0e23\u0547\u0aaa" +
            "\u06f7\u0e1d\u0e0b\u05e5\u0579_,;\u0701?!/\\'\u0e04\u0e52\u0188\u0257\ufec9\u093f" +
            "\ufeed\u0266\u0671\ufedd\u16d5\u026d\u0e53\u0e01\u047b\u03c1\u06f9\u027c\u0e23" +
            "\u0547\u0aaa\u06f7\u0e1d\u0e0b\u05e5\u05790123456789";
    private static final String CURVY_2 = "\u03b1\u0432\u00a2\u2202\u0454\u0192\ufeed\u043d\u03b9\u05e0" +
            "\u043a\u2113\u043c\u03b7\u03c3\u03c1\u06f9\u044f\u0455\u0442\u03c5\u03bd\u03c9\u03c7\u0443" +
            "\u0579_,;.?!/\\'\u03b1\u0432\u00a2\u2202\u0454\u0192\ufeed\u043d\u03b9\u05e0\u043a\u2113" +
            "\u043c\u03b7\u03c3\u03c1\u06f9\u044f\u0455\u0442\u03c5\u03bd\u03c9\u03c7\u0443\u05790123456789";
    private static final String CURVY_3
            = "ค๒ς๔єŦﻮђเןкɭ๓ภ๏קợгรՇยשฬאץչ_,;.?!/\\'ค๒ς๔єŦﻮђเןкɭ๓ภ๏קợгรՇยשฬאץչ0123456789";
    private static final String ROCK_DOT
            = "äḅċḋëḟġḧïjḳḷṁṅöṗqṛṡẗüṿẅẍÿż_,;.?!/\\'ÄḄĊḊЁḞĠḦЇJḲḶṀṄÖṖQṚṠṪÜṾẄẌŸŻ012ӟ456789";
    private static final String STROKE
            = "Ⱥƀȼđɇfǥħɨɉꝁłmnøᵽꝗɍsŧᵾvwxɏƶ_,;.?!/\\'ȺɃȻĐɆFǤĦƗɈꝀŁMNØⱣꝖɌSŦᵾVWXɎƵ01ƻ3456789";
    private static final String SUPPER_SCRIPT
            = "ᵃᵇᶜᵈᵉᶠᵍʰⁱʲᵏˡᵐⁿᵒᵖqʳˢᵗᵘᵛʷˣʸᶻ_,;.?!/\\'ᴬᴮᶜᴰᴱᶠᴳᴴᴵᴶᴷᴸᴹᴺᴼᴾQᴿˢᵀᵁⱽᵂˣʸᶻ⁰¹²³⁴⁵⁶⁷⁸⁹";
    private static final String SUB_SCRIPT
            = "ₐbcdₑfgₕᵢⱼₖₗₘₙₒₚqᵣₛₜᵤᵥwₓyz_,;.?!/\\'ₐBCDₑFGₕᵢⱼₖₗₘₙₒₚQᵣₛₜᵤᵥWₓYZ₀₁₂₃₄₅₆₇₈₉";
    private static final String FAUX_CYRILLIC
            = "аъсↁэfБЂіјкlмиорqѓѕтцvшхЎz_,;.?!/\\'ДБҀↁЄFБНІЈЌLМИФРQЯЅГЦVЩЖЧZ0123456789";
    private static final String SMALL_CAP
            = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴩqʀꜱᴛᴜᴠᴡxyᴢ_,;.?!/\\'ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴩQʀꜱᴛᴜᴠᴡxYᴢ0123456789";
    private static final String ANTROPHOBIA = "αв¢∂єfgнιנкℓмиσρqяѕтυνωχуz" +
            "_,;.?!/\\'αв¢∂єfgнιנкℓмиσρqяѕтυνωχуz0123456789";
    private static final String CURRENCY = "₳฿₵ĐɆ₣₲ⱧłJ₭Ⱡ₥₦Ø₱QⱤ₴₮ɄV₩ӾɎⱫ_,;.?!/\\'₳฿₵ĐɆ₣₲ⱧłJ₭Ⱡ₥" +
            "₦Ø₱QⱤ₴₮ɄV₩ӾɎⱫ0123456789";
    private static final String PARANORMAL = "αвcdєfghíjklmnσpqrstuvwхчz_,;.?!/\\'αвcdєfghíjklmnσpqrstuvwхчz0123456789";
    private static final String SORCERER = "ǟɮƈɖɛʄɢɦɨʝӄʟʍռօքզʀֆȶʊʋաӼʏʐ_,;.?!/\\'ǟɮƈɖɛʄɢɦɨʝӄʟʍռօքզʀֆȶʊʋաӼʏʐ0123456789";
    private static final String[] EFFECTS;

    static {
        NORMAL = "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        MAX_LENGTH = NORMAL.length();


        EFFECTS = new String[]{
                "ąβȼď€ƒǥhɨjЌℓʍɲ๏ρǭя$ţµ˅ώж¥ƶ_,;.?!/\\'ąβȼď€ƒǥhɨjЌℓʍɲ๏ρǭя$ţµ˅ώж¥ƶ0123456789",
                "åβçď£ƒğȟȋjķȽɱñ¤קǭȑ§țɥ√Ψ×ÿž_,;.?!/\\'åβçď£ƒğȟȋjķȽɱñ¤קǭȑ§țɥ√Ψ×ÿž0123456789",
                "ąþȼȡƹƒǥɦɨǰƙŁʍɲǿρǭřȿƮµ˅ώж¥ƶ_,;.?!/\\'ąþȼȡƹƒǥɦɨǰƙŁʍɲǿρǭřȿƮµ˅ώж¥ƶ0123456789",
                "άвςȡέғģħίјķĻмήόρqŕşţùνώxчž_,;.?!/\\'άвςȡέғģħίјķĻмήόρqŕşţùνώxчž0123456789",
                "ÃβČĎẸƑĞĤĮĴЌĹϻŇỖƤǪŘŜŤǗϋŴЖЎŻ_,;.?!/\\'ÃβČĎẸƑĞĤĮĴЌĹϻŇỖƤǪŘŜŤǗϋŴЖЎŻ0123456789",
                "αв¢∂єfgнιנкℓмиσρqяѕтυνωχуz_,;.?!/\\'αв¢∂єfgнιנкℓмиσρqяѕтυνωχуz0123456789",
                "ค๒ς๔єŦﻮђเןкl๓ภ๏קợгรtยשฬץאz_,;.?!/\\'ค๒ς๔єŦﻮђเןкl๓ภ๏קợгรtยשฬץאz0123456789",
                "ĂβČĎĔŦĞĤĨĴĶĹМŃŐРQŔŚŤÚVŴЖŶŹ_,;.?!/\\'ĂβČĎĔŦĞĤĨĴĶĹМŃŐРQŔŚŤÚVŴЖŶŹ0123456789",
                "48(d3f9h!jk1mn0pqr57uvwxy2_,;.?!/\\'48(d3f9h!jk1mn0pqr57uvwxy20123456789",
                "ɐqɔpǝɟƃɥ!ɾʞןɯuodbɹsʇnʌʍxʎz_,;.?!/\\'ɐqɔpǝɟƃɥ!ɾʞןɯuodbɹsʇnʌʍxʎz0123456789",
                "9876543210zʎxʍʌnʇsɹbdouɯןʞɾ!ɥƃɟǝpɔqɐ'\\/!?.;,_zʎxʍʌnʇsɹbdouɯןʞɾ!ɥƃɟǝpɔqɐ",
                "aвcdeғgнιjĸlмnopqrѕтυvwхyz_,;.?!/\\'aвcdeғgнιjĸlмnopqrѕтυvwхyz0123456789",
                "მჩეძპfცhἶქκlოῆõρგΓჰནυὗwჯყɀ_,;.?!/\\'მჩეძპfცhἶქκlოῆõρგΓჰནυὗwჯყɀ0123456789",
                "ÄBĊĐË₣ĠȞÏĴĶĻMŅÖPǬŖŚȚŮVŴXŸŹ_,;.?!/\\'ÄBĊĐË₣ĠȞÏĴĶĻMŅÖPǬŖŚȚŮVŴXŸŹ0123456789",
                "αвc∂εғgнιנкℓмησρqяsтυvωxүz_,;.?!/\\'αвc∂εғgнιנкℓмησρqяsтυvωxүz0123456789",
                "äḅċďệḟġḧïjḳŀṃńöṗqŕṩẗüṿẅẍÿẓ_,;.?!/\\'äḅċďệḟġḧïjḳŀṃńöṗqŕṩẗüṿẅẍÿẓ0123456789",
                "ḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒ_,;.?!/\\'ḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒ0123456789",
                "⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵_,;.?!/\\'⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵0123456789",

//                //parentheses up case
//                "\uD83C\uDD10\uD83C\uDD11\uD83C\uDD12\uD83C\uDD13\uD83C\uDD14\uD83C\uDD15\uD83C" +
//                        "\uDD16\uD83C\uDD17\uD83C\uDD18\uD83C\uDD19\uD83C\uDD1A\uD83C\uDD1B\uD83C" +
//                        "\uDD1C\uD83C\uDD1D\uD83C\uDD1E\uD83C\uDD1F\uD83C\uDD20\uD83C\uDD21\uD83C" +
//                        "\uDD22\uD83C\uDD23\uD83C\uDD24\uD83C\uDD25\uD83C\uDD26\uD83C\uDD27\uD83C" +
//                        "\uDD28\uD83C\uDD29_,;.?!/\\'\uD83C\uDD10\uD83C\uDD11\uD83C\uDD12\uD83C" +
//                        "\uDD13\uD83C\uDD14\uD83C\uDD15\uD83C\uDD16\uD83C\uDD17\uD83C\uDD18\uD83C" +
//                        "\uDD19\uD83C\uDD1A\uD83C\uDD1B\uD83C\uDD1C\uD83C\uDD1D\uD83C\uDD1E\uD83C" +
//                        "\uDD1F\uD83C\uDD20\uD83C\uDD21\uD83C\uDD22\uD83C\uDD23\uD83C\uDD24\uD83C" +
//                        "\uDD25\uD83C\uDD26\uD83C\uDD27\uD83C\uDD28\uD83C\uDD290123456789",

                "αЪċđэךּġЋїפֿқľmήŏÞףřšŧŭνώאַỶż_,;.?!/\\'αЪċđэךּġЋїפֿқľmήŏÞףřšŧŭνώאַỶż0123456789",
                "αвς∂єfgнιנкℓмиσρףяѕтυνωאָуz_,;.?!/\\'αвς∂єfgнιנкℓмиσρףяѕтυνωאָуz0123456789",
                "ḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒ_,;.?!/\\'ḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒ0123456789",
                "ÁßČĎĔŦĞĤĨĴĶĹМŃŐРQŔŚŤÚVŴЖŶŹ_,;.?!/\\'ÁßČĎĔŦĞĤĨĴĶĹМŃŐРQŔŚŤÚVŴЖŶŹ0123456789",
                "ĂбĈĎÊҒĜĤĨĴҚĹMŇÕPØŘŜŤŨVŴҲŶŽ_,;.?!/\\'ĂбĈĎÊҒĜĤĨĴҚĹMŇÕPØŘŜŤŨVŴҲŶŽ0123456789",
                "ąɓçdęƒɠђįʝķɭɱŋǫƥʠŗşţųvwҳƴʐ_,;.?!/\\'ąɓçdęƒɠђįʝķɭɱŋǫƥʠŗşţųvwҳƴʐ0123456789",
        };

        STYLES = new ArrayList<>();
        STYLES.addAll(Arrays.asList(EFFECTS));
        STYLES.add(CIRCLE);
        STYLES.add(FULL_WIDTH);
        STYLES.add(A_CUTE);
        STYLES.add(CURVY_1);
        STYLES.add(CURVY_2);
        STYLES.add(CURVY_3);
        STYLES.add(ROCK_DOT);
        STYLES.add(STROKE);
        STYLES.add(SUPPER_SCRIPT);
        STYLES.add(SUB_SCRIPT);
        STYLES.add(FAUX_CYRILLIC);
        STYLES.add(SMALL_CAP);
        STYLES.add(ANTROPHOBIA);
        STYLES.add(CURRENCY);
        STYLES.add(PARANORMAL);
        STYLES.add(SORCERER);
    }

    private String replacement = "";

    ReplaceEffect(String replacement) {
        this.replacement = replacement;
    }

    public static StringBuilder convert(String text, String data) {
        StringBuilder result = new StringBuilder();
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int index = NORMAL.indexOf(letter);
            result.append((index != -1) ? data.charAt(index) : letter);
        }
        return result;
    }

    public static ArrayList<String> convert(String text) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String effect : STYLES) {
            arrayList.add(convert(text, effect).toString());
        }
        return arrayList;
    }

    public static ArrayList<Style> createStyle() {
        ArrayList<Style> styles = new ArrayList<>();
        for (String style : STYLES) {
            styles.add(new ReplaceEffect(style));
        }
        return styles;
    }

    @Override
    public int hashCode() {
        return replacement.hashCode();
    }

    @Override
    public String generate(String input) {
        StringBuilder result = new StringBuilder();
        char letter;
        for (int i = 0; i < input.length(); i++) {
            letter = input.charAt(i);
            int index = NORMAL.indexOf(letter);
            result.append((index != -1) ? replacement.charAt(index) : letter);
        }
        return result.toString();
    }
}
