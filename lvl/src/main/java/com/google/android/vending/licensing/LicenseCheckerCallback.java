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
 * Callback for the license checker library.
 * <p>
 * Upon checking with the Market server and conferring with the {@link Policy},
 * the library calls the appropriate callback method to communicate the result.
 * <p>
 * <b>The callback does not occur in the original checking thread.</b> Your
 * application should post to the appropriate handling thread or lock
 * accordingly.
 * <p>
 * The reason that is passed back with allow/dontAllow is the base status handed
 * to the policy for allowed/disallowing the license. Policy.RETRY will call
 * allow or dontAllow depending on other statistics associated with the policy,
 * while in most cases Policy.NOT_LICENSED will call dontAllow and
 * Policy.LICENSED will Allow.
 */
public interface LicenseCheckerCallback {

    /**
     * Allow use. App should proceed as normal.
     * 
     * @param reason Policy.LICENSED or Policy.RETRY typically. (although in
     *            theory the policy can return Policy.NOT_LICENSED here as well)
     */
    public void allow(int reason);

    /**
     * Don't allow use. App should inform user and take appropriate action.
     * 
     * @param reason Policy.NOT_LICENSED or Policy.RETRY. (although in theory
     *            the policy can return Policy.LICENSED here as well ---
     *            perhaps the call to the LVL took too long, for example)
     */
    public void dontAllow(int reason);

    /** Application error codes. */
    public static final int ERROR_INVALID_PACKAGE_NAME = 1;
    public static final int ERROR_NON_MATCHING_UID = 2;
    public static final int ERROR_NOT_MARKET_MANAGED = 3;
    public static final int ERROR_CHECK_IN_PROGRESS = 4;
    public static final int ERROR_INVALID_PUBLIC_KEY = 5;
    public static final int ERROR_MISSING_PERMISSION = 6;

    /**
     * Error in application code. Caller did not call or set up license checker
     * correctly. Should be considered fatal.
     */
    public void applicationError(int errorCode);
}
