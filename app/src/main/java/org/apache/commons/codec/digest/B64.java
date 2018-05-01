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
package org.apache.commons.codec.digest;

import java.util.Random;

/**
 * Base64 like method to convert binary bytes into ASCII chars.
 *
 * TODO: Can Base64 be reused?
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @version $Id: B64.java 1435550 2013-01-19 14:09:52Z tn $
 * @since 1.7
 */
class B64 {

    /**
     * Table with characters for Base64 transformation.
     */
    static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * Base64 like conversion of bytes to ASCII chars.
     *
     * @param b2
     *            A byte from the result.
     * @param b1
     *            A byte from the result.
     * @param b0
     *            A byte from the result.
     * @param outLen
     *            The number of expected output chars.
     * @param buffer
     *            Where the output chars is appended to.
     */
    static void b64from24bit(final byte b2, final byte b1, final byte b0, final int outLen,
                             final StringBuilder buffer) {
        // The bit masking is necessary because the JVM byte type is signed!
        int w = ((b2 << 16) & 0x00ffffff) | ((b1 << 8) & 0x00ffff) | (b0 & 0xff);
        // It's effectively a "for" loop but kept to resemble the original C code.
        int n = outLen;
        while (n-- > 0) {
            buffer.append(B64T.charAt(w & 0x3f));
            w >>= 6;
        }
    }

    /**
     * Generates a string of random chars from the B64T set.
     *
     * @param num
     *            Number of chars to generate.
     */
    static String getRandomSalt(final int num) {
        final StringBuilder saltString = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            saltString.append(B64T.charAt(new Random().nextInt(B64T.length())));
        }
        return saltString.toString();
    }
}
