package com.oneafricamedia.android.notifications.managers;

import android.util.Log;


import com.oneafricamedia.android.notifications.api.GCMMicroServiceRESTClient;
import com.oneafricamedia.android.notifications.model.api.requests.GcmToggleDataRequest;
import com.oneafricamedia.android.notifications.model.api.requests.UserDeviceRegistration;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GCMDeviceApiManager extends BaseApiManager<GCMMicroServiceRESTClient> {

    public String mBaseURL;
    public String mAuthenticationString;

    public GCMDeviceApiManager() {
        super(GCMMicroServiceRESTClient.class);
    }

    public void registerDevice(String token, String userId) throws IOException {
        setApiInterface();

        callAsync(mApiInterface.registerDevice(mAuthenticationString,
                new UserDeviceRegistration(token, String.valueOf(userId))),
                new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful())
                            Log.d("LogTag", "Successfully registered device with GCM microservice");
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.d("LogTag", "Registering device with GCM microservice failed: " + t.getMessage());
                    }
                });
    }

    public void setPushEnabled(GcmToggleDataRequest gcmToggleDataRequest) throws IOException {
        setApiInterface();

        callAsync(mApiInterface.toggleGcmFlag(mAuthenticationString, gcmToggleDataRequest),
                new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful())
                            Log.d("LogTag", "Successfully toggled PuNo flag");
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.d("LogTag", "Toggling PuNo flag failed: " + t.getMessage());
                    }
                });
    }

    @Override
    protected String getUrl() {
        return mBaseURL;
    }
}
