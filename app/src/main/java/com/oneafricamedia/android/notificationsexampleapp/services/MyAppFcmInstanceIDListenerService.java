package com.oneafricamedia.android.notificationsexampleapp.services;

import com.oneafricamedia.android.notifications.model.BackendBundle;
import com.oneafricamedia.android.notifications.services.FcmInstanceIDListenerService;
import com.oneafricamedia.android.notificationsexampleapp.ExampleApplication;

public class MyAppFcmInstanceIDListenerService extends FcmInstanceIDListenerService {
    @Override
    public void onTokenRefresh() {

        String userId = (((ExampleApplication) getApplication()).getUserId() != null) ?
                ((ExampleApplication) getApplication()).getUserId().toString() : "0";

        super.onTokenRefresh(new BackendBundle("http://server:9000/",
                "Basic Base64-123456789==",
                "http://server:9000/",
                "Basic Base64-123456789==",
                userId,
                true)
        );
    }
}
