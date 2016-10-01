package com.oneafricamedia.android.notifications.model.api.requests;

public class ApiTogglePunoRequest {
    public String user_id;
    public boolean receive_push_notifications;

    public ApiTogglePunoRequest(String user_id, boolean receive_push_notifications) {
        this.user_id = user_id;
        this.receive_push_notifications = receive_push_notifications;
    }
}
