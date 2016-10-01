package com.oneafricamedia.android.notifications.model.api.requests;

import android.os.Build;

public class UserDeviceRegistration {

    String operating_system;
    String operating_system_version;
    String manufacturer;
    String model;
    String instance_id;
    String user_id;

    public UserDeviceRegistration(String instance_id, String user_id) {
        operating_system = "Android";
        operating_system_version = Build.VERSION.RELEASE;
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        this.instance_id = instance_id;
        this.user_id = user_id;
    }

    public String getOperating_system() {
        return operating_system;
    }

    public String getOperating_system_version() {
        return operating_system_version;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
