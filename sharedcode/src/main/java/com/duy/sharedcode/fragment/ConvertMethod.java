/*
 * Copyright (c) 2017 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.sharedcode.fragment;

public enum ConvertMethod {
    ASCII(0),
    BINARY(1),
    HEX(2),
    OCTAL(3),
    REVERSER(4),
    UPPER(5),
    LOWER(6),
    UPSIDE_DOWNSIDE(7),
    SUPPER_SCRIPT(8),
    SUB_SCRIPT(9),
    MORSE_CODE(10),
    BASE_64(11),
    ZALGO(12),
    BASE32(13),
    MD5(14),
    SHA_2(15),
    URL(16);

    private int i;

    ConvertMethod(int i) {
        this.i = i;
    }
}