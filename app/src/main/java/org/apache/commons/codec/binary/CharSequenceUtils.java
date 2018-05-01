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
package org.apache.commons.codec.binary;

/**
 * <p>
 * Operations on {@link CharSequence} that are <code>null</code> safe.
 * </p>
 * <p>
 * Copied from Apache Commons Lang r1586295 on April 10, 2014 (day of 3.3.2 release).
 * </p>
 *
 * @see CharSequence
 * @since 1.10
 */
public class CharSequenceUtils {

    /**
     * Green implementation of regionMatches.
     *
     * @param cs
     *            the <code>CharSequence</code> to be processed
     * @param ignoreCase
     *            whether or not to be case insensitive
     * @param thisStart
     *            the index to start on the <code>cs</code> CharSequence
     * @param substring
     *            the <code>CharSequence</code> to be looked for
     * @param start
     *            the index to start on the <code>substring</code> CharSequence
     * @param length
     *            character length of the region
     * @return whether the region matched
     */
    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
            final CharSequence substring, final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2) &&
                    Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }
}
