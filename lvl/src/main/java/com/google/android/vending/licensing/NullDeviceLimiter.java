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
 * A DeviceLimiter that doesn't limit the number of devices that can use a
 * given user's license.
 * <p>
 * Unless you have reason to believe that your application is being pirated
 * by multiple users using the same license (signing in to Market as the same
 * user), we recommend you use this implementation.
 */
public class NullDeviceLimiter implements DeviceLimiter {

    public int isDeviceAllowed(String userId) {
        return Policy.LICENSED;
    }
}
