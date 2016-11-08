package com.oneafricamedia.android.notifications.util;

import android.util.Log;

import com.oneafricamedia.android.notifications.events.UserIdMessage;
import com.oneafricamedia.android.notifications.managers.GCMDeviceApiManager;
import com.oneafricamedia.android.notifications.model.BackendBundle;
import com.oneafricamedia.android.notifications.model.api.requests.ApiTogglePunoRequest;
import com.oneafricamedia.android.notifications.model.api.requests.UserDeviceRegistration;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiUtil implements Callback<UserDeviceRegistration> {
    private GCMDeviceApiManager mGcmDeviceApi;
    private BackendBundle mBackendBundle;

    public ApiUtil(BackendBundle backendBundle) {
        this.mGcmDeviceApi = new GCMDeviceApiManager();
        this.mBackendBundle = backendBundle;
    }

    /**
     * Register device with GCM Microservice
     *
     * @param token  The new token
     * @param userId The user's ID
     */
    public void registerDevice(String token, String userId) {
        mGcmDeviceApi.mBaseURL = mBackendBundle.getGcmBaseUrl();
        mGcmDeviceApi.mAuthenticationString = mBackendBundle.getGcmAuthenticationString();
        try {
            mGcmDeviceApi.registerDevice(mBackendBundle.getGcmUrlPath(), token, userId, this);
        } catch (IOException e) {
            Log.e("LogTag", "Could not register device with GCM microservice: " + e.getMessage());
        }
    }

    /**
     * Set push notifications enabled for user
     **/
    public void togglePuNoFlag(String user_id, boolean flag) {
        mGcmDeviceApi.mBaseURL = mBackendBundle.getApiBaseUrl();
        mGcmDeviceApi.mAuthenticationString = mBackendBundle.getApiBaseAuthenticationString();
        try {
            mGcmDeviceApi.setPushEnabled(mBackendBundle.getApiUrlPath(), new ApiTogglePunoRequest(user_id, flag), new Callback() {
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
        } catch (IOException e) {
            Log.e("LogTag", "Could not toggle PuNo flag: " + e.getMessage());
        }
    }

    @Override
    public void onResponse(Call<UserDeviceRegistration> call, Response<UserDeviceRegistration> response) {
        if (response.isSuccessful()) {
            Log.d("LogTag", "Successfully registered device with GCM microservice");
            if (response.body().getUser_id() != null)
                togglePuNoFlag(response.body().getUser_id(), true);
            EventBus.getDefault().post(new UserIdMessage(response.body().getUser_id()));
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.d("LogTag", "Registering device with GCM microservice failed: " + t.getMessage());
    }

}
