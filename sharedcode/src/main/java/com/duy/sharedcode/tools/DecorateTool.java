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

package com.duy.sharedcode.tools;

import java.util.ArrayList;

/**
 * Created by Duy on 07-Jun-17.
 */

public class DecorateTool {
    public static final String[] EFFECTS = new String[]{
            "•?((¯°·._.• [ %1$s ] •._.·°¯))؟•",
            "ıllıllı [ %1$s ] ıllıllı",
            "¸,ø¤º°`°º¤ø,¸¸,ø¤º° [ %1$s ] °º¤ø,¸¸,ø¤º°`°º¤ø,¸",
            "°°°·.°·..·°¯°·._.· [ %1$s ] ·._.·°¯°·.·° .·°°°",
            "•´¯`•. [ %1$s ] .•´¯`•",
            "×º°”˜`”°º× [ %1$s ] ×º°”˜`”°º×",
            "•]••´º´•» [ %1$s ] «•´º´••[•",
            "]|I{•------» [ %1$s ] «------•}I|[",
            "§.•´¨'°÷•..× [ %1$s ] ×,.•´¨'°÷•..§",
            "•°¯`•• [ %1$s ] ••´¯°•",
            "(¯`·.¸¸.·´¯`·.¸¸.-> [ %1$s ] <-.¸¸.·´¯`·.¸¸.·´¯)",
            "*´¯`*.¸¸.*´¯`* [ %1$s ] *´¯`*.¸¸.*´¯`*",
            "(¯`·.¸¸.-> °º [ %1$s ] º° <-.¸¸.·´¯)",
            "°·.¸.·°¯°·.¸.·°¯°·.¸.-> [ %1$s ] <-.¸.·°¯°·.¸.·°¯°·.¸.·°",
            "|!¤*'~``~'*¤!| [ %1$s ] |!¤*'~``~'*¤!|",
            "._|.<(+_+)>.|_. [ %1$s ] ._|.<(+_+)>.|_.",
            "•._.••´¯``•.¸¸.•` [ %1$s ] `•.¸¸.•´´¯`••._.•",
            "¸„.-•~¹°”ˆ˜¨ [ %1$s ] ¨˜ˆ”°¹~•-.„¸",
            "(¯´•._.• [ %1$s ] •._.•´¯)",
            "••¤(`×[¤ [ %1$s ] ¤]×´)¤••",
            "•´¯`•» [ %1$s ] «•´¯`•",
            "`•.,¸¸,.•´¯ [ %1$s ] ¯`•.,¸¸,.•´",
            "¸,ø¤º°`°º¤ø,¸ [ %1$s ] ¸,ø¤º°`°º¤ø,¸",
            ".o0×X×0o. [ %1$s ] .o0×X×0o.",
            ",-*'^'~*-.,_,.-*~ [ %1$s ] ~*-.,_,.-*~'^'*-,",
            "`•.¸¸.•´´¯`••._.• [ %1$s ] •._.••`¯´´•.¸¸.•`",
            "—(••÷[ [ %1$s ] ]÷••—",
            "¤¸¸.•´¯`•¸¸.•..>> [ %1$s ] <<..•.¸¸•´¯`•.¸¸¤",
            "••.•´¯`•.•• [ %1$s ] ••.•´¯`•.••",
            ".•°¤*(¯`★´¯)*¤° [ %1$s ] °¤*(¯´★`¯)*¤°•.",
            "๑۞๑,¸¸,ø¤º°`°๑۩ [ %1$s ] ๑۩ ,¸¸,ø¤º°`°๑۞๑",
            "-漫~*'¨¯¨'*·舞~ [ %1$s ] ~舞*'¨¯¨'*·~漫-",
            "★·.·´¯`·.·★ [ %1$s ] ★·.·´¯`·.·★",
            "▁ ▂ ▄ ▅ ▆ ▇ █ [ %1$s ] █ ▇ ▆ ▅ ▄ ▂ ▁",
            "▀▄▀▄▀▄ [ %1$s ] ▄▀▄▀▄▀",
            "(-_-) [ %1$s ] (-_-)",
            "▌│█║▌║▌║ [ %1$s ] ║▌║▌║█│▌"
    };

    public static ArrayList<String> generate(String input) {
        ArrayList<String> result = new ArrayList<>();

        for (String effect : EFFECTS) {
            result.add(String.format(effect, input));
        }

        return result;
    }
}
