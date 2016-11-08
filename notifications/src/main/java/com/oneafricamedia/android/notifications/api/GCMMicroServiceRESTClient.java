package com.oneafricamedia.android.notifications.api;

import com.oneafricamedia.android.notifications.model.api.UserAPIResult;
import com.oneafricamedia.android.notifications.model.api.requests.ApiTogglePunoRequest;
import com.oneafricamedia.android.notifications.model.api.requests.UserDeviceRegistration;
import com.oneafricamedia.android.notifications.util.NotificationComponentUtil;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface GCMMicroServiceRESTClient {

    @PUT
    Call<UserDeviceRegistration> registerDevice(@Url String url, @Header("Authorization") String basicAuth, @Body UserDeviceRegistration registerDevice);

    @PUT
    Call<UserAPIResult> togglePunoFlagApi(@Url String url, @Header("Authorization") String basicAuth, @Body ApiTogglePunoRequest apiTogglePunoRequest);

}
