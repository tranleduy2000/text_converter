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
 * Non-caching policy. All requests will be sent to the licensing service,
 * and no local caching is performed.
 * <p>
 * Using a non-caching policy ensures that there is no local preference data
 * for malicious users to tamper with. As a side effect, applications
 * will not be permitted to run while offline. Developers should carefully
 * weigh the risks of using this Policy over one which implements caching,
 * such as ServerManagedPolicy.
 * <p>
 * Access to the application is only allowed if a LICESNED response is.
 * received. All other responses (including RETRY) will deny access.
 */
public class StrictPolicy implements Policy {

    private int mLastResponse;

    public StrictPolicy() {
        // Set default policy. This will force the application to check the policy on launch.
        mLastResponse = Policy.RETRY;
    }

    /**
     * Process a new response from the license server. Since we aren't
     * performing any caching, this equates to reading the LicenseResponse.
     * Any ResponseData provided is ignored.
     *
     * @param response the result from validating the server response
     * @param rawData the raw server response data
     */
    public void processServerResponse(int response, ResponseData rawData) {
        mLastResponse = response;
    }

    /**
     * {@inheritDoc}
     *
     * This implementation allows access if and only if a LICENSED response
     * was received the last time the server was contacted.
     */
    public boolean allowAccess() {
        return (mLastResponse == Policy.LICENSED);
    }

}
