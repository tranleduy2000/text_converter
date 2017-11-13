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

import junit.framework.TestCase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import static com.duy.sharedcode.codec.ZalgoMiniCodec.convert;

/**
 * Created by Duy on 10-Jul-17.
 */
public class ZalgoCodecTest extends TestCase {
    public void test1() {
        final String zalgoTxt = convert("Hello everyone", true, false, true, true, true);

        try {
            final File fileDir = new File("C:\\github\\text_converter\\sharedcode\\src\\test\\java\\com\\duy\\sharedcode\\tools", "zalgo.txt");
            final Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));

            final String[] lines = zalgoTxt.split("\n");

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                out.append(line).append("\r\n");
                ;
            }

            out.flush();
            out.close();

        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}