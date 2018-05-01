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

package com.duy.common.purchase;

import android.content.Context;
import android.support.annotation.Nullable;

import com.duy.common.utils.DLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by Duy on 9/15/2017.
 */

public class FileUtil {

    private static final String TAG = "FileUtil";
    private static final String LICENSE_FILE_NAME = "license";

    public static boolean existFile(String file) {
        try {
            return new File(file).exists();
        } catch (Exception e) {
            return false;
        }
    }

    @Nullable
    public static String readFile(String file) {
        return readFile(new File(file));
    }

    @Nullable
    public static String readFile(File file) {
        StringBuilder s = null;
        FileReader fileReader = null;
        BufferedReader buf = null;
        try {
            fileReader = new FileReader(file);
            buf = new BufferedReader(fileReader);

            String line;
            s = new StringBuilder();
            while ((line = buf.readLine()) != null) s.append(line).append("\n");
        } catch (FileNotFoundException ignored) {
            DLog.e(TAG, "File does not exist " + file);
        } catch (IOException e) {
            DLog.e(TAG, "Failed to read " + file);
        } finally {
            try {
                if (fileReader != null) fileReader.close();
                if (buf != null) buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s == null ? null : s.toString().trim();
    }

    public static void saveLicence(Context context) {
        String content = StringXor.encode(Installation.id(context));
        File file = new File(context.getCacheDir(), LICENSE_FILE_NAME);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write licenced cache
     */
    public static boolean licenseCached(Context context) {
        File file = new File(context.getCacheDir(), LICENSE_FILE_NAME);
        if (file.exists()) {
            String content = readFile(file);
            if (content != null && !content.isEmpty()) {
                content = StringXor.decode(content);
                if (content.equals(Installation.id(context))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean clearLicence(Context context) {
        File file = new File(context.getCacheDir(), LICENSE_FILE_NAME);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
