package com.oneafricamedia.android.notifications.interfaces;


public interface FcmNotification {
    public interface OnCompletionListener {
        public void onSuccess();

        public void onFailure();
    }
}
