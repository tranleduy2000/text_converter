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

package com.duy.sharedcode.tools;

import com.duy.sharedcode.stylist.StylistGenerator;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Duy on 11-Jul-17.
 */
public class ArrayEffectTest extends TestCase {

    public void test1() throws IOException {
        StylistGenerator effectEncoder = new StylistGenerator();
        ArrayList<String> generate = effectEncoder.generate("the quick brown fox jumps over the lazy dog");

        final File fileDir = new File("C:\\github\\text_converter\\sharedcode\\src\\test\\java\\com\\d" +
                "uy\\sharedcode\\tools", "test1.txt");

        FileOutputStream fileOutputStream = new FileOutputStream(fileDir);
        for (String s : generate) {
            fileOutputStream.write(s.getBytes());
            fileOutputStream.write('\n');
        }
        fileOutputStream.close();
    }

    public void testUnicode() throws IOException {
        StylistGenerator effectEncoder = new StylistGenerator();
        ArrayList<String> generate = effectEncoder.generate("Xin chào tất cả các bạn");

        final File fileDir = new File("C:\\github\\text_converter\\sharedcode\\src\\test\\java\\com\\d" +
                "uy\\sharedcode\\tools", "testUnicode.txt");

        FileOutputStream fileOutputStream = new FileOutputStream(fileDir);
        for (String s : generate) {
            fileOutputStream.write(s.getBytes());
            fileOutputStream.write('\n');
        }
        fileOutputStream.close();
    }
}