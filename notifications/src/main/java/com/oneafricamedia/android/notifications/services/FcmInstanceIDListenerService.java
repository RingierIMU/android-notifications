package com.oneafricamedia.android.notifications.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.oneafricamedia.android.notifications.model.BackendBundle;
import com.oneafricamedia.android.notifications.util.ApiUtil;

public class FcmInstanceIDListenerService extends FirebaseInstanceIdService {
    ApiUtil apiUtil;

    public FcmInstanceIDListenerService() {
    }

    /**
     * Default constructor: Don't call this in production!
     * <p>
     * Either implement your own or use one of the other implementations in this class.
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("LogTag", "Refreshed token: " + refreshedToken);
    }

    /**
     * Takes in a BackendBundle with Strings of config settings, then registers the device
     * with the GCM microservice and enables the PuNo flag in the backend. If the BackendBundle does
     * not include infos on the GCM microservice it will not do anything. Base API calls are
     * regarded optional.
     *
     * @param backendBundle A BackendBundle with infos needed for API calls
     */
    public void onTokenRefresh(BackendBundle backendBundle) {
        if (backendBundle != null) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            apiUtil = new ApiUtil(backendBundle);

            if (backendBundle.containsValidGcmInfo()) {
                apiUtil.registerDevice(refreshedToken, backendBundle.getUserId());

                if (backendBundle.containsValidApiInfo()) {
                    if (!backendBundle.isUsingMicroserviceAsBackend()) {
                        apiUtil.togglePuNoFlag("", true);
                    }
                } else {
                    Log.d("LogTag", "Registered with GCM microservice but no info on base API found.");
                }

            } else {
                Log.d("LogTag", "No info about GCM microservice, so not doing anything there.");
            }
        } else {
            onTokenRefresh();
        }
    }


}
