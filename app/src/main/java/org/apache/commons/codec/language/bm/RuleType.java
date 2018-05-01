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
 * Types of rule.
 *
 * @since 1.6
 * @version $Id: RuleType.java 1542813 2013-11-17 20:52:32Z tn $
 */
public enum RuleType {

    /** Approximate rules, which will lead to the largest number of phonetic interpretations. */
    APPROX("approx"),
    /** Exact rules, which will lead to a minimum number of phonetic interpretations. */
    EXACT("exact"),
    /** For internal use only. Please use {@link #APPROX} or {@link #EXACT}. */
    RULES("rules");

    private final String name;

    RuleType(final String name) {
        this.name = name;
    }

    /**
     * Gets the rule name.
     *
     * @return the rule name.
     */
    public String getName() {
        return this.name;
    }

}
