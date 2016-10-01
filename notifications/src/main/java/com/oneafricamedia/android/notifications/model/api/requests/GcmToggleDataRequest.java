package com.oneafricamedia.android.notifications.model.api.requests;

public class GcmToggleDataRequest {
    public boolean receive_push_notifications;

    public GcmToggleDataRequest(boolean receive_push_notifications) {
        this.receive_push_notifications = receive_push_notifications;
    }
}
