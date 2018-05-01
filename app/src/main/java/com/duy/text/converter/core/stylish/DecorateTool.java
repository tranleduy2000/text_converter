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

import java.util.ArrayList;

/**
 * Created by Duy on 07-Jun-17.
 */

public class DecorateTool {
    private static final String[] EFFECTS = new String[]{
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
            "▌│█║▌║▌║ [ %1$s ] ║▌║▌║█│▌",
            "•♥•♥•♥•♥•♥ [ %1$s ] ♥•♥•♥•♥•♥•",
            "˙·٠•●♥ [ %1$s ] ♥●•٠·˙"
    };

    public static ArrayList<String> generate(String input) {
        ArrayList<String> result = new ArrayList<>();

        for (String effect : EFFECTS) {
            result.add(String.format(effect, input));
        }

        return result;
    }
}
