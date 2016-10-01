package com.oneafricamedia.android.notifications.model.api.responses;

import com.oneafricamedia.android.notifications.model.api.Meta;

import java.io.Serializable;

public abstract class OneAfricaMediaApiResponse<Data> implements Serializable {
    public Error error;
    public Meta meta;
    public Data data;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
