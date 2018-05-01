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

package org.apache.commons.codec.language;

/**
 * Encodes a string into a Caverphone 2.0 value.
 *
 * This is an algorithm created by the Caversham Project at the University of Otago. It implements the Caverphone 2.0
 * algorithm:
 *
 * @version $Id: Caverphone.java 1075947 2011-03-01 17:56:14Z ggregory $
 * @see <a href="http://en.wikipedia.org/wiki/Caverphone">Wikipedia - Caverphone</a>
 * @see <a href="http://caversham.otago.ac.nz/files/working/ctp150804.pdf">Caverphone 2.0 specification</a>
 * @since 1.5
 *
 * <p>This class is immutable and thread-safe.</p>
 */
public class Caverphone2 extends AbstractCaverphone {

    private static final String TEN_1 = "1111111111";

    /**
     * Encodes the given String into a Caverphone 2.0 value.
     *
     * @param source
     *            String the source string
     * @return A caverphone code for the given String
     */
    @Override
    public String encode(final String source) {
        String txt = source;
        if (txt == null || txt.length() == 0) {
            return TEN_1;
        }

        // 1. Convert to lowercase
        txt = txt.toLowerCase(java.util.Locale.ENGLISH);

        // 2. Remove anything not A-Z
        txt = txt.replaceAll("[^a-z]", "");

        // 2.5. Remove final e
        txt = txt.replaceAll("e$", ""); // 2.0 only

        // 3. Handle various start options
        txt = txt.replaceAll("^cough", "cou2f");
        txt = txt.replaceAll("^rough", "rou2f");
        txt = txt.replaceAll("^tough", "tou2f");
        txt = txt.replaceAll("^enough", "enou2f"); // 2.0 only
        txt = txt.replaceAll("^trough", "trou2f"); // 2.0 only
                                                   // note the spec says ^enough here again, c+p error I assume
        txt = txt.replaceAll("^gn", "2n");

        // End
        txt = txt.replaceAll("mb$", "m2");

        // 4. Handle replacements
        txt = txt.replaceAll("cq", "2q");
        txt = txt.replaceAll("ci", "si");
        txt = txt.replaceAll("ce", "se");
        txt = txt.replaceAll("cy", "sy");
        txt = txt.replaceAll("tch", "2ch");
        txt = txt.replaceAll("c", "k");
        txt = txt.replaceAll("q", "k");
        txt = txt.replaceAll("x", "k");
        txt = txt.replaceAll("v", "f");
        txt = txt.replaceAll("dg", "2g");
        txt = txt.replaceAll("tio", "sio");
        txt = txt.replaceAll("tia", "sia");
        txt = txt.replaceAll("d", "t");
        txt = txt.replaceAll("ph", "fh");
        txt = txt.replaceAll("b", "p");
        txt = txt.replaceAll("sh", "s2");
        txt = txt.replaceAll("z", "s");
        txt = txt.replaceAll("^[aeiou]", "A");
        txt = txt.replaceAll("[aeiou]", "3");
        txt = txt.replaceAll("j", "y"); // 2.0 only
        txt = txt.replaceAll("^y3", "Y3"); // 2.0 only
        txt = txt.replaceAll("^y", "A"); // 2.0 only
        txt = txt.replaceAll("y", "3"); // 2.0 only
        txt = txt.replaceAll("3gh3", "3kh3");
        txt = txt.replaceAll("gh", "22");
        txt = txt.replaceAll("g", "k");
        txt = txt.replaceAll("s+", "S");
        txt = txt.replaceAll("t+", "T");
        txt = txt.replaceAll("p+", "P");
        txt = txt.replaceAll("k+", "K");
        txt = txt.replaceAll("f+", "F");
        txt = txt.replaceAll("m+", "M");
        txt = txt.replaceAll("n+", "N");
        txt = txt.replaceAll("w3", "W3");
        txt = txt.replaceAll("wh3", "Wh3");
        txt = txt.replaceAll("w$", "3"); // 2.0 only
        txt = txt.replaceAll("w", "2");
        txt = txt.replaceAll("^h", "A");
        txt = txt.replaceAll("h", "2");
        txt = txt.replaceAll("r3", "R3");
        txt = txt.replaceAll("r$", "3"); // 2.0 only
        txt = txt.replaceAll("r", "2");
        txt = txt.replaceAll("l3", "L3");
        txt = txt.replaceAll("l$", "3"); // 2.0 only
        txt = txt.replaceAll("l", "2");

        // 5. Handle removals
        txt = txt.replaceAll("2", "");
        txt = txt.replaceAll("3$", "A"); // 2.0 only
        txt = txt.replaceAll("3", "");

        // 6. put ten 1s on the end
        txt = txt + TEN_1;

        // 7. take the first ten characters as the code
        return txt.substring(0, TEN_1.length());
    }

}
