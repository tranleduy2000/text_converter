package com.duy.text_converter.utils;

import java.util.ArrayList;

/**
 * Created by Duy on 07-Jun-17.
 */

public class EffectTool {
    /**
     * original text
     */
    public static final String NORMAL =
            "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final String[] EFFECTS = new String[]{
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
            "ⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ_,;.?!/\\'ⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ0123456789",
            "მჩეძპfცhἶქκlოῆõρგΓჰནυὗwჯყɀ_,;.?!/\\'მჩეძპfცhἶქκlოῆõρგΓჰནυὗwჯყɀ0123456789",
            "ÄBĊĐË₣ĠȞÏĴĶĻMŅÖPǬŖŚȚŮVŴXŸŹ_,;.?!/\\'ÄBĊĐË₣ĠȞÏĴĶĻMŅÖPǬŖŚȚŮVŴXŸŹ0123456789",
            "αвc∂εғgнιנкℓмησρqяsтυvωxүz_,;.?!/\\'αвc∂εғgнιנкℓмησρqяsтυvωxүz0123456789"
    };


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
        for (String effect : EFFECTS) {
            arrayList.add(convert(text, effect).toString());
        }
        return arrayList;
    }

    public static void main(String[] args) {

    }
}
