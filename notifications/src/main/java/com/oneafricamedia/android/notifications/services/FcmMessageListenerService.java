package com.oneafricamedia.android.notifications.services;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oneafricamedia.android.notifications.events.MarketingMessageReceived;
import com.oneafricamedia.android.notifications.interfaces.FcmNotification;
import com.oneafricamedia.android.notifications.model.NotificationBundle;
import com.oneafricamedia.android.notifications.util.NotificationComponentUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class FcmMessageListenerService extends FirebaseMessagingService {

    private NotificationComponentUtil mNotificationComponentUtil;
    private boolean mNotificationSuccess = false;
    private EventBus mEventBus = null;

    public FcmMessageListenerService() {
        mNotificationComponentUtil = new NotificationComponentUtil(this);
        try {
            mEventBus = EventBus.getDefault();
        } catch (Exception exception) {
            Log.e("LogTag", "Unable to get default EventBus instance.");
        }
    }

    /**
     * Default onMessageReceived - Called when a message is received from Firebase (via the main application)
     * <p>
     * Logs the message and fires a MarketingMessageReceived event to EventBus default channel
     *
     * @param message The RemoteMessage object from Firebase
     */
    @Override
    public void onMessageReceived(RemoteMessage message) {
        MarketingMessageReceived marketingMessageReceived = new MarketingMessageReceived("");
        marketingMessageReceived.message = message.getFrom();
        if (mEventBus != null) mEventBus.post(marketingMessageReceived);
        mNotificationSuccess = true;
    }

    /**
     * Takes in the RemoteMessage from Firebase as well as a Map of Objects needed for
     * crafting the notification. You can pass in a fully crafted Intent within that map
     * (which is the preferred way) or a list of activities.
     *
     * @param message  The RemoteMessage object from Firebase
     * @param appInfos A Map of Objects needed by the library for
     *                 the generation of a notification
     */
    public void onMessageReceived(RemoteMessage message, Map<String, Object> appInfos) {
        Map data = message.getData();

        if (!appInfos.containsKey(NotificationComponentUtil.NOTIFICATION) &&
                !appInfos.containsKey(NotificationComponentUtil.INTENT) &&
                !appInfos.containsKey(NotificationComponentUtil.ACTIVITY_MAP)) {

            if (appInfos.containsKey(NotificationComponentUtil.EVENT_MAP) &&
                    appInfos.get(NotificationComponentUtil.EVENT_MAP) instanceof Map) {
                mNotificationSuccess = mNotificationComponentUtil.handleEvents(
                        (String) data.get(NotificationComponentUtil.KEY_TYPE),
                        (Map) appInfos.get(NotificationComponentUtil.EVENT_MAP));
            } else {
                Log.e("LogTag", "Neither notification nor event found in appInfos, doing nothing.");
            }
        } else {
            Notification notification;

            if (appInfos.containsKey(NotificationComponentUtil.NOTIFICATION) &&
                    appInfos.get(NotificationComponentUtil.NOTIFICATION) instanceof Notification) {
                notification = (Notification) appInfos.get(NotificationComponentUtil.NOTIFICATION);
                mNotificationSuccess = mNotificationComponentUtil.showNotification(notification);
            } else {
                if (mNotificationComponentUtil.processNotificationMessage(data, appInfos)) {
                    Intent intent;
                    boolean autoCancel;
                    Bitmap largeIcon;
                    NotificationCompat.Style notificationStyle;
                    String category;

                    if (!appInfos.containsKey(NotificationComponentUtil.SMALL_ICON) ||
                            !appInfos.containsKey(NotificationComponentUtil.TITLE) ||
                            !appInfos.containsKey(NotificationComponentUtil.TEXT))
                        Log.e("LogTag", "A notification message needs to have a small_icon, title and text.");

                    if (!appInfos.containsKey(NotificationComponentUtil.INTENT) &&
                            !appInfos.containsKey(NotificationComponentUtil.ACTIVITY_MAP))
                        Log.e("LogTag", "A notification message needs to have either an intent or an activity.");

                    try {
                        autoCancel = (appInfos.containsKey(NotificationComponentUtil.AUTO_CANCEL)) ?
                                (boolean) appInfos.get(NotificationComponentUtil.AUTO_CANCEL) : false;

                        largeIcon = (appInfos.containsKey(NotificationComponentUtil.LARGE_ICON)) ?
                                (Bitmap) appInfos.get(NotificationComponentUtil.LARGE_ICON) : null;

                        notificationStyle = (appInfos.containsKey(NotificationComponentUtil.STYLE)) ?
                                (NotificationCompat.Style) appInfos.get(NotificationComponentUtil.STYLE) : null;

                        category = (appInfos.containsKey(NotificationComponentUtil.CATEGORY)) ?
                                (String) appInfos.get(NotificationComponentUtil.CATEGORY) : null;

                        if (appInfos.containsKey(NotificationComponentUtil.INTENT)) {
                            intent = (Intent) appInfos.get(NotificationComponentUtil.INTENT);
                            appInfos.put(NotificationComponentUtil.NOTIFICATION_ACTIVITY, null);
                        } else {
                            intent = null;
                        }

                        notification = mNotificationComponentUtil.craftNotification(
                                (Class) appInfos.get(NotificationComponentUtil.NOTIFICATION_ACTIVITY),
                                intent,
                                (String) data.get(NotificationComponentUtil.KEY_PAYLOAD),
                                (int) appInfos.get(NotificationComponentUtil.SMALL_ICON),
                                (String) appInfos.get(NotificationComponentUtil.TITLE),
                                (String) appInfos.get(NotificationComponentUtil.TEXT),
                                largeIcon,
                                autoCancel,
                                notificationStyle,
                                category);

                        mNotificationComponentUtil.showNotification(notification);

                        mNotificationSuccess = true;
                    } catch (ClassCastException classCastException) {
                        Log.e("LogTag", "Something was wrong with the map that came from the main application: " + classCastException.getMessage());
                    }
                } else {
                    Log.d("LogTag", "Unable to process notification, probably a system message, trying to fire matching events.");
                }
            }
            if (appInfos.containsKey(NotificationComponentUtil.EVENT_MAP) &&
                    appInfos.get(NotificationComponentUtil.EVENT_MAP) instanceof Map) {
                if (mNotificationComponentUtil.handleEvents((String) data.get(NotificationComponentUtil.KEY_TYPE),
                        appInfos)) {
                    mNotificationSuccess = true;
                } else {
                    Log.d("LogTag", "Could not find a suiting event to fire.");
                }
            } else {
                Log.d("LogTag", "No events found in appInfo so not firing any.");
            }
        }
    }

    /**
     * Takes in the RemoteMessage from Firebase as well as a Map of Objects needed for
     * crafting the notification. You can pass in a fully crafted Intent within that map
     * (which is the preferred way) or a list of activities. An
     * FcmNotification.OnCompletionListener that is called upon completion of processing
     * the message is passed in as well.
     *
     * @param remoteMessage        The RemoteMessage object from Firebase
     * @param appInfos             A Map of Objects needed by the library for
     *                             the generation of a notification
     * @param onCompletionListener An FcmNotification.OnCompletionListener that is called upon completion of processing the message
     */
    public void onMessageReceived(RemoteMessage remoteMessage, Map<String, Object> appInfos, FcmNotification.OnCompletionListener onCompletionListener) {
        onMessageReceived(remoteMessage, appInfos);

        handleListener(onCompletionListener);
    }

    private void handleListener(FcmNotification.OnCompletionListener onCompletionListener) {
        if (onCompletionListener != null) {
            if (mNotificationSuccess) {
                onCompletionListener.onSuccess();
            } else {
                onCompletionListener.onFailure();
            }
        } else {
            Log.e("LogTag", "Your OnCompleteListener was null so we ain't doin' nothing!");
        }

    }

    public void onMessageReceived(NotificationBundle notificationBundle) {
        // TODO Implement this...
    }
}
