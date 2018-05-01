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
 * Allows the developer to limit the number of devices using a single license.
 * <p>
 * The LICENSED response from the server contains a user identifier unique to
 * the &lt;application, user&gt; pair. The developer can send this identifier
 * to their own server along with some device identifier (a random number
 * generated and stored once per application installation,
 * {@link android.telephony.TelephonyManager#getDeviceId getDeviceId},
 * {@link android.provider.Settings.Secure#ANDROID_ID ANDROID_ID}, etc).
 * The more sources used to identify the device, the harder it will be for an
 * attacker to spoof.
 * <p>
 * The server can look at the &lt;application, user, device id&gt; tuple and
 * restrict a user's application license to run on at most 10 different devices
 * in a week (for example). We recommend not being too restrictive because a
 * user might legitimately have multiple devices or be in the process of
 * changing phones. This will catch egregious violations of multiple people
 * sharing one license.
 */
public interface DeviceLimiter {

    /**
     * Checks if this device is allowed to use the given user's license.
     *
     * @param userId the user whose license the server responded with
     * @return LICENSED if the device is allowed, NOT_LICENSED if not, RETRY if an error occurs
     */
    int isDeviceAllowed(String userId);
}
