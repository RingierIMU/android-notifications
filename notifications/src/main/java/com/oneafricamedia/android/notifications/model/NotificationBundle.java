package com.oneafricamedia.android.notifications.model;

import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Map;

/**
 * Used to pass information for the GCM microservice into the component.
 *
 * TODO Implement this...
 */
public class NotificationBundle {
    Intent intent;
    Notification notification;

    Map<String, Class> activityMap;
    Map<String, Object> eventMap;

    String notification_title;
    String notification_text;
    String category;
    boolean autoCancel;
    NotificationCompat.Style notificationStyle;
}