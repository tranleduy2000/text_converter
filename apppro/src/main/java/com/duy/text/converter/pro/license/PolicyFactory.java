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

package com.duy.text.converter.pro.license;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ServerManagedPolicy;

/**
 * Created by Duy on 12-Jul-17.
 */

public class PolicyFactory {
    public static Policy createPolicy(@NonNull Context context, @NonNull String packageName) {
        String deviceID = Installation.id(context);
        return new ServerManagedPolicy(context, new AESObfuscator(Key.SALT, packageName, deviceID));
    }
}
