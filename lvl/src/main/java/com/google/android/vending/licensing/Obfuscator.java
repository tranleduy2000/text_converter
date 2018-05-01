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

package com.google.android.vending.licensing;

/**
 * Interface used as part of a {@link Policy} to allow application authors to obfuscate
 * licensing data that will be stored into a SharedPreferences file.
 * <p>
 * Any transformation scheme must be reversable. Implementing classes may optionally implement an
 * integrity check to further prevent modification to preference data. Implementing classes
 * should use device-specific information as a key in the obfuscation algorithm to prevent
 * obfuscated preferences from being shared among devices.
 */
public interface Obfuscator {

    /**
     * Obfuscate a string that is being stored into shared preferences.
     *
     * @param original The data that is to be obfuscated.
     * @param key The key for the data that is to be obfuscated.
     * @return A transformed version of the original data.
     */
    String obfuscate(String original, String key);

    /**
     * Undo the transformation applied to data by the obfuscate() method.
     *
     * @param original The data that is to be obfuscated.
     * @param key The key for the data that is to be obfuscated.
     * @return A transformed version of the original data.
     * @throws ValidationException Optionally thrown if a data integrity check fails.
     */
    String unobfuscate(String obfuscated, String key) throws ValidationException;
}
