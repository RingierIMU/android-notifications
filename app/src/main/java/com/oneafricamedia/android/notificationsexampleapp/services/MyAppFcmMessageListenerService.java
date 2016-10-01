package com.oneafricamedia.android.notificationsexampleapp.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.oneafricamedia.android.notifications.interfaces.FcmNotification;
import com.oneafricamedia.android.notifications.services.FcmMessageListenerService;
import com.oneafricamedia.android.notifications.util.NotificationComponentUtil;
import com.oneafricamedia.android.notificationsexampleapp.R;
import com.oneafricamedia.android.notificationsexampleapp.events.AlertEnquiry;
import com.oneafricamedia.android.notificationsexampleapp.events.AlertListing;
import com.oneafricamedia.android.notificationsexampleapp.events.ShitHitTheFan;
import com.oneafricamedia.android.notificationsexampleapp.ui.AlertActivity;
import com.oneafricamedia.android.notificationsexampleapp.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class MyAppFcmMessageListenerService extends FcmMessageListenerService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, Object> appInfoMap = new HashMap();
        Map<String, Class> activityMap = new HashMap<>();
        Map<String, Object> eventMap = new HashMap<>();
        Intent intent = new Intent();

        // This is where we prepare our Notification
        //withActivityMap(appInfoMap, activityMap);
        //withIntent(intent, appInfoMap, activityMap);
        //withNotification(appInfoMap);

        // This is where we show it and maybe handle the callback
        //super.onMessageReceived(remoteMessage, appInfoMap);
        //super.onMessageReceived(remoteMessage, appInfoMap, new FcmNotification.OnCompletionListener() {
        //    @Override
        //    public void onSuccess() {
        //        Log.d("LogTag", "Received Callback from the Notification Component: Success");
        //    }
        //    @Override
        //    public void onFailure() {
        //        Log.e("LogTag", "Received Callback from the Notification Component: Failure");
        //    }
        //});

        // This is how we show it and hand in a list of events as well
        withActivityAndEventMap(appInfoMap, activityMap, eventMap);
        //super.onMessageReceived(remoteMessage, appInfoMap);
        super.onMessageReceived(remoteMessage, appInfoMap, new FcmNotification.OnCompletionListener() {
            @Override
            public void onSuccess() {
                Log.d("LogTag", "Received Callback from the Notification Component: Success");
            }

            @Override
            public void onFailure() {
                Log.e("LogTag", "Received Callback from the Notification Component: Failure");
            }
        });


        // This is how it works with a NotificationBundle
        //super.onMessageReceived(withNotificationBundle());
    }

    // You can either pass in the activities that you have...
    private void withActivityAndEventMap(Map map, Map activityMap, Map eventMap) {
        activityMap.put("ALERT_LISTING", AlertActivity.class);
        activityMap.put("ALERT_ENQUIRY", AlertActivity.class);
        activityMap.put("ALERT_DEPRECATION", MainActivity.class);
        map.put(NotificationComponentUtil.ACTIVITY_MAP, activityMap);

        eventMap.put("SHIT_HIT_FAN", new ShitHitTheFan("Shit hit the fan ;-)"));
        eventMap.put("ALERT_ENQUIRY", new AlertEnquiry("There is a new enquiry for you..."));
        eventMap.put("ALERT_LISTING", new AlertListing("There is a new alert for a listing for you..."));
        map.put(NotificationComponentUtil.EVENT_MAP, eventMap);

        map.put(NotificationComponentUtil.SMALL_ICON, R.drawable.common_ic_googleplayservices);
        map.put(NotificationComponentUtil.TITLE, "Notification with map of activities");
        map.put(NotificationComponentUtil.TEXT, "We pass in a list of activities that the component can map accordingly.");
    }


    // You can either pass in the activities that you have...
    private void withActivityMap(Map map, Map activityMap) {
        activityMap.put("ALERT_LISTING", AlertActivity.class);
        activityMap.put("ALERT_ENQUIRY", AlertActivity.class);
        activityMap.put("ALERT_DEPRECATION", MainActivity.class);
        map.put(NotificationComponentUtil.ACTIVITY_MAP, activityMap);

        map.put(NotificationComponentUtil.SMALL_ICON, R.drawable.common_ic_googleplayservices);
        map.put(NotificationComponentUtil.TITLE, "Notification with map of activities");
        map.put(NotificationComponentUtil.TEXT, "We pass in a list of activities that the component can map accordingly.");
    }

    // ...or craft your own complete intent & pass it in...
    private void withIntent(Intent intent, Map map, Map activityMap) {
        activityMap.put("ALERT_LISTING", AlertActivity.class);
        activityMap.put("ALERT_ENQUIRY", AlertActivity.class);
        activityMap.put("ALERT_DEPRECATION", MainActivity.class);
        map.put(NotificationComponentUtil.ACTIVITY_MAP, activityMap);

        intent = new Intent(this, AlertActivity.class);
        intent.putExtra(AlertActivity.ALERT_ID, 1337);
        map.put(NotificationComponentUtil.INTENT, intent);

        map.put(NotificationComponentUtil.SMALL_ICON, R.drawable.common_google_signin_btn_icon_dark_normal);
        map.put(NotificationComponentUtil.TITLE, "Notification with an intent");
        map.put(NotificationComponentUtil.TEXT, "We pass in an Intent which the component uses for the notification.");
    }

    // ...or even pass in your own Notification Object.
    private void withNotification(Map map) {
        map.put(NotificationComponentUtil.NOTIFICATION,
                new NotificationComponentUtil(this).craftNotification(AlertActivity.class,
                        null,
                        null,
                        R.drawable.common_full_open_on_phone,
                        "Notification on it's own",
                        "We pass in a complete Notification object which the app developer can craft" +
                                "100% according to their needs / design etc.",
                        null,
                        true,
                        null,
                        null));
    }

    // You can also pass in a list of events that you want to have fired into
    // your app when the according message type comes in via Firebase
    private void createEventMap(Map map) {
        map.put("SHIT_HIT_FAN", new ShitHitTheFan("Wow, shit hit the fan!"));
    }
}