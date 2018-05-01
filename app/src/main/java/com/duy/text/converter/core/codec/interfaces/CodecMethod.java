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

package com.duy.text.converter.core.codec.interfaces;

import com.duy.text.converter.core.codec.AsciiCodec;
import com.duy.text.converter.core.codec.AtbashCodec;
import com.duy.text.converter.core.codec.Base32Codec;
import com.duy.text.converter.core.codec.Base64Codec;
import com.duy.text.converter.core.codec.BinaryCodec;
import com.duy.text.converter.core.codec.CaesarCodec;
import com.duy.text.converter.core.codec.HexCodec;
import com.duy.text.converter.core.codec.LowerCaseCodec;
import com.duy.text.converter.core.codec.MorseCodec;
import com.duy.text.converter.core.codec.NatoCodec;
import com.duy.text.converter.core.codec.OctalCodec;
import com.duy.text.converter.core.codec.RandomCaseCodec;
import com.duy.text.converter.core.codec.ReverserCodec;
import com.duy.text.converter.core.codec.Rot13Codec;
import com.duy.text.converter.core.codec.SubscriptCodec;
import com.duy.text.converter.core.codec.SuperscriptCodec;
import com.duy.text.converter.core.codec.URLCodec;
import com.duy.text.converter.core.codec.UnicodeCodec;
import com.duy.text.converter.core.codec.UpperCaseCodec;
import com.duy.text.converter.core.codec.UpsideDownCodec;
import com.duy.text.converter.core.codec.WingdingCodec;
import com.duy.text.converter.core.codec.ZalgoBigCodec;
import com.duy.text.converter.core.codec.ZalgoMiniCodec;
import com.duy.text.converter.core.codec.ZalgoNormalCodec;

public enum CodecMethod {
    ASCII {
        @Override
        public Codec getCodec() {
            return new AsciiCodec();
        }
    },
    BINARY {
        @Override
        public Codec getCodec() {
            return new BinaryCodec();
        }
    },
    HEX {
        @Override
        public Codec getCodec() {
            return new HexCodec();
        }
    },
    OCTAL {
        @Override
        public Codec getCodec() {
            return new OctalCodec();
        }
    },
    REVERSER {
        @Override
        public Codec getCodec() {
            return new ReverserCodec();
        }
    },
    UPPER_CASE {
        @Override
        public Codec getCodec() {
            return new UpperCaseCodec();
        }
    },
    LOWER {
        @Override
        public Codec getCodec() {
            return new LowerCaseCodec();
        }
    },
    UPSIDE_DOWNSIDE {
        @Override
        public Codec getCodec() {
            return new UpsideDownCodec();
        }
    },
    SUPPER_SCRIPT {
        @Override
        public Codec getCodec() {
            return new SuperscriptCodec();
        }
    },
    SUB_SCRIPT {
        @Override
        public Codec getCodec() {
            return new SubscriptCodec();
        }
    },
    MORSE_CODE {
        @Override
        public Codec getCodec() {
            return new MorseCodec();
        }
    },
    ZALGO_MINI {
        @Override
        public Codec getCodec() {
            return new ZalgoMiniCodec();
        }
    },
    ZALGO_NORMAL {
        @Override
        public Codec getCodec() {
            return new ZalgoNormalCodec();
        }
    },
    ZALGO_BIG {
        @Override
        public Codec getCodec() {
            return new ZalgoBigCodec();
        }
    },
    BASE_32 {
        @Override
        public Codec getCodec() {
            return new Base32Codec();
        }
    },
    BASE_64 {
        @Override
        public Codec getCodec() {
            return new Base64Codec();
        }
    },
    URL {
        @Override
        public Codec getCodec() {
            return new URLCodec();
        }
    },
    RANDOM_CASE {
        @Override
        public Codec getCodec() {
            return new RandomCaseCodec();
        }
    },
    CAESAR {
        @Override
        public Codec getCodec() {
            return new CaesarCodec();
        }
    },
    ATBASH {
        @Override
        public Codec getCodec() {
            return new AtbashCodec();
        }
    },
    ROT_13 {
        @Override
        public Codec getCodec() {
            return new Rot13Codec();
        }
    },
    NATO {
        @Override
        public Codec getCodec() {
            return new NatoCodec();
        }
    },
    UNICODE {
        @Override
        public Codec getCodec() {
            return new UnicodeCodec();
        }
    },
    WINGDING {
        @Override
        public Codec getCodec() {
            return new WingdingCodec();
        }
    };

    public abstract Codec getCodec();
}