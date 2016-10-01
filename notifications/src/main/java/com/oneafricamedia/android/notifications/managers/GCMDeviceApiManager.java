package com.oneafricamedia.android.notifications.managers;

import com.oneafricamedia.android.notifications.api.GCMMicroServiceRESTClient;
import com.oneafricamedia.android.notifications.model.api.requests.ApiTogglePunoRequest;
import com.oneafricamedia.android.notifications.model.api.requests.UserDeviceRegistration;

import java.io.IOException;

import retrofit2.Callback;

public class GCMDeviceApiManager extends BaseApiManager<GCMMicroServiceRESTClient> {

    public String mBaseURL;
    public String mAuthenticationString;

    public GCMDeviceApiManager() {
        super(GCMMicroServiceRESTClient.class);
    }

    public void registerDevice(String token, String userId, Callback callback) throws IOException {
        setApiInterface();

        callAsync(mApiInterface.registerDevice(mAuthenticationString,
                new UserDeviceRegistration(token, String.valueOf(userId))),
                callback);
    }

    public void setPushEnabled(ApiTogglePunoRequest apiTogglePunoRequest, Callback callback) throws IOException {
        setApiInterface();

        callAsync(mApiInterface.togglePunoFlagApi(mAuthenticationString, apiTogglePunoRequest),
                callback);
    }

    @Override
    protected String getUrl() {
        return mBaseURL;
    }
}
