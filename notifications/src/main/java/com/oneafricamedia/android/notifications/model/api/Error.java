package com.oneafricamedia.android.notifications.model.api;

import com.oneafricamedia.android.notifications.model.api.responses.OneAfricaMediaApiResponse;

public class Error extends OneAfricaMediaApiResponse {
    private int id;
    private String message;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
