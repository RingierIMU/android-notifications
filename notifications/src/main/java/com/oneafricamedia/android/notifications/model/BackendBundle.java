package com.oneafricamedia.android.notifications.model;

import android.text.TextUtils;
import android.webkit.URLUtil;

/**
 * Used to pass information into the component. If any of the GCM infos is left empty, nothing will
 * happen within the component and base API is considered optional, i.e. if left empty no calls
 * will be made but registration with the GCM microservice will still happen.
 */
public class BackendBundle {
    private String gcmBaseUrl;
    private String gcmAuthenticationString;
    private String apiBaseUrl;
    private String apiBaseAuthenticationString;
    private String userId;

    public BackendBundle(String gcmBaseUrl, String gcmAuthenticationString, String apiBaseUrl, String apiBaseAuthenticationString, String userId) {
        this.gcmBaseUrl = gcmBaseUrl;
        this.gcmAuthenticationString = gcmAuthenticationString;
        this.apiBaseUrl = apiBaseUrl;
        this.apiBaseAuthenticationString = apiBaseAuthenticationString;
        this.userId = userId;
    }

    public String getGcmBaseUrl() {
        return gcmBaseUrl;
    }

    public void setGcmBaseUrl(String gcmBaseUrl) {
        this.gcmBaseUrl = gcmBaseUrl;
    }

    public String getGcmAuthenticationString() {
        return gcmAuthenticationString;
    }

    public void setGcmAuthenticationString(String gcmAuthenticationString) {
        this.gcmAuthenticationString = gcmAuthenticationString;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getApiBaseAuthenticationString() {
        return apiBaseAuthenticationString;
    }

    public void setApiBaseAuthenticationString(String apiBaseAuthenticationString) {
        this.apiBaseAuthenticationString = apiBaseAuthenticationString;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean containsValidApiInfo() {
        if (!URLUtil.isValidUrl(apiBaseUrl) || TextUtils.isEmpty(apiBaseAuthenticationString))
            return false;
        return true;
    }

    public boolean containsValidGcmInfo() {
        if (!URLUtil.isValidUrl(gcmBaseUrl) || TextUtils.isEmpty(gcmAuthenticationString))
            return false;
        return true;
    }
}
