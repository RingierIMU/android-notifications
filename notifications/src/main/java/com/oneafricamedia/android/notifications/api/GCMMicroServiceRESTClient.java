package com.oneafricamedia.android.notifications.api;

import com.oneafricamedia.android.notifications.model.api.UserAPIResult;
import com.oneafricamedia.android.notifications.model.api.requests.GcmToggleDataRequest;
import com.oneafricamedia.android.notifications.model.api.requests.UserDeviceRegistration;
import com.oneafricamedia.android.notifications.util.NotificationComponentUtil;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface GCMMicroServiceRESTClient {

    @PUT(NotificationComponentUtil.DEVICE_REGISTRATION_ENDPOINT)
    Call<UserDeviceRegistration> registerDevice(@Header("Authorization") String basicAuth, @Body UserDeviceRegistration registerDevice);

    @PUT(NotificationComponentUtil.USER_ME_ENDPOINT)
    Call<UserAPIResult> toggleGcmFlag(@Header("Authorization") String basicAuth, @Body GcmToggleDataRequest gcmToggleDataRequest);

}
