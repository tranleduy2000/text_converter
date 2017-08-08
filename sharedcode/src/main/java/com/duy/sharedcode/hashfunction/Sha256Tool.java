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

package com.duy.sharedcode.hashfunction;

import android.support.annotation.NonNull;

import org.apache.commons.codec.digest.Sha2Crypt;

/**
 * Created by Duy on 11-Jul-17.
 */

public class Sha256Tool implements HashFunction {

    @Override
    public String getName() {
        return "SHA-256";
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return Sha2Crypt.sha256Crypt(text.getBytes());
    }


}
