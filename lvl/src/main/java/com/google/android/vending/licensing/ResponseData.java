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

import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * ResponseData from licensing server.
 */
public class ResponseData {

    public int responseCode;
    public int nonce;
    public String packageName;
    public String versionCode;
    public String userId;
    public long timestamp;
    /** Response-specific data. */
    public String extra;

    /**
     * Parses response string into ResponseData.
     *
     * @param responseData response data string
     * @throws IllegalArgumentException upon parsing error
     * @return ResponseData object
     */
    public static ResponseData parse(String responseData) {
        // Must parse out main response data and response-specific data.
    	int index = responseData.indexOf(':');
    	String mainData, extraData;
    	if ( -1 == index ) {
    		mainData = responseData;
    		extraData = "";
    	} else {
    		mainData = responseData.substring(0, index);
    		extraData = index >= responseData.length() ? "" : responseData.substring(index+1);
    	}

        String [] fields = TextUtils.split(mainData, Pattern.quote("|"));
        if (fields.length < 6) {
            throw new IllegalArgumentException("Wrong number of fields.");
        }

        ResponseData data = new ResponseData();
        data.extra = extraData;
        data.responseCode = Integer.parseInt(fields[0]);
        data.nonce = Integer.parseInt(fields[1]);
        data.packageName = fields[2];
        data.versionCode = fields[3];
        // Application-specific user identifier.
        data.userId = fields[4];
        data.timestamp = Long.parseLong(fields[5]);

        return data;
    }

    @Override
    public String toString() {
        return TextUtils.join("|", new Object [] { responseCode, nonce, packageName, versionCode,
            userId, timestamp });
    }
}
