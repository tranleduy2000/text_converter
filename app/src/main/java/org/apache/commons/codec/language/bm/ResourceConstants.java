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

package org.apache.commons.codec.language.bm;

import org.apache.commons.codec.CharEncoding;

/**
 * Constants used to process resource files.
 *
 * <p>This class is immutable and thread-safe.</p>
 *
 * @since 1.6
 * @version $Id: ResourceConstants.java 1376690 2012-08-23 20:51:45Z tn $
 */
class ResourceConstants {

    static final String CMT = "//";
    static final String ENCODING = CharEncoding.UTF_8;
    static final String EXT_CMT_END = "*/";
    static final String EXT_CMT_START = "/*";

}
