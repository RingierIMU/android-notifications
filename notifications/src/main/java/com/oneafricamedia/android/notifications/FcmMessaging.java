package com.oneafricamedia.android.notifications;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.UUID;

public class FcmMessaging {

    private static final String SERVER_DOMAIN = "@gcm.googleapis.com";
    private static FcmMessaging mInstance;
    private FirebaseMessaging mFirebaseMessaging;
    private String mSenderId;

    private FcmMessaging(String senderId) {
        this.mSenderId = senderId;
        this.mFirebaseMessaging = FirebaseMessaging.getInstance();
    }

    public static FcmMessaging prepare(String senderId) {
        synchronized (FcmMessaging.class) {
            if (mInstance == null) {
                mInstance = new FcmMessaging(senderId);
            }
        }
        return mInstance;
    }

    private RemoteMessage buildRemoteMessage(Map<String, String> data) {
        RemoteMessage.Builder builder = new RemoteMessage.Builder(mSenderId + SERVER_DOMAIN);
        builder.setMessageId(UUID.randomUUID().toString());
        for (Map.Entry<String, String> entry : data.entrySet()) {
            builder.addData(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    public void subscribeToTopic(String topic) {
        mFirebaseMessaging.subscribeToTopic(topic);
    }

    public void unsubscribeFromTopic(String topic) {
        mFirebaseMessaging.subscribeToTopic(topic);
    }

    public void send(RemoteMessage remoteMessage) {
        mFirebaseMessaging.send(remoteMessage);
    }

    public void sendRawData(Map<String, String> data) {
        mFirebaseMessaging.send(buildRemoteMessage(data));
    }


}
