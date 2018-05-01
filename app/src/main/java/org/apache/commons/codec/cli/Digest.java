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
package org.apache.commons.codec.cli;

import org.apache.commons.codec.binary.FHex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Locale;

/**
 * A minimal command line to run digest over files, directories or a string
 *
 * @see #main(String[])
 * @since 1.11
 */
public class Digest {

    /**
     * Runs the digest algorithm in {@code args[0]} on the file in {@code args[1]}. If there is no {@code args[1]}, use
     * standard input.
     *
     * <p>
     * The algorithm can also be {@code ALL} or {@code *} to output one line for each known algorithm.
     * </p>
     *
     * @param args
     *            {@code args[0]} is one of {@link MessageDigestAlgorithms} name,
     *            {@link MessageDigest} name, {@code ALL}, or {@code *}.
     *            {@code args[1+]} is a FILE/DIRECTORY/String.
     * @throws IOException if an error occurs
     */
    public static void main(final String[] args) throws IOException {
        new Digest(args).run();
    }

    private final String algorithm;
    private final String[] args;
    private final String[] inputs;

    private Digest(final String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("args");
        }
        if (args.length == 0) {
            throw new IllegalArgumentException(
                    String.format("Usage: java %s [algorithm] [FILE|DIRECTORY|string] ...", Digest.class.getName()));
        }
        this.args = args;
        algorithm = args[0];
        if (args.length <= 1) {
            inputs = null;
        } else {
            inputs = new String[args.length -1];
            System.arraycopy(args, 1, inputs, 0, inputs.length);
        }
    }

    private void println(final String prefix, final byte[] digest) {
        println(prefix, digest, null);
    }

    private void println(final String prefix, final byte[] digest, final String fileName) {
        // The standard appears to be to print
        // hex, space, then either space or '*' followed by filename
        // where '*' is used for binary files
        // shasum(1) has a -b option which generates " *" separator
        // we don't distinguish binary files at present
        System.out.println(prefix + FHex.encodeHexString(digest) + (fileName != null ? "  " + fileName : ""));
    }

    private void run() throws IOException {
        if (algorithm.equalsIgnoreCase("ALL") || algorithm.equals("*")) {
            run(MessageDigestAlgorithms.values());
            return;
        }
        final MessageDigest messageDigest = DigestUtils.getDigest(algorithm, null);
        if (messageDigest != null) {
            run("", messageDigest);
        } else {
            run("", DigestUtils.getDigest(algorithm.toUpperCase(Locale.ROOT)));
        }
    }

    private void run(final String[] digestAlgorithms) throws IOException {
        for (final String messageDigestAlgorithm : digestAlgorithms) {
            if (DigestUtils.isAvailable(messageDigestAlgorithm)) {
                run(messageDigestAlgorithm + " ", messageDigestAlgorithm);
            }
        }
    }

    private void run(final String prefix, final MessageDigest messageDigest) throws IOException {
        if (inputs == null) {
            println(prefix, DigestUtils.digest(messageDigest, System.in));
            return;
        }
        for(final String source : inputs) {
            final File file = new File(source);
            if (file.isFile()) {
                println(prefix, DigestUtils.digest(messageDigest, file), source);
            } else if (file.isDirectory()) {
                final File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    run(prefix, messageDigest, listFiles);
                }
            } else {
                // use the default charset for the command-line parameter
                final byte[] bytes = source.getBytes(Charset.defaultCharset());
                println(prefix, DigestUtils.digest(messageDigest, bytes));
            }
        }
    }

    private void run(final String prefix, final MessageDigest messageDigest, final File[] files) throws IOException {
        for (final File file : files) {
            if (file.isFile()) {
                println(prefix, DigestUtils.digest(messageDigest, file), file.getName());
            }
        }
    }

    private void run(final String prefix, final String messageDigestAlgorithm) throws IOException {
        run(prefix, DigestUtils.getDigest(messageDigestAlgorithm));
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), Arrays.toString(args));
    }
}
