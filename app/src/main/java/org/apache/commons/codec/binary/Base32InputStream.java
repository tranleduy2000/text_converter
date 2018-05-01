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

import java.io.InputStream;

/**
 * Provides Base32 encoding and decoding in a streaming fashion (unlimited size). When encoding the default lineLength
 * is 76 characters and the default lineEnding is CRLF, but these can be overridden by using the appropriate
 * constructor.
 * <p>
 * The default behaviour of the Base32InputStream is to DECODE, whereas the default behaviour of the Base32OutputStream
 * is to ENCODE, but this behaviour can be overridden by using a different constructor.
 * </p>
 * <p>
 * Since this class operates directly on byte streams, and not character streams, it is hard-coded to only encode/decode
 * character encodings which are compatible with the lower 127 ASCII chart (ISO-8859-1, Windows-1252, UTF-8, etc).
 * </p>
 *
 * @version $Id: Base32InputStream.java 1586299 2014-04-10 13:50:21Z ggregory $
 * @see <a href="http://www.ietf.org/rfc/rfc4648.txt">RFC 4648</a>
 * @since 1.5
 */
public class Base32InputStream extends BaseNCodecInputStream {

    /**
     * Creates a Base32InputStream such that all data read is Base32-decoded from the original provided InputStream.
     *
     * @param in
     *            InputStream to wrap.
     */
    public Base32InputStream(final InputStream in) {
        this(in, false);
    }

    /**
     * Creates a Base32InputStream such that all data read is either Base32-encoded or Base32-decoded from the original
     * provided InputStream.
     *
     * @param in
     *            InputStream to wrap.
     * @param doEncode
     *            true if we should encode all data read from us, false if we should decode.
     */
    public Base32InputStream(final InputStream in, final boolean doEncode) {
        super(in, new Base32(false), doEncode);
    }

    /**
     * Creates a Base32InputStream such that all data read is either Base32-encoded or Base32-decoded from the original
     * provided InputStream.
     *
     * @param in
     *            InputStream to wrap.
     * @param doEncode
     *            true if we should encode all data read from us, false if we should decode.
     * @param lineLength
     *            If doEncode is true, each line of encoded data will contain lineLength characters (rounded down to
     *            nearest multiple of 4). If lineLength &lt;= 0, the encoded data is not divided into lines. If doEncode
     *            is false, lineLength is ignored.
     * @param lineSeparator
     *            If doEncode is true, each line of encoded data will be terminated with this byte sequence (e.g. \r\n).
     *            If lineLength &lt;= 0, the lineSeparator is not used. If doEncode is false lineSeparator is ignored.
     */
    public Base32InputStream(final InputStream in, final boolean doEncode,
                             final int lineLength, final byte[] lineSeparator) {
        super(in, new Base32(lineLength, lineSeparator), doEncode);
    }

}
