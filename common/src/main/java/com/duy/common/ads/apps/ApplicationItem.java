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

package com.duy.common.ads.apps;

import java.io.Serializable;

class ApplicationItem implements Serializable, Cloneable {
    private String name;
    private String applicationId;
    private String iconUrl;
    private String wallpaperUrl;

    ApplicationItem(String name, String applicationId, String iconUrl, String wallpaperUrl) {
        this.name = name;
        this.applicationId = applicationId;
        this.iconUrl = iconUrl;
        this.wallpaperUrl = wallpaperUrl;
    }

    @Override
    public String toString() {
        return "ApplicationItem{" +
                "name='" + name + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", wallpaperUrl='" + wallpaperUrl + '\'' +
                '}';
    }

    public String getWallpaperUrl() {
        return wallpaperUrl;
    }

    public void setWallpaperUrl(String wallpaperUrl) {
        this.wallpaperUrl = wallpaperUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }


}