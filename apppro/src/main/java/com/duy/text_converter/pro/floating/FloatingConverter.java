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

package com.duy.text_converter.pro.floating;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duy.text_converter.pro.R;
import com.xlythe.view.floating.FloatingView;

/**
 * Created by Duy on 9/4/2017.
 */

public class FloatingConverter extends FloatingView {
    @NonNull
    @Override
    protected View inflateButton(@NonNull ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.floating_converter_icon, parent, false);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull ViewGroup parent) {
        View child = LayoutInflater.from(getContext()).inflate(R.layout.fragment_codec, parent, false);
        return child;
    }

    @NonNull
    @Override
    protected Notification createNotification() {
        Intent intent = new Intent(this, FloatingConverter.class).setAction(ACTION_OPEN);
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.floating_notification_description))
                .setContentIntent(PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .build();
    }
}
