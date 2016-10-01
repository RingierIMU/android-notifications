package com.oneafricamedia.android.notifications.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Map;

public class NotificationComponentUtil {
    public static final String DEVICE_REGISTRATION_ENDPOINT = "users/me/device";
    public static final String USER_ME_ENDPOINT = "users/me";

    public static final String KEY_TYPE = "type";
    public static final String KEY_ENTITY_ID = "entity_id";
    public static final String KEY_PAYLOAD = "payload";

    public static final int NOTIFICATION_START_HOUR = 8;
    public static final int NOTIFICATION_END_HOUR = 20;

    public static final String ACTIVITY_MAP = "ACTIVITY_MAP";
    public static final String EVENT_MAP = "EVENT_MAP";
    public static final String TITLE = "NOTIFICATION_TITLE";
    public static final String TEXT = "NOTIFICATION_TEXT";
    public static final String SMALL_ICON = "NOTIFICATION_SMALL_ICON";
    public static final String INTENT = "NOTIFICATION_INTENT";
    public static final String AUTO_CANCEL = "NOTIFICATION_AUTO_CANCEL";
    public static final String LARGE_ICON = "NOTIFICATION_LARGE_ICON";
    public static final String STYLE = "NOTIFICATION_STYLE";
    public static final String CATEGORY = "NOTIFICATION_CATEGORY";

    public static final String NOTIFICATION_ACTIVITY = "activity";
    public static final String NOTIFICATION = "NOTIFICATION";

    private Context mContext;
    private EventBus mEventBus = null;

    public NotificationComponentUtil(Context context) {
        mContext = context;
        try {
            mEventBus = EventBus.getDefault();
        } catch (Exception exception) {
            Log.e("LogTag", "Unable to get default EventBus instance.");
        }
    }

    /**
     * Takes in the Map from Firebase as well as a Map of Objects needed for
     * crafting the notification. You can pass in a fully crafted Intent within that map
     * or a list of activities.
     *
     * @param remoteMessageData A Map with the raw data from the RemoteMessage
     * @param appInfos          A Map of Objects needed by the library for
     *                          the generation of a notification, especially the activities
     * @return True if the message was successfully processed, false in any other case
     */
    public boolean processNotificationMessage(Map<String, String> remoteMessageData, Map<String, Object> appInfos) {
        if (remoteMessageData.containsKey(NotificationComponentUtil.KEY_TYPE)) {
            String type = remoteMessageData.get(NotificationComponentUtil.KEY_TYPE);

            return handleActivities(type, appInfos);
        }

        return false;
    }

    /**
     * Takes in the message type and a Map of events.
     *
     * @param type     A String containing the message type
     * @param appInfos A Map of events with their respective
     */
    public boolean handleEvents(String type, Map<String, Object> appInfos) {
        Map<String, Object> eventMap = (Map<String, Object>) appInfos.get(EVENT_MAP);

        for (String key : eventMap.keySet()) {
            if (type.equalsIgnoreCase(key)) {
                Log.d("LogTag", "Firing event for type: " + type);
                if (mEventBus != null) mEventBus.post(eventMap.get(key));
                return true;
            }
        }
        return false;
    }

    /**
     * Takes in the message type and a Map of activities.
     *
     * @param type     A String containing the message type
     * @param appInfos A Map of events with their respective
     */
    public boolean handleActivities(String type, Map<String, Object> appInfos) {
        Map<String, Object> activityMap = (Map<String, Object>) appInfos.get(ACTIVITY_MAP);

        for (String key : activityMap.keySet()) {
            if (type.equalsIgnoreCase(key)) {
                Log.d("LogTag", "Assigning activity" + activityMap.get(type) + " for type: " + type);
                appInfos.put(NOTIFICATION_ACTIVITY, activityMap.get(type));
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a notification that opens an activity. That activity can either be determined from a
     * Intent being passed in or a new Intent can be crafted with the Class.
     * I.e. one of those can be null but not both of them.
     *
     * @param clz    An Object of type Class that refers to the Activity the notification should open
     * @param intent An Object of type Intent that refers to the Intent of the notification
     * @param icon   An int that refers to a drawable icon
     * @param title  A String that will be the title of the notification
     * @param text   A String that will be the text of the notification
     * @return An instance of a Notification object
     */
    public Notification craftNotification(Class clz, Intent intent, String intentExtra, int icon, String title, String text, Bitmap largeIcon,
                                          boolean autoCancel, NotificationCompat.Style notificationStyle, String category) {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(text);

        if (autoCancel) notificationBuilder.setAutoCancel(true);
        if (largeIcon != null) notificationBuilder.setLargeIcon(largeIcon);
        if (notificationStyle != null) notificationBuilder.setStyle(notificationStyle);
        if (category != null) notificationBuilder.setCategory(category);

        if (shouldAddSoundVibrate()) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(defaultSoundUri);
            long[] v = {500, 1000};
            notificationBuilder.setVibrate(v);
        }

        Intent resultIntent = null;

        if (intent != null) {
            resultIntent = intent;
        } else if (clz != null) {
            resultIntent = new Intent(mContext, clz);
            resultIntent.putExtra(KEY_PAYLOAD, intentExtra);
        } else {
            Log.e("LogTag", "Class can't be null!");
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        notificationBuilder.setContentIntent(resultPendingIntent);

        return notificationBuilder.build();
    }

    /**
     * Shows a notification.
     *
     * @param notification An Object of type Notification to be shown to the user
     */
    public boolean showNotification(Notification notification) {
        try {
            NotificationManager mNotificationManager =
                    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(1, notification);
            return true;
        } catch (Exception exception) {
            Log.e("LogTag", "Exceoption showing the Notification: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Check if the notification should cause a vibration and a sound. At the moment this is based
     * fixed times. If it is after 8:00 and before 20:00, it should notify with a sound and vibrate.
     *
     * @return true if the vibrate and sound should be added, else false
     */
    private boolean shouldAddSoundVibrate() {
        Calendar nowCalendar = Calendar.getInstance();
        int hour = nowCalendar.get(Calendar.HOUR_OF_DAY);
        return hour >= NotificationComponentUtil.NOTIFICATION_START_HOUR && hour < NotificationComponentUtil.NOTIFICATION_END_HOUR;
    }
}
