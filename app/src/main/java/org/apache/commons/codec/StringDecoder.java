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

package org.apache.commons.codec;

/**
 * Defines common decoding methods for String decoders.
 *
 * @version $Id: StringDecoder.java 1379145 2012-08-30 21:02:52Z tn $
 */
public interface StringDecoder extends FDecoder {

    /**
     * Decodes a String and returns a String.
     *
     * @param source
     *            the String to decode
     * @return the encoded String
     * @throws DecoderException
     *             thrown if there is an error condition during the Encoding process.
     */
    String decode(String source) throws DecoderException;
}

