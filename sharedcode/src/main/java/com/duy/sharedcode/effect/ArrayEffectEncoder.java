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

package com.duy.sharedcode.effect;

import android.support.annotation.NonNull;

import com.duy.sharedcode.codec.Encoder;

import java.util.ArrayList;

/**
 * Created by Duy on 06-Jul-17.
 */

public class ArrayEffectEncoder implements Encoder {
    private static final String NORMAL;
    private static final String[][] EFFECTS;

    static {
        NORMAL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] array = new String[]{
                //blue text style
                "\uD83C\uDDE6 \uD83C\uDDE7 \uD83C\uDDE8 \uD83C\uDDE9 \uD83C\uDDEA \uD83C\uDDEB " +
                        "\uD83C\uDDEC \uD83C\uDDED \uD83C\uDDEE \uD83C\uDDEF \uD83C\uDDF0 \uD83C\uDDF1 " +
                        "\uD83C\uDDF2 \uD83C\uDDF3 \uD83C\uDDF4 \uD83C\uDDF5 \uD83C\uDDF6 \uD83C\uDDF7 " +
                        "\uD83C\uDDF8 \uD83C\uDDF9 \uD83C\uDDFA \uD83C\uDDFB \uD83C\uDDFC \uD83C\uDDFD " +
                        "\uD83C\uDDFE \uD83C\uDDFF \uD83C\uDDE6 \uD83C\uDDE7 \uD83C\uDDE8 \uD83C\uDDE9 " +
                        "\uD83C\uDDEA \uD83C\uDDEB \uD83C\uDDEC \uD83C\uDDED \uD83C\uDDEE \uD83C\uDDEF " +
                        "\uD83C\uDDF0 \uD83C\uDDF1 \uD83C\uDDF2 \uD83C\uDDF3 \uD83C\uDDF4 \uD83C\uDDF5 " +
                        "\uD83C\uDDF6 \uD83C\uDDF7 \uD83C\uDDF8 \uD83C\uDDF9 \uD83C\uDDFA \uD83C\uDDFB " +
                        "\uD83C\uDDFC \uD83C\uDDFD \uD83C\uDDFE \uD83C\uDDFF ",

                "\u2E3D\u2E3Da\u2E3D\u2E3Db\u2E3D\u2E3Dc\u2E3D\u2E3Dd\u2E3D\u2E3De\u2E3D\u2E3Df" +
                        "\u2E3D\u2E3Dg\u2E3D\u2E3Dh\u2E3D\u2E3Di\u2E3D\u2E3Dj\u2E3D\u2E3Dk\u2E3D" +
                        "\u2E3Dl\u2E3D\u2E3Dm\u2E3D\u2E3Dn\u2E3D\u2E3Do\u2E3D\u2E3Dp\u2E3D\u2E3Dq" +
                        "\u2E3D\u2E3Dr\u2E3D\u2E3Ds\u2E3D\u2E3Dt\u2E3D\u2E3Du\u2E3D\u2E3Dv\u2E3D" +
                        "\u2E3Dw\u2E3D\u2E3Dx\u2E3D\u2E3Dy\u2E3D\u2E3Dz\u2E3D\u2E3DA\u2E3D\u2E3DB" +
                        "\u2E3D\u2E3DC\u2E3D\u2E3DD\u2E3D\u2E3DE\u2E3D\u2E3DF\u2E3D\u2E3DG\u2E3D" +
                        "\u2E3DH\u2E3D\u2E3DI\u2E3D\u2E3DJ\u2E3D\u2E3DK\u2E3D\u2E3DL\u2E3D\u2E3DM" +
                        "\u2E3D\u2E3DN\u2E3D\u2E3DO\u2E3D\u2E3DP\u2E3D\u2E3DQ\u2E3D\u2E3DR" +
                        "\u2E3D\u2E3DS\u2E3D\u2E3DT\u2E3D\u2E3DU\u2E3D\u2E3DV\u2E3D\u2E3DW\u2E3D" +
                        "\u2E3DX\u2E3D\u2E3DY\u2E3D\u2E3DZ\u2E3D\u2E3D",

                "\u2E3E\u2E3Ea\u2E3E\u2E3Eb\u2E3E\u2E3Ec\u2E3E\u2E3Ed\u2E3E\u2E3Ee\u2E3E\u2E3Ef" +
                        "\u2E3E\u2E3Eg\u2E3E\u2E3Eh\u2E3E\u2E3Ei\u2E3E\u2E3Ej\u2E3E\u2E3Ek\u2E3E" +
                        "\u2E3El\u2E3E\u2E3Em\u2E3E\u2E3En\u2E3E\u2E3Eo\u2E3E\u2E3Ep\u2E3E\u2E3E" +
                        "q\u2E3E\u2E3Er\u2E3E\u2E3Es\u2E3E\u2E3Et\u2E3E\u2E3Eu\u2E3E\u2E3Ev\u2E3E" +
                        "\u2E3Ew\u2E3E\u2E3Ex\u2E3E\u2E3Ey\u2E3E\u2E3Ez\u2E3E\u2E3EA\u2E3E\u2E3E" +
                        "B\u2E3E\u2E3EC\u2E3E\u2E3ED\u2E3E\u2E3EE\u2E3E\u2E3EF\u2E3E\u2E3EG\u2E3E" +
                        "\u2E3EH\u2E3E\u2E3EI\u2E3E\u2E3EJ\u2E3E\u2E3EK\u2E3E\u2E3EL\u2E3E\u2E3E" +
                        "M\u2E3E\u2E3EN\u2E3E\u2E3EO\u2E3E\u2E3EP\u2E3E\u2E3EQ\u2E3E\u2E3ER\u2E3E" +
                        "\u2E3ES\u2E3E\u2E3ET\u2E3E\u2E3EU\u2E3E\u2E3EV\u2E3E\u2E3EW\u2E3E\u2E3E" +
                        "X\u2E3E\u2E3EY\u2E3E\u2E3EZ\u2E3E\u2E3E",

                "a\u0336b\u0336c\u0336d\u0336e\u0336f\u0336g\u0336h\u0336i\u0336j\u0336k\u0336l\u0336" +
                        "m\u0336n\u0336o\u0336p\u0336q\u0336r\u0336s\u0336t\u0336u\u0336v\u0336w\u0336" +
                        "x\u0336y\u0336z\u0336a\u0336b\u0336c\u0336d\u0336e\u0336f\u0336g\u0336h\u0336" +
                        "i\u0336j\u0336k\u0336l\u0336m\u0336n\u0336o\u0336p\u0336q\u0336r\u0336s\u0336" +
                        "t\u0336u\u0336v\u0336w\u0336x\u0336y\u0336z\u0336",

                "a\u0334b\u0334c\u0334d\u0334e\u0334f\u0334g\u0334h\u0334i\u0334j\u0334k\u0334l\u0334" +
                        "m\u0334n\u0334o\u0334p\u0334q\u0334r\u0334s\u0334t\u0334u\u0334v\u0334w\u0334" +
                        "x\u0334y\u0334z\u0334a\u0334b\u0334c\u0334d\u0334e\u0334f\u0334g\u0334h\u0334" +
                        "i\u0334j\u0334k\u0334l\u0334m\u0334n\u0334o\u0334p\u0334q\u0334r\u0334s\u0334" +
                        "t\u0334u\u0334v\u0334w\u0334x\u0334y\u0334z\u0334",

                "a\u0337b\u0337c\u0337d\u0337e\u0337f\u0337g\u0337h\u0337i\u0337j\u0337k\u0337l\u0337" +
                        "m\u0337n\u0337o\u0337p\u0337q\u0337r\u0337s\u0337t\u0337u\u0337v\u0337w\u0337" +
                        "x\u0337y\u0337z\u0337a\u0337b" +
                        "\u0337c\u0337d\u0337e\u0337f\u0337g\u0337h\u0337i\u0337j\u0337k\u0337l\u0337" +
                        "m\u0337n\u0337o\u0337p\u0337q\u0337r\u0337s\u0337t\u0337u\u0337v\u0337w\u0337" +
                        "x\u0337y\u0337z\u0337",

                "a\u0338b\u0338c\u0338d\u0338e\u0338f\u0338g\u0338h\u0338i\u0338j\u0338k\u0338l\u0338" +
                        "m\u0338n\u0338o\u0338p\u0338q\u0338r\u0338s\u0338t\u0338u\u0338v\u0338w\u0338" +
                        "x\u0338y\u0338z\u0338A\u0338B" +
                        "\u0338C\u0338D\u0338E\u0338F\u0338G\u0338H\u0338I\u0338J\u0338K\u0338L\u0338" +
                        "M\u0338N\u0338O\u0338P\u0338Q\u0338R\u0338S\u0338T\u0338U\u0338V\u0338W\u0338" +
                        "X\u0338Y\u0338Z\u0338",

                "a\u0332b\u0332c\u0332d\u0332e\u0332f\u0332g\u0332h\u0332i\u0332j\u0332k\u0332l\u0332" +
                        "m\u0332n\u0332o\u0332p\u0332q\u0332r\u0332s\u0332t\u0332u\u0332v\u0332w\u0332" +
                        "x\u0332y\u0332z\u0332a\u0332b\u0332c\u0332d\u0332e\u0332f\u0332g\u0332h\u0332" +
                        "i\u0332j\u0332k\u0332l\u0332m\u0332n\u0332o\u0332p\u0332q\u0332r\u0332s\u0332" +
                        "t\u0332u\u0332v\u0332w\u0332x\u0332y\u0332z\u0332",

                "a̳b̳c̳d̳e̳f̳g̳h̳i̳j̳k̳l̳m̳n̳o̳p̳q̳r̳s̳t̳u̳v̳w̳x̳y̳z̳a̳b̳c̳d̳e̳f̳g̳h̳i̳j̳k̳l̳m̳n̳o̳p̳q̳r̳s̳t̳u̳v̳w̳x̳y̳z̳",

                "[̲̅a̲̅][̲̅b̲̅][̲̅c̲̅][̲̅d̲̅][̲̅e̲̅][̲̅f̲̅][̲̅g̲̅][̲̅h̲̅][̲̅i̲̅][̲̅j̲̅][̲̅k̲̅][̲̅l̲̅][" +
                        "̲̅m̲̅][̲̅n̲̅][̲̅o̲̅][̲̅p̲̅][̲̅q̲̅][̲̅r̲̅][̲̅s̲̅][̲̅t̲̅][̲̅u̲̅][̲̅v̲̅][̲̅w̲̅][" +
                        "̲̅x̲̅][̲̅y̲̅][̲̅z̲̅][̲̅a̲̅][̲̅b̲̅][̲̅c̲̅][̲̅d̲̅][̲̅e̲̅][̲̅f̲̅][̲̅g̲̅][̲̅h̲̅][̲̅i̲̅]" +
                        "[̲̅j̲̅][̲̅k̲̅][̲̅l̲̅][̲̅m̲̅][̲̅n̲̅][̲̅o̲̅][̲̅p̲̅][̲̅q̲̅][̲̅r̲̅][̲̅s̲̅][̲̅t̲̅]" +
                        "[̲̅u̲̅][̲̅v̲̅][̲̅w̲̅][̲̅x̲̅][̲̅y̲̅][̲̅z̲̅]",

                "a̾b̾c̾d̾e̾f̾g̾h̾i̾j̾k̾l̾m̾n̾o̾p̾q̾r̾s̾t̾u̾v̾w̾x̾y̾z" +
                        "̾a̾b̾c̾d̾e̾f̾g̾h̾i̾j̾k̾l̾m̾n̾o̾p̾q̾r̾s̾t̾u̾v̾w̾x̾y̾z̾",

                "ḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒḀḃḉḊḕḟḠḧḭjḲḶṁṆṏṖqṙṠṮṳṼẇẌẏẒ",

                "a͆b͆c͆d͆e͆f͆g͆h͆i͆j͆k͆l͆m͆n͆o͆p͆q͆r͆s͆t͆u͆v͆w͆x͆y͆z͆a" +
                        "͆b͆c͆d͆e͆f͆g͆h͆i͆j͆k͆l͆m͆n͆o͆p͆q͆r͆s͆t͆u͆v͆w͆x͆y͆z͆",

                "a̺b̺c̺d̺e̺f̺g̺h̺i̺j̺k̺l̺m̺n̺o̺p̺q̺r̺s̺t̺u̺v̺w̺x̺y̺z̺a̺b̺c̺d̺e̺f̺g̺h̺i̺j̺k̺l̺m̺n̺o̺p̺q̺r̺s̺t̺u̺v̺w̺x̺y̺z̺",

                "a͙b͙c͙d͙e͙f͙g͙h͙i͙j͙k͙l͙m͙n͙o͙p͙q͙r͙s͙t͙u͙v͙w͙x͙y͙z͙a͙b͙c͙d͙e͙f͙g͙h͙i͙j͙k͙l͙m͙n͙o͙p͙q͙r͙s͙t͙u͙v͙w͙x͙y͙z͙",

                "a̟b̟c̟d̟e̟f̟g̟h̟i̟j̟k̟l̟m̟n̟o̟p̟q̟r̟s̟t̟u̟v̟w̟x̟y̟z̟a̟b̟c̟d̟e̟f̟g̟h̟i̟j̟k̟l̟m̟n̟o̟p̟q̟r̟s̟t̟u̟v̟w̟x̟y̟z̟",

                "a͓̽b͓̽c͓̽d͓̽e͓̽f͓̽g͓̽h͓̽i͓̽j͓̽k͓̽l͓̽m͓̽n͓̽o͓̽p͓̽q͓̽r͓̽s͓̽t͓̽u͓̽v͓̽w͓̽x͓̽y͓̽z͓̽" +
                        "a͓̽b͓̽c͓̽d͓̽e͓̽f͓̽g͓̽h͓̽i͓̽j͓̽k͓̽l͓̽m͓̽n͓̽o͓̽p͓̽q͓̽r͓̽s͓̽t͓̽u͓̽v͓̽w͓̽x͓̽y͓̽z͓̽",

                "a͎b͎c͎d͎e͎f͎g͎h͎i͎j͎k͎l͎m͎n͎o͎p͎q͎r͎s͎t͎u͎v͎w͎x͎y͎z͎a͎b͎c͎d͎e͎f͎g͎h͎i͎j͎k͎l͎m͎n͎o͎p͎q͎r͎s͎t͎u͎v͎w͎x͎y͎z͎",

                "a♥b♥c♥d♥e♥f♥g♥h♥i♥j♥k♥l♥m♥n♥o♥p♥q♥r♥s♥t♥u♥v♥w♥x♥y♥z♥a♥" +
                        "b♥c♥d♥e♥f♥g♥h♥i♥j♥k♥l♥m♥n♥o♥p♥q♥r♥s♥t♥u♥v♥w♥x♥y♥z♥",


                "ãb̃c̃d̃ẽf̃g̃h̃ĩj̃k̃l̃m̃ñõp̃q̃r̃s̃t̃ũṽw̃x̃ỹz̃ÃB̃C̃D̃ẼF̃G̃H̃ĨJ̃K̃L̃M̃ÑÕP̃Q̃R̃S̃T̃ŨṼW̃X̃ỸZ̃",

                "ḁb̥c̥d̥e̥f̥g̥h̥i̥j̥k̥l̥m̥n̥o̥p̥q̥r̥s̥t̥u̥v̥w̥x̥y̥z̥ḀB̥C̥D̥E̥F̥G̥H̥I̥J̥K̥L̥M̥N̥O̥P̥Q̥R̥S̥T̥U̥V̥W̥X̥Y̥Z̥",

                "a̐b̐c̐d̐e̐f̐g̐h̐i̐j̐k̐l̐m̐n̐o̐p̐q̐r̐s̐t̐u̐v̐w̐x̐y" +
                        "̐z̐A̐B̐C̐D̐E̐F̐G̐H̐I̐J̐K̐L̐M̐N̐O̐P̐Q̐R̐S̐T̐U̐V̐W̐X̐Y̐Z̐",

                "a͒b͒c͒d͒e͒f͒g͒h͒i͒j͒k͒l͒m͒n͒o͒p͒q͒r͒s͒t͒u͒v͒w͒x͒y͒z͒" +
                        "A͒B͒C͒D͒E͒F͒G͒H͒I͒J͒K͒L͒M͒N͒O͒P͒Q͒R͒S͒T͒U͒V͒W͒X͒Y͒Z͒",

                "ȁb̏c̏d̏ȅf̏g̏h̏ȉj̏k̏l̏m̏n̏ȍp̏q̏ȑs̏t̏ȕv̏w̏x̏y̏z̏ȀB̏C̏D̏ȄF̏G̏H̏ȈJ̏K̏L̏M̏N̏ȌP̏Q̏ȐS̏T̏ȔV̏W̏X̏Y̏Z̏",

                "a\u032bb\u032bc\u032bd\u032be\u032bf\u032bg\u032bh\u032bi\u032bj\u032bk\u032bl\u032b" +
                        "m\u032bn\u032bo\u032bp\u032bq\u032br\u032bs\u032bt\u032bu\u032bv\u032bw\u032b" +
                        "x\u032by\u032bz\u032bA\u032bB\u032bC\u032bD\u032bE\u032bF\u032bG\u032bH\u032b" +
                        "I\u032bJ\u032bK\u032bL\u032bM\u032bN\u032bO\u032bP\u032bQ\u032bR\u032bS\u032b" +
                        "T\u032bU\u032bV\u032bW\u032bX\u032bY\u032bZ\u032b",

                "a\u035ab\u035ac\u035ad\u035ae\u035af\u035ag\u035ah\u035ai\u035aj\u035ak\u035al\u035a" +
                        "m\u035an\u035ao\u035ap\u035aq\u035ar\u035as\u035at\u035au\u035av\u035aw\u035a" +
                        "x\u035ay\u035az\u035aA\u035aB\u035aC\u035aD\u035aE\u035aF\u035aG\u035aH\u035a" +
                        "I\u035aJ\u035aK\u035aL\u035aM\u035aN\u035aO\u035aP\u035aQ\u035aR\u035aS\u035a" +
                        "T\u035aU\u035aV\u035aW\u035aX\u035aY\u035aZ\u035a",

                "a̾b̾c̾d̾e̾f̾g̾h̾i̾j̾k̾l̾m̾n̾o̾p̾q̾r̾s̾t̾u̾v̾w̾x̾y̾z̾A̾B̾C̾D̾E̾F̾G̾H̾I̾J̾K̾L̾M̾N̾O̾P̾Q̾R̾S̾T̾U̾V̾W̾X̾Y̾Z̾",

                "\u2E3Da\u2E3Db\u2E3Dc\u2E3Dd\u2E3De\u2E3Df\u2E3Dg\u2E3Dh\u2E3Di\u2E3Dj\u2E3Dk\u2E3D" +
                        "l\u2E3Dm\u2E3Dn\u2E3Do\u2E3Dp\u2E3Dq\u2E3Dr\u2E3Ds\u2E3Dt\u2E3Du\u2E3Dv\u2E3D" +
                        "w\u2E3Dx\u2E3Dy\u2E3Dz\u2E3DA\u2E3DB\u2E3DC\u2E3DD\u2E3DE\u2E3DF\u2E3DG\u2E3D" +
                        "H\u2E3DI\u2E3DJ\u2E3DK\u2E3DL\u2E3DM\u2E3DN\u2E3DO\u2E3DP\u2E3DQ\u2E3DR\u2E3D" +
                        "S\u2E3DT\u2E3DU\u2E3DV\u2E3DW\u2E3DX\u2E3DY\u2E3DZ\u2E3D",

                "\u2E3Ea\u2E3Eb\u2E3Ec\u2E3Ed\u2E3Ee\u2E3Ef\u2E3Eg\u2E3Eh\u2E3Ei\u2E3Ej\u2E3Ek\u2E3E" +
                        "l\u2E3Em\u2E3En\u2E3Eo\u2E3Ep\u2E3Eq\u2E3Er\u2E3Es\u2E3Et\u2E3Eu\u2E3Ev\u2E3E" +
                        "w\u2E3Ex\u2E3Ey\u2E3Ez\u2E3EA\u2E3EB\u2E3EC\u2E3ED\u2E3EE\u2E3EF\u2E3EG\u2E3E" +
                        "H\u2E3EI\u2E3EJ\u2E3EK\u2E3EL\u2E3EM\u2E3EN\u2E3EO\u2E3EP\u2E3EQ\u2E3ER\u2E3E" +
                        "S\u2E3ET\u2E3EU\u2E3EV\u2E3EW\u2E3EX\u2E3EY\u2E3EZ\u2E3E",
                "『a』『b』『c』『d』『e』『f』『g』『h』『i』『j』『k』『l』『m』『n』『o』『p』『q』『r』" +
                        "『s』『t』『u』『v』『w』『x』『y』『z』『a』『b』『c』『d』『e』『f』『g』『h』" +
                        "『i』『j』『k』『l』『m』『n』『o』『p』『q』『r』『s』『t』『u』『v』『w』『x』" +
                        "『y』『z』",

                "〖a〗〖b〗〖c〗〖d〗〖e〗〖f〗〖g〗〖h〗〖i〗〖j〗〖k〗〖l〗〖m〗〖n〗〖o〗〖p〗〖q〗" +
                        "〖r〗〖s〗〖t〗〖u〗〖v〗〖w〗〖x〗〖y〗〖z〗〖A〗〖B〗〖C〗〖D〗〖E〗〖F〗" +
                        "〖G〗〖H〗〖I〗〖J〗〖K〗〖L〗〖M〗〖N〗〖O〗〖P〗〖Q〗〖R〗〖S〗〖T〗〖U〗" +
                        "〖V〗〖W〗〖X〗〖Y〗〖Z〗",

                "〘a〙〘b〙〘c〙〘d〙〘e〙〘f〙〘g〙〘h〙〘i〙〘j〙〘k〙〘l〙〘m〙〘n〙〘o〙〘p〙〘q〙" +
                        "〘r〙〘s〙〘t〙〘u〙〘v〙〘w〙〘x〙〘y〙〘z〙〘A〙〘B〙〘C〙〘D〙〘E〙〘F〙" +
                        "〘G〙〘H〙〘I〙〘J〙〘K〙〘L〙〘M〙〘N〙〘O〙〘P〙〘Q〙〘R〙〘S〙〘T〙〘U〙" +
                        "〘V〙〘W〙〘X〙〘Y〙〘Z〙",

                "〚a〛〚b〛〚c〛〚d〛〚e〛〚f〛〚g〛〚h〛〚i〛〚j〛〚k〛〚l〛〚m〛〚n〛〚o〛" +
                        "〚p〛〚q〛〚r〛〚s〛〚t〛〚u〛〚v〛〚w〛〚x〛〚y〛〚z〛〚A〛〚B〛" +
                        "〚C〛〚D〛〚E〛〚F〛〚G〛〚H〛〚I〛〚J〛〚K〛〚L〛〚M〛〚N〛〚O〛" +
                        "〚P〛〚Q〛〚R〛〚S〛〚T〛〚U〛〚V〛〚W〛〚X〛〚Y〛〚Z〛",

                "《a》《b》《c》《d》《e》《f》《g》《h》《i》《j》《k》《l》《m》《n》《o》《p》" +
                        "《q》《r》《s》《t》《u》《v》《w》《x》《y》《z》《A》《B》《C》《D》《E》" +
                        "《F》《G》《H》《I》《J》《K》《L》《M》《N》《O》《P》《Q》《R》《S》《T》" +
                        "《U》《V》《W》《X》《Y》《Z》",

                "〔a〕〔b〕〔c〕〔d〕〔e〕〔f〕〔g〕〔h〕〔i〕〔j〕〔k〕〔l〕〔m〕〔n〕〔o〕〔p〕" +
                        "〔q〕〔r〕〔s〕〔t〕〔u〕〔v〕〔w〕〔x〕〔y〕〔z〕〔A〕〔B〕〔C〕〔D〕" +
                        "〔E〕〔F〕〔G〕〔H〕〔I〕〔J〕〔K〕〔L〕〔M〕〔N〕〔O〕〔P〕〔Q〕〔R〕" +
                        "〔S〕〔T〕〔U〕〔V〕〔W〕〔X〕〔Y〕〔Z〕",

                "【a】【b】【c】【d】【e】【f】【g】【h】【i】【j】【k】【l】【m】【n】【o】【p】" +
                        "【q】【r】【s】【t】【u】【v】【w】【x】【y】【z】【A】【B】【C】【D】" +
                        "【E】【F】【G】【H】【I】【J】【K】【L】【M】【N】【O】【P】【Q】【R】" +
                        "【S】【T】【U】【V】【W】【X】【Y】【Z】",
                "☾a☽☾b☽☾c☽☾d☽☾e☽☾f☽☾g☽☾h☽☾i☽☾j☽☾k☽☾l☽☾m☽☾n☽☾o☽☾p☽☾q☽☾r☽☾s☽☾t☽☾u☽☾v☽☾w☽☾x☽" +
                        "☾y☽☾z☽☾a☽☾b☽☾c☽☾d☽☾e☽☾f☽☾g☽☾h☽☾i☽☾j☽☾k☽☾l☽☾m☽☾n☽☾o☽☾p☽☾q☽☾r☽☾s☽☾t☽" +
                        "☾u☽☾v☽☾w☽☾x☽☾y☽☾z☽",

                "⟦a⟧⟦b⟧⟦c⟧⟦d⟧⟦e⟧⟦f⟧⟦g⟧⟦h⟧⟦i⟧⟦j⟧⟦k⟧⟦l⟧⟦m⟧⟦n⟧⟦o⟧⟦p⟧⟦q⟧⟦r⟧⟦s⟧⟦t⟧⟦u⟧⟦v⟧⟦w⟧⟦x⟧⟦y⟧⟦z⟧⟦a⟧⟦b⟧⟦c⟧⟦d⟧" +
                        "⟦e⟧⟦f⟧⟦g⟧⟦h⟧⟦i⟧⟦j⟧⟦k⟧⟦l⟧⟦m⟧⟦n⟧⟦o⟧⟦p⟧⟦q⟧⟦r⟧⟦s⟧⟦t⟧⟦u⟧⟦v⟧⟦w⟧⟦x⟧⟦y⟧⟦z⟧",

                "⟅a⟆⟅b⟆⟅c⟆⟅d⟆⟅e⟆⟅f⟆⟅g⟆⟅h⟆⟅i⟆⟅j⟆⟅k⟆⟅l⟆⟅m⟆⟅n⟆⟅o⟆⟅p⟆⟅q⟆⟅r⟆⟅s⟆⟅t⟆⟅u⟆⟅v⟆⟅w⟆⟅x⟆⟅y⟆⟅z⟆⟅a⟆" +
                        "⟅b⟆⟅c⟆⟅d⟆⟅e⟆⟅f⟆⟅g⟆⟅h⟆⟅i⟆⟅j⟆⟅k⟆⟅l⟆⟅m⟆⟅n⟆⟅o⟆⟅p⟆⟅q⟆⟅r⟆⟅s⟆⟅t⟆⟅u⟆⟅v⟆⟅w⟆⟅x⟆⟅y⟆⟅z⟆",

                "⚞a⚟⚞b⚟⚞c⚟⚞d⚟⚞e⚟⚞f⚟⚞g⚟⚞h⚟⚞i⚟⚞j⚟⚞k⚟⚞l⚟⚞m⚟⚞n⚟⚞o⚟⚞p⚟⚞q" +
                        "⚟⚞r⚟⚞s⚟⚞t⚟⚞u⚟⚞v⚟⚞w⚟⚞x⚟⚞y⚟⚞z⚟⚞a⚟⚞b⚟⚞c⚟⚞d⚟⚞e⚟⚞f" +
                        "⚟⚞g⚟⚞h⚟⚞i⚟⚞j⚟⚞k⚟⚞l⚟⚞m⚟⚞n⚟⚞o⚟⚞p⚟⚞q⚟⚞r⚟⚞s⚟⚞t⚟⚞u" +
                        "⚟⚞v⚟⚞w⚟⚞x⚟⚞y⚟⚞z⚟",

                "╔a╗╔b╗╔c╗╔d╗╔e╗╔f╗╔g╗╔h╗╔i╗╔j╗╔k╗╔l╗╔m╗╔n╗╔o╗╔p╗╔q╗╔r╗╔s╗╔t╗╔u╗╔v╗" +
                        "╔w╗╔x╗╔y╗╔z╗╔A╗╔B╗╔C╗╔D╗╔E╗╔F╗╔G╗╔H╗╔I╗╔J╗╔K╗╔L╗╔M╗╔N╗╔O╗╔P╗" +
                        "╔Q╗╔R╗╔S╗╔T╗╔U╗╔V╗╔W╗╔X╗╔Y╗╔Z╗",

                "╚a╝╚b╝╚c╝╚d╝╚e╝╚f╝╚g╝╚h╝╚i╝╚j╝╚k╝╚l╝╚m╝╚n╝╚o╝╚p╝╚q╝╚r╝╚s╝╚t╝╚u╝╚v╝" +
                        "╚w╝╚x╝╚y╝╚z╝╚A╝╚B╝╚C╝╚D╝╚E╝╚F╝╚G╝╚H╝╚I╝╚J╝╚K╝╚L╝╚M╝╚N╝╚O╝╚P╝" +
                        "╚Q╝╚R╝╚S╝╚T╝╚U╝╚V╝╚W╝╚X╝╚Y╝╚Z╝",

                "╠a╣╠b╣╠c╣╠d╣╠e╣╠f╣╠g╣╠h╣╠i╣╠j╣╠k╣╠l╣╠m╣╠n╣╠o╣╠p╣╠q╣╠r╣╠s╣╠t╣╠u╣╠v╣" +
                        "╠w╣╠x╣╠y╣╠z╣╠A╣╠B╣╠C╣╠D╣╠E╣╠F╣╠G╣╠H╣╠I╣╠J╣╠K╣╠L╣╠M╣╠N╣╠O╣╠P╣" +
                        "╠Q╣╠R╣╠S╣╠T╣╠U╣╠V╣╠W╣╠X╣╠Y╣╠Z╣",

                "╟a╢╟b╢╟c╢╟d╢╟e╢╟f╢╟g╢╟h╢╟i╢╟j╢╟k╢╟l╢╟m╢╟n╢╟o╢╟p╢╟q╢╟r╢╟s╢╟t╢╟u╢╟v╢" +
                        "╟w╢╟x╢╟y╢╟z╢╟A╢╟B╢╟C╢╟D╢╟E╢╟F╢╟G╢╟H╢╟I╢╟J╢╟K╢╟L╢╟M╢╟N╢╟O╢╟P" +
                        "╢╟Q╢╟R╢╟S╢╟T╢╟U╢╟V╢╟W╢╟X╢╟Y╢╟Z╢",

                "╭a╮╭b╮╭c╮╭d╮╭e╮╭f╮╭g╮╭h╮╭i╮╭j╮╭k╮╭l╮╭m╮╭n╮╭o╮╭p╮╭q╮╭r╮╭s╮╭t╮╭u╮╭v╮╭w╮╭x╮╭y╮╭z╮" +
                        "╭A╮╭B╮╭C╮╭D╮╭E╮╭F╮╭G╮╭H╮╭I╮╭J╮╭K╮╭L╮╭M╮╭N╮╭O╮╭P╮╭Q╮╭R╮╭S╮╭T╮╭U╮╭V╮╭W╮╭" +
                        "X╮╭Y╮╭Z╮",

                "╰a╯╰b╯╰c╯╰d╯╰e╯╰f╯╰g╯╰h╯╰i╯╰j╯╰k╯╰l╯╰m╯╰n╯╰o╯╰p╯╰q╯╰r╯╰s╯╰t╯╰u╯╰v╯╰w╯╰x╯╰y╯╰z╯" +
                        "╰A╯╰B╯╰C╯╰D╯╰E╯╰F╯╰G╯╰H╯╰I╯╰J╯╰K╯╰L╯╰M╯╰N╯╰O╯╰P╯╰Q╯╰R╯╰S╯╰T╯╰U╯╰V╯╰W╯╰" +
                        "X╯╰Y╯╰Z╯",

                "⫷a⫸⫷b⫸⫷c⫸⫷d⫸⫷e⫸⫷f⫸⫷g⫸⫷h⫸⫷i⫸⫷j⫸⫷k⫸⫷l⫸⫷m⫸⫷n⫸⫷o⫸⫷p⫸" +
                        "⫷q⫸⫷r⫸⫷s⫸⫷t⫸⫷u⫸⫷v⫸⫷w⫸⫷x⫸⫷y⫸⫷z⫸⫷A⫸⫷B⫸⫷C⫸⫷D⫸⫷E" +
                        "⫸⫷F⫸⫷G⫸⫷H⫸⫷I⫸⫷J⫸⫷K⫸⫷L⫸⫷M⫸⫷N⫸⫷O⫸⫷P⫸⫷Q⫸⫷R⫸⫷S⫸" +
                        "⫷T⫸⫷U⫸⫷V⫸⫷W⫸⫷X⫸⫷Y⫸⫷Z⫸",

                "┋A┋B┋C┋D┋E┋F┋G┋H┋I┋J┋K┋L┋M┋N┋O┋P┋Q┋R┋S┋T┋U┋V┋W┋X┋Y┋Z┋A┋B┋C┋D┋E┋F┋G┋H┋I┋J┋K┋L┋M┋" +
                        "N┋O┋P┋Q┋R┋S┋T┋U┋V┋W┋X┋Y┋Z┋",

                "a\u20e0b\u20e0c\u20e0d\u20e0e\u20e0f\u20e0g\u20e0h\u20e0i\u20e0j\u20e0k\u20e0l\u20e0" +
                        "m\u20e0n\u20e0o\u20e0p\u20e0q\u20e0r\u20e0s\u20e0t\u20e0u\u20e0v\u20e0w\u20e0" +
                        "x\u20e0y\u20e0z\u20e0a\u20e0b\u20e0c\u20e0d\u20e0e\u20e0f\u20e0g\u20e0h\u20e0" +
                        "i\u20e0j\u20e0k\u20e0l\u20e0m\u20e0n\u20e0o\u20e0p\u20e0q\u20e0r\u20e0s\u20e0" +
                        "t\u20e0u\u20e0v\u20e0w\u20e0x\u20e0y\u20e0z\u20e0",

                "\u0e56\u06e3\u06dcA\u0e56\u06e3\u06dcB\u0e56\u06e3\u06dcC\u0e56\u06e3\u06dcD\u0e56" +
                        "\u06e3\u06dcE\u0e56\u06e3\u06dcF\u0e56\u06e3\u06dcG\u0e56\u06e3\u06dcH\u0e56" +
                        "\u06e3\u06dcI\u0e56\u06e3\u06dcJ\u0e56\u06e3\u06dcK\u0e56\u06e3\u06dcL\u0e56" +
                        "\u06e3\u06dcM\u0e56\u06e3\u06dcN\u0e56\u06e3\u06dcO\u0e56\u06e3\u06dcP\u0e56" +
                        "\u06e3\u06dcQ\u0e56\u06e3\u06dcR\u0e56\u06e3\u06dcS\u0e56\u06e3\u06dcT\u0e56" +
                        "\u06e3\u06dcU\u0e56\u06e3\u06dcV\u0e56\u06e3\u06dcW\u0e56\u06e3\u06dcX\u0e56" +
                        "\u06e3\u06dcY\u0e56\u06e3\u06dcZ\u0e56\u06e3\u06dcA\u0e56\u06e3\u06dcB\u0e56" +
                        "\u06e3\u06dcC\u0e56\u06e3\u06dcD\u0e56\u06e3\u06dcE\u0e56\u06e3\u06dcF\u0e56" +
                        "\u06e3\u06dcG\u0e56\u06e3\u06dcH\u0e56\u06e3\u06dcI\u0e56\u06e3\u06dcJ\u0e56" +
                        "\u06e3\u06dcK\u0e56\u06e3\u06dcL\u0e56\u06e3\u06dcM\u0e56\u06e3\u06dcN\u0e56" +
                        "\u06e3\u06dcO\u0e56\u06e3\u06dcP\u0e56\u06e3\u06dcQ\u0e56\u06e3\u06dcR\u0e56" +
                        "\u06e3\u06dcS\u0e56\u06e3\u06dcT\u0e56\u06e3\u06dcU\u0e56\u06e3\u06dcV\u0e56" +
                        "\u06e3\u06dcW\u0e56\u06e3\u06dcX\u0e56\u06e3\u06dcY\u0e56\u06e3\u06dcZ",
        };

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

    private ArrayList<Encoder> mEncoders;

    public ArrayEffectEncoder() {
        ArrayEffectFactory factory = new ArrayEffectFactory();
        mEncoders = factory.getEncoders();
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

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return null;
    }
}
