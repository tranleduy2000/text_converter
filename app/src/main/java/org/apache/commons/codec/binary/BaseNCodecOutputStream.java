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

import static org.apache.commons.codec.binary.BaseNCodec.EOF;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.codec.binary.BaseNCodec.Context;

/**
 * Abstract superclass for Base-N output streams.
 * <p>
 * To write the EOF marker without closing the stream, call {@link #eof()} or use an <a
 * href="https://commons.apache.org/proper/commons-io/">Apache Commons IO</a> <a href=
 * "https://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/output/CloseShieldOutputStream.html"
 * >CloseShieldOutputStream</a>.
 * </p>
 *
 * @since 1.5
 * @version $Id: BaseNCodecOutputStream.java 1744727 2016-05-20 12:43:52Z sebb $
 */
public class BaseNCodecOutputStream extends FilterOutputStream {

    private final boolean doEncode;

    private final BaseNCodec baseNCodec;

    private final byte[] singleByte = new byte[1];

    private final Context context = new Context();

    // TODO should this be protected?
    public BaseNCodecOutputStream(final OutputStream out, final BaseNCodec basedCodec, final boolean doEncode) {
        super(out);
        this.baseNCodec = basedCodec;
        this.doEncode = doEncode;
    }

    /**
     * Writes the specified <code>byte</code> to this output stream.
     *
     * @param i
     *            source byte
     * @throws IOException
     *             if an I/O error occurs.
     */
    @Override
    public void write(final int i) throws IOException {
        singleByte[0] = (byte) i;
        write(singleByte, 0, 1);
    }

    /**
     * Writes <code>len</code> bytes from the specified <code>b</code> array starting at <code>offset</code> to this
     * output stream.
     *
     * @param b
     *            source byte array
     * @param offset
     *            where to start reading the bytes
     * @param len
     *            maximum number of bytes to write
     *
     * @throws IOException
     *             if an I/O error occurs.
     * @throws NullPointerException
     *             if the byte array parameter is null
     * @throws IndexOutOfBoundsException
     *             if offset, len or buffer size are invalid
     */
    @Override
    public void write(final byte b[], final int offset, final int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (offset < 0 || len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (offset > b.length || offset + len > b.length) {
            throw new IndexOutOfBoundsException();
        } else if (len > 0) {
            if (doEncode) {
                baseNCodec.encode(b, offset, len, context);
            } else {
                baseNCodec.decode(b, offset, len, context);
            }
            flush(false);
        }
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be written out to the stream. If propagate is
     * true, the wrapped stream will also be flushed.
     *
     * @param propagate
     *            boolean flag to indicate whether the wrapped OutputStream should also be flushed.
     * @throws IOException
     *             if an I/O error occurs.
     */
    private void flush(final boolean propagate) throws IOException {
        final int avail = baseNCodec.available(context);
        if (avail > 0) {
            final byte[] buf = new byte[avail];
            final int c = baseNCodec.readResults(buf, 0, avail, context);
            if (c > 0) {
                out.write(buf, 0, c);
            }
        }
        if (propagate) {
            out.flush();
        }
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be written out to the stream.
     *
     * @throws IOException
     *             if an I/O error occurs.
     */
    @Override
    public void flush() throws IOException {
        flush(true);
    }

    /**
     * Closes this output stream and releases any system resources associated with the stream.
     * <p>
     * To write the EOF marker without closing the stream, call {@link #eof()} or use an
     * <a href="https://commons.apache.org/proper/commons-io/">Apache Commons IO</a> <a href=
     * "https://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/output/CloseShieldOutputStream.html"
     * >CloseShieldOutputStream</a>.
     * </p>
     *
     * @throws IOException
     *             if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        eof();
        flush();
        out.close();
    }

    /**
     * Writes EOF.
     *
     * @throws IOException
     *             if an I/O error occurs.
     * @since 1.11
     */
    public void eof() throws IOException {
        // Notify encoder of EOF (-1).
        if (doEncode) {
            baseNCodec.encode(singleByte, 0, EOF, context);
        } else {
            baseNCodec.decode(singleByte, 0, EOF, context);
        }
    }

}
