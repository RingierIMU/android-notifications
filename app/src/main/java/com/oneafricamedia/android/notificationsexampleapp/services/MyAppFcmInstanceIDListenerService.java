package com.oneafricamedia.android.notificationsexampleapp.services;

import com.oneafricamedia.android.notifications.model.BackendBundle;
import com.oneafricamedia.android.notifications.services.FcmInstanceIDListenerService;

public class MyAppFcmInstanceIDListenerService extends FcmInstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh(new BackendBundle("http://www.site.com/GCM/", "auth", "http://www.site.com/API/", "auth", "0"));
    }
}
