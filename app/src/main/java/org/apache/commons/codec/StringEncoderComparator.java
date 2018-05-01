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

import java.util.Comparator;

/**
 * Compares Strings using a {@link StringEncoder}. This comparator is used to sort Strings by an encoding scheme such as
 * Soundex, Metaphone, etc. This class can come in handy if one need to sort Strings by an encoded form of a name such
 * as Soundex.
 *
 * <p>This class is immutable and thread-safe.</p>
 *
 * @version $Id: StringEncoderComparator.java 1468177 2013-04-15 18:35:15Z ggregory $
 */
@SuppressWarnings("rawtypes")
// TODO ought to implement Comparator<String> but that's not possible whilst maintaining binary compatibility.
public class StringEncoderComparator implements Comparator {

    /**
     * Internal encoder instance.
     */
    private final StringEncoder stringEncoder;

    /**
     * Constructs a new instance.
     *
     * @deprecated Creating an instance without a {@link StringEncoder} leads to a {@link NullPointerException}. Will be
     *             removed in 2.0.
     */
    @Deprecated
    public StringEncoderComparator() {
        this.stringEncoder = null; // Trying to use this will cause things to break
    }

    /**
     * Constructs a new instance with the given algorithm.
     *
     * @param stringEncoder
     *            the StringEncoder used for comparisons.
     */
    public StringEncoderComparator(final StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }

    /**
     * Compares two strings based not on the strings themselves, but on an encoding of the two strings using the
     * StringEncoder this Comparator was created with.
     *
     * If an {@link EncoderException} is encountered, return <code>0</code>.
     *
     * @param o1
     *            the object to compare
     * @param o2
     *            the object to compare to
     * @return the Comparable.compareTo() return code or 0 if an encoding error was caught.
     * @see Comparable
     */
    @Override
    public int compare(final Object o1, final Object o2) {

        int compareCode = 0;

        try {
            @SuppressWarnings("unchecked") // May fail with CCE if encode returns something that is not Comparable
            // However this was always the case.
            final Comparable<Comparable<?>> s1 = (Comparable<Comparable<?>>) this.stringEncoder.encode(o1);
            final Comparable<?> s2 = (Comparable<?>) this.stringEncoder.encode(o2);
            compareCode = s1.compareTo(s2);
        } catch (final EncoderException ee) {
            compareCode = 0;
        }
        return compareCode;
    }

}
