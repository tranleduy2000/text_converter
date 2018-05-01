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
 * Provides the highest level of abstraction for Decoders.
 * <p>
 * This is the sister interface of {@link FEncoder}. All Decoders implement this common generic interface.
 * Allows a user to pass a generic Object to any Decoder implementation in the codec package.
 * <p>
 * One of the two interfaces at the center of the codec package.
 *
 * @version $Id: Decoder.java 1379145 2012-08-30 21:02:52Z tn $
 */
public interface FDecoder {

    /**
     * Decodes an "encoded" Object and returns a "decoded" Object. Note that the implementation of this interface will
     * try to cast the Object parameter to the specific type expected by a particular Decoder implementation. If a
     * {@link ClassCastException} occurs this decode method will throw a DecoderException.
     *
     * @param source
     *            the object to decode
     * @return a 'decoded" object
     * @throws DecoderException
     *             a decoder exception can be thrown for any number of reasons. Some good candidates are that the
     *             parameter passed to this method is null, a param cannot be cast to the appropriate type for a
     *             specific encoder.
     */
    Object decode(Object source) throws DecoderException;
}

