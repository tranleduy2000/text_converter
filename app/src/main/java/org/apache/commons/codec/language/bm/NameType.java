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

package org.apache.commons.codec.language.bm;

/**
 * Supported types of names. Unless you are matching particular family names, use {@link #GENERIC}. The
 * <code>GENERIC</code> NameType should work reasonably well for non-name words. The other encodings are
 * specifically tuned to family names, and may not work well at all for general text.
 *
 * @since 1.6
 * @version $Id: NameType.java 1429868 2013-01-07 16:08:05Z ggregory $
 */
public enum NameType {

    /** Ashkenazi family names */
    ASHKENAZI("ash"),

    /** Generic names and words */
    GENERIC("gen"),

    /** Sephardic family names */
    SEPHARDIC("sep");

    private final String name;

    NameType(final String name) {
        this.name = name;
    }

    /**
     * Gets the short version of the name type.
     *
     * @return the NameType short string
     */
    public String getName() {
        return this.name;
    }
}
