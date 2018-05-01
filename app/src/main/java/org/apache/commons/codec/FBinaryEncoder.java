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
 * Defines common encoding methods for byte array encoders.
 *
 * @version $Id: BinaryEncoder.java 1379145 2012-08-30 21:02:52Z tn $
 */
public interface FBinaryEncoder extends FEncoder {

    /**
     * Encodes a byte array and return the encoded data as a byte array.
     *
     * @param source
     *            Data to be encoded
     * @return A byte array containing the encoded data
     * @throws EncoderException
     *             thrown if the Encoder encounters a failure condition during the encoding process.
     */
    byte[] encode(byte[] source) throws EncoderException;
}

