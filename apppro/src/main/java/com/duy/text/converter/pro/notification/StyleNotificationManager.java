/*
 * Copyright (c) 2017 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use context file except in compliance with the License.
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

package com.duy.text.converter.pro.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.duy.text.converter.pro.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Duy on 26-Jul-17.
 */

public class StyleNotificationManager {

    private static final int NOTIFICATION_ENCODE_ID = 0;
    private static final int NOTIFICATION_DECODE_ID = 1;

    public static void showNotificationEncodeIfNeed(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.getBoolean(context.getString(R.string.pref_key_enable_encode), false)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.encode_notification);
            remoteViews.setTextViewText(R.id.title, "En\ncode");

            Intent intent1 = new Intent();
            intent1.setAction(StyleNotification.ACTION_ENCODE_STYLE_1);
            PendingIntent pendingIntentStyle1 = PendingIntent.getBroadcast(context, 12345, intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_one, pendingIntentStyle1);

            Intent intent2 = new Intent();
            intent2.setAction(StyleNotification.ACTION_ENCODE_STYLE_2);
            PendingIntent pendingIntentStyle2 = PendingIntent.getBroadcast(context, 12345, intent2,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_two, pendingIntentStyle2);

            Intent intent3 = new Intent();
            intent3.setAction(StyleNotification.ACTION_ENCODE_STYLE_3);
            PendingIntent pendingIntentStyle3 = PendingIntent.getBroadcast(context, 12345, intent3,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_three, pendingIntentStyle3);

            Intent intent4 = new Intent();
            intent4.setAction(StyleNotification.ACTION_ENCODE_STYLE_4);
            PendingIntent pendingIntentStyle4 = PendingIntent.getBroadcast(context, 12345, intent4,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_four, pendingIntentStyle4);

            Intent intent5 = new Intent();
            intent5.setAction(StyleNotification.ACTION_ENCODE_STYLE_5);
            PendingIntent pendingIntentStyle5 = PendingIntent.getBroadcast(context, 12345, intent5,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_four, pendingIntentStyle4);

            builder.setContent(remoteViews);
            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ENCODE_ID, notification);
        }
    }

    public static void showNotificationDecodeIfNeed(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.getBoolean(context.getString(R.string.pref_key_enable_encode), false)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.encode_notification);
            remoteViews.setTextViewText(R.id.title, "De\ncode");

            Intent intent1 = new Intent();
            intent1.setAction(StyleNotification.ACTION_DECODE_STYLE_1);
            PendingIntent pendingIntentStyle1 = PendingIntent.getBroadcast(context, 12345, intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_one, pendingIntentStyle1);

            Intent intent2 = new Intent();
            intent2.setAction(StyleNotification.ACTION_DECODE_STYLE_2);
            PendingIntent pendingIntentStyle2 = PendingIntent.getBroadcast(context, 12345, intent2,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_two, pendingIntentStyle2);

            Intent intent3 = new Intent();
            intent3.setAction(StyleNotification.ACTION_DECODE_STYLE_3);
            PendingIntent pendingIntentStyle3 = PendingIntent.getBroadcast(context, 12345, intent3,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_three, pendingIntentStyle3);

            Intent intent4 = new Intent();
            intent4.setAction(StyleNotification.ACTION_DECODE_STYLE_4);
            PendingIntent pendingIntentStyle4 = PendingIntent.getBroadcast(context, 12345, intent4,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_four, pendingIntentStyle4);

            Intent intent5 = new Intent();
            intent5.setAction(StyleNotification.ACTION_DECODE_STYLE_5);
            PendingIntent pendingIntentStyle5 = PendingIntent.getBroadcast(context, 12345, intent5,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.img_four, pendingIntentStyle5);

            builder.setContent(remoteViews);

            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_DECODE_ID, notification);
        }
    }
}
