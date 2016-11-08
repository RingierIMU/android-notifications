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
    private String gcmUrlPath;
    private String apiBaseUrl;
    private String apiBaseAuthenticationString;
    private String apiUrlPath;
    private String userId;
    private boolean usingMicroserviceAsBackend;

    public BackendBundle(String gcmBaseUrl, String gcmUrlPath, String gcmAuthenticationString, String apiBaseUrl, String apiUrlPath, String apiBaseAuthenticationString, String userId, boolean usingMicroserviceAsBackend) {
        this.gcmBaseUrl = gcmBaseUrl;
        this.gcmUrlPath = gcmUrlPath;
        this.gcmAuthenticationString = gcmAuthenticationString;
        this.apiBaseUrl = apiBaseUrl;
        this.apiUrlPath = apiUrlPath;
        this.apiBaseAuthenticationString = apiBaseAuthenticationString;
        this.userId = userId;
        this.usingMicroserviceAsBackend = usingMicroserviceAsBackend;
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

    public boolean isUsingMicroserviceAsBackend() {
        return usingMicroserviceAsBackend;
    }

    public void setUsingMicroserviceAsBackend(boolean usingMicroserviceAsBackend) {
        this.usingMicroserviceAsBackend = usingMicroserviceAsBackend;
    }

    public String getGcmUrlPath() {
        return gcmUrlPath;
    }

    public void setGcmUrlPath(String gcmUrlPath) {
        this.gcmUrlPath = gcmUrlPath;
    }

    public String getApiUrlPath() {
        return apiUrlPath;
    }

    public void setApiUrlPath(String apiUrlPath) {
        this.apiUrlPath = apiUrlPath;
    }

    public boolean containsValidApiInfo() {
        if (!URLUtil.isValidUrl(apiBaseUrl) || TextUtils.isEmpty(apiBaseAuthenticationString) || TextUtils.isEmpty(apiUrlPath))
            return false;
        return true;
    }

    public boolean containsValidGcmInfo() {
        if (!URLUtil.isValidUrl(gcmBaseUrl) || TextUtils.isEmpty(gcmAuthenticationString) || TextUtils.isEmpty(gcmUrlPath))
            return false;
        return true;
    }


}
