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

package flynn.tim.ciphersolver.vigenere;

/**
 * Created by Tim on 3/22/2015.
 * Code modified from source provided at: http://rosettacode.org/wiki/Vigen%C3%A8re_cipher#Java
 */
class VigenereCipher implements ICipher {

    static String encrypt(String text, final String key) {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            res.append((char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A'));
            j = ++j % key.length();
        }
        return res.toString();
    }

    static String decrypt(String text, final String key) {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            res.append((char) ((c - key.charAt(j) + 26) % 26 + 'A'));
            j = ++j % key.length();
        }
        return res.toString();
    }
}
