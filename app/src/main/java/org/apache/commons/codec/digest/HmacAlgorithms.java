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

package org.apache.commons.codec.digest;

/**
 * Standard {@link HmacUtils} algorithm names from the <cite>Java Cryptography Architecture Standard Algorithm Name
 * Documentation</cite>.
 *
 * <p>
 * <strong>Note: Not all JCE implementations support all the algorithms in this enum.</strong>
 * </p>
 *
 * @see <a href="http://docs.oracle.com/javase/6/docs/technotes/guides/security/SunProviders.html#SunJCEProvider"> Java
 *      6 Cryptography Architecture Sun Providers Documentation</a>
 * @see <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html#SunJCEProvider"> Java
 *      7 Cryptography Architecture Sun Providers Documentation</a>
 * @see <a href="http://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#SunJCEProvider"> Java
 *      8 Cryptography Architecture Sun Providers Documentation</a>
 * @see <a href=
 *      "http://docs.oracle.com/javase/9/security/oracleproviders.htm#JSSEC-GUID-A47B1249-593C-4C38-A0D0-68FA7681E0A7">
 *      Java 9 Cryptography Architecture Sun Providers Documentation</a>
 * @since 1.10
 * @version $Id: HmacAlgorithms.java 1811624 2017-10-09 23:07:49Z ggregory $
 */
public enum HmacAlgorithms {

    /**
     * The HmacMD5 Message Authentication Code (MAC) algorithm specified in RFC 2104 and RFC 1321.
     * <p>
     * Every implementation of the Java platform is required to support this standard MAC algorithm.
     * </p>
     */
    HMAC_MD5("HmacMD5"),

    /**
     * The HmacSHA1 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * Every implementation of the Java platform is required to support this standard MAC algorithm.
     * </p>
     */
    HMAC_SHA_1("HmacSHA1"),

    /**
     * The HmacSHA224 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * Every implementation of the Java 8+ platform is required to support this standard MAC algorithm.
     * </p>
     * @since 1.11
     */
    HMAC_SHA_224("HmacSHA224"),

    /**
     * The HmacSHA256 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * Every implementation of the Java platform is required to support this standard MAC algorithm.
     * </p>
     */
    HMAC_SHA_256("HmacSHA256"),

    /**
     * The HmacSHA384 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * This MAC algorithm is <em>optional</em>; not all implementations support it.
     * </p>
     */
    HMAC_SHA_384("HmacSHA384"),

    /**
     * The HmacSHA512 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * This MAC algorithm is <em>optional</em>; not all implementations support it.
     * </p>
     */
    HMAC_SHA_512("HmacSHA512");

    private final String name;

    private HmacAlgorithms(final String algorithm) {
        this.name = algorithm;
    }

    /**
     * Gets the algorithm name.
     *
     * @return the algorithm name.
     * @since 1.11
     */
    public String getName() {
        return name;
    }

    /**
     * The algorithm name
     *
     * @see <a href="http://docs.oracle.com/javase/6/docs/technotes/guides/security/SunProviders.html#SunJCEProvider">
     *      Java 6 Cryptography Architecture Sun Providers Documentation</a>
     * @see <a href="http://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html#SunJCEProvider">
     *      Java 7 Cryptography Architecture Sun Providers Documentation</a>
     * @see <a href="http://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#SunJCEProvider">
     *      Java 8 Cryptography Architecture Sun Providers Documentation</a>
     * @see <a href=
     *      "http://docs.oracle.com/javase/9/security/oracleproviders.htm#JSSEC-GUID-A47B1249-593C-4C38-A0D0-68FA7681E0A7">
     *      Java 9 Cryptography Architecture Sun Providers Documentation</a>
     * @return The algorithm name ("HmacSHA512" for example)
     */
    @Override
    public String toString() {
        return name;
    }

}
