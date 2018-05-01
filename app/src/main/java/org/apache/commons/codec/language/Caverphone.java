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
 * Encodes a string into a Caverphone 2.0 value. Delegate to a {@link Caverphone2} instance.
 *
 * This is an algorithm created by the Caversham Project at the University of Otago. It implements the Caverphone 2.0
 * algorithm:
 *
 * @version $Id: Caverphone.java 1079535 2011-03-08 20:54:37Z ggregory $
 * @see <a href="http://en.wikipedia.org/wiki/Caverphone">Wikipedia - Caverphone</a>
 * @see <a href="http://caversham.otago.ac.nz/files/working/ctp150804.pdf">Caverphone 2.0 specification</a>
 * @since 1.4
 * @deprecated 1.5 Replaced by {@link Caverphone2}, will be removed in 2.0.
 */
@Deprecated
public class Caverphone implements StringEncoder {

    /**
     * Delegate to a {@link Caverphone2} instance to avoid code duplication.
     */
    final private Caverphone2 encoder = new Caverphone2();

    /**
     * Creates an instance of the Caverphone encoder
     */
    public Caverphone() {
        super();
    }

    /**
     * Encodes the given String into a Caverphone value.
     *
     * @param source
     *            String the source string
     * @return A caverphone code for the given String
     */
    public String caverphone(final String source) {
        return this.encoder.encode(source);
    }

    /**
     * Encodes an Object using the caverphone algorithm. This method is provided in order to satisfy the requirements of
     * the Encoder interface, and will throw an EncoderException if the supplied object is not of type java.lang.String.
     *
     * @param obj
     *            Object to encode
     * @return An object (or type java.lang.String) containing the caverphone code which corresponds to the String
     *         supplied.
     * @throws EncoderException
     *             if the parameter supplied is not of type java.lang.String
     */
    @Override
    public Object encode(final Object obj) throws EncoderException {
        if (!(obj instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.caverphone((String) obj);
    }

    /**
     * Encodes a String using the Caverphone algorithm.
     *
     * @param str
     *            String object to encode
     * @return The caverphone code corresponding to the String supplied
     */
    @Override
    public String encode(final String str) {
        return this.caverphone(str);
    }

    /**
     * Tests if the caverphones of two strings are identical.
     *
     * @param str1
     *            First of two strings to compare
     * @param str2
     *            Second of two strings to compare
     * @return <code>true</code> if the caverphones of these strings are identical, <code>false</code> otherwise.
     */
    public boolean isCaverphoneEqual(final String str1, final String str2) {
        return this.caverphone(str1).equals(this.caverphone(str2));
    }

}
