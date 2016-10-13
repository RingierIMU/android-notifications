package com.oneafricamedia.android.notificationsexampleapp;

import android.app.Application;
import android.util.Log;

import com.oneafricamedia.android.notifications.FcmMessaging;
import com.oneafricamedia.android.notifications.events.UserIdMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ExampleApplication extends Application {

    private static final String SENDER_ID="000000000000";

    private Long userId;
    public FcmMessaging mFcmMessenger;

    public ExampleApplication() {
        this.mFcmMessenger = FcmMessaging.prepare(ExampleApplication.SENDER_ID);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserIdMessage userIdMessage) {
        setUserId(Long.parseLong(userIdMessage.message));
        Log.d("LogTag", "My user ID is now: " + userIdMessage.message);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public FcmMessaging getFcmMessenger() {
        return mFcmMessenger;
    }
}
