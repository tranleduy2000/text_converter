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

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/**
 * Encodes a string into a Caverphone value.
 *
 * This is an algorithm created by the Caversham Project at the University of Otago. It implements the Caverphone 2.0
 * algorithm:
 *
 * <p>This class is immutable and thread-safe.</p>
 *
 * @version $Id: Caverphone.java 1075947 2011-03-01 17:56:14Z ggregory $
 * @see <a href="http://en.wikipedia.org/wiki/Caverphone">Wikipedia - Caverphone</a>
 * @since 1.5
 */
public abstract class AbstractCaverphone implements StringEncoder {

    /**
     * Creates an instance of the Caverphone encoder
     */
    public AbstractCaverphone() {
        super();
    }

    /**
     * Encodes an Object using the caverphone algorithm. This method is provided in order to satisfy the requirements of
     * the Encoder interface, and will throw an EncoderException if the supplied object is not of type java.lang.String.
     *
     * @param source
     *            Object to encode
     * @return An object (or type java.lang.String) containing the caverphone code which corresponds to the String
     *         supplied.
     * @throws EncoderException
     *             if the parameter supplied is not of type java.lang.String
     */
    @Override
    public Object encode(final Object source) throws EncoderException {
        if (!(source instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.encode((String) source);
    }

    /**
     * Tests if the encodings of two strings are equal.
     *
     * This method might be promoted to a new AbstractStringEncoder superclass.
     *
     * @param str1
     *            First of two strings to compare
     * @param str2
     *            Second of two strings to compare
     * @return <code>true</code> if the encodings of these strings are identical, <code>false</code> otherwise.
     * @throws EncoderException
     *             thrown if there is an error condition during the encoding process.
     */
    public boolean isEncodeEqual(final String str1, final String str2) throws EncoderException {
        return this.encode(str1).equals(this.encode(str2));
    }

}
