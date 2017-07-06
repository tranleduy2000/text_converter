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
 * Created by Duy on 07-Jun-17.
 */

public class StyleTool {
    /**
     * original text
     */
    public static final String NORMAL;
    public static final int MAX_LENGTH;
    public static final ArrayList<String> STYLES;

    private static final String CIRCLE = "ⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ_,;⨀?!⊘⦸'ⒶⒷⒸⒹ" +
            "ⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏ0①②③④⑤⑥⑦⑧⑨";
    private static final String FULL_WIDTH
            = "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ_，；．？！／\\＇ＡＢＣＤＥＦＧＨＩＪＫ" +
            "ＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ０１２３４５６７８９";
    private static final String A_CUTE
            = "ábćdéfǵhíjḱĺḿńőṕqŕśtúvẃxӳź_,;.?!/\\'ÁBĆDÉFǴHíJḰĹḾŃŐṔQŔśTŰVẂXӲŹ0123456789";
    private static final String CURVY_1
            = "ค๒ƈɗﻉिﻭɦٱﻝᛕɭ๓กѻρ۹ɼรՇપ۷ฝซץչ_,;܁?!/\\'ค๒ƈɗﻉिﻭɦٱﻝᛕɭ๓กѻρ۹ɼรՇપ۷ฝซץչ0123456789";
    private static final String CURVY_2
            = "αв¢∂єƒﻭнιנкℓмησρ۹яѕтυνωχуչ_,;.?!/\\'αв¢∂єƒﻭнιנкℓмησρ۹яѕтυνωχуչ0123456789";
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
                "ḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒ_,;.?!/\\'ḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒ0123456789"};

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
}
