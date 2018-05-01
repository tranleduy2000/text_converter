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

package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

/**
 * Utility methods for this package.
 *
 * <p>This class is immutable and thread-safe.</p>
 *
 * @version $Id: Utils.java 1811344 2017-10-06 15:19:57Z ggregory $
 * @since 1.4
 */
class Utils {

    /**
     * Radix used in encoding and decoding.
     */
    private static final int RADIX = 16;

    /**
     * Returns the numeric value of the character <code>b</code> in radix 16.
     *
     * @param b
     *            The byte to be converted.
     * @return The numeric value represented by the character in radix 16.
     *
     * @throws DecoderException
     *             Thrown when the byte is not valid per {@link Character#digit(char,int)}
     */
    static int digit16(final byte b) throws DecoderException {
        final int i = Character.digit((char) b, RADIX);
        if (i == -1) {
            throw new DecoderException("Invalid URL encoding: not a valid digit (radix " + RADIX + "): " + b);
        }
        return i;
    }

    /**
     * Returns the upper case hex digit of the lower 4 bits of the int.
     *
     * @param b the input int
     * @return the upper case hex digit of the lower 4 bits of the int.
     */
    static char hexDigit(final int b) {
        return Character.toUpperCase(Character.forDigit(b & 0xF, RADIX));
    }

}
