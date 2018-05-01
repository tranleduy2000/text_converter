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
 * Policy used by {@link LicenseChecker} to determine whether a user should have
 * access to the application.
 */
public interface Policy {

    /**
     * Change these values to make it more difficult for tools to automatically
     * strip LVL protection from your APK.
     */

    /**
     * LICENSED means that the server returned back a valid license response
     */
    public static final int LICENSED = 0x0100;
    /**
     * NOT_LICENSED means that the server returned back a valid license response
     * that indicated that the user definitively is not licensed
     */
    public static final int NOT_LICENSED = 0x0231;
    /**
     * RETRY means that the license response was unable to be determined ---
     * perhaps as a result of faulty networking
     */
    public static final int RETRY = 0x0123;

    /**
     * Provide results from contact with the license server. Retry counts are
     * incremented if the current value of response is RETRY. Results will be
     * used for any future policy decisions.
     * 
     * @param response the result from validating the server response
     * @param rawData the raw server response data, can be null for RETRY
     */
    void processServerResponse(int response, ResponseData rawData);

    /**
     * Check if the user should be allowed access to the application.
     */
    boolean allowAccess();
}
