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
 * Provides Base64 encoding and decoding in a streaming fashion (unlimited size). When encoding the default lineLength
 * is 76 characters and the default lineEnding is CRLF, but these can be overridden by using the appropriate
 * constructor.
 * <p>
 * The default behaviour of the Base64InputStream is to DECODE, whereas the default behaviour of the Base64OutputStream
 * is to ENCODE, but this behaviour can be overridden by using a different constructor.
 * </p>
 * <p>
 * This class implements section <cite>6.8. Base64 Content-Transfer-Encoding</cite> from RFC 2045 <cite>Multipurpose
 * Internet Mail Extensions (MIME) Part One: Format of Internet Message Bodies</cite> by Freed and Borenstein.
 * </p>
 * <p>
 * Since this class operates directly on byte streams, and not character streams, it is hard-coded to only encode/decode
 * character encodings which are compatible with the lower 127 ASCII chart (ISO-8859-1, Windows-1252, UTF-8, etc).
 * </p>
 *
 * @version $Id: Base64InputStream.java 1634429 2014-10-27 01:08:36Z ggregory $
 * @see <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>
 * @since 1.4
 */
public class Base64InputStream extends BaseNCodecInputStream {

    /**
     * Creates a Base64InputStream such that all data read is Base64-decoded from the original provided InputStream.
     *
     * @param in
     *            InputStream to wrap.
     */
    public Base64InputStream(final InputStream in) {
        this(in, false);
    }

    /**
     * Creates a Base64InputStream such that all data read is either Base64-encoded or Base64-decoded from the original
     * provided InputStream.
     *
     * @param in
     *            InputStream to wrap.
     * @param doEncode
     *            true if we should encode all data read from us, false if we should decode.
     */
    public Base64InputStream(final InputStream in, final boolean doEncode) {
        super(in, new Base64(false), doEncode);
    }

    /**
     * Creates a Base64InputStream such that all data read is either Base64-encoded or Base64-decoded from the original
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
    public Base64InputStream(final InputStream in, final boolean doEncode,
                             final int lineLength, final byte[] lineSeparator) {
        super(in, new Base64(lineLength, lineSeparator), doEncode);
    }
}
