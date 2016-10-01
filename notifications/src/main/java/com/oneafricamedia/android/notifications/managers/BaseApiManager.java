package com.oneafricamedia.android.notifications.managers;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApiManager<T> {

    private final Class<T> mClz;
    protected T mApiInterface;

    public BaseApiManager(Class<T> clz) {
        mClz = clz;
    }

    protected void setApiInterface() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor);

        OkHttpClient client = builder.build();
        mApiInterface = new Retrofit.Builder()
                .baseUrl(getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(mClz);
    }

    protected Response<?> call(Call<?> call, String name) throws IOException {
        if (mApiInterface == null) setApiInterface();

        try {
            Response<?> execute = call.execute();
            if (execute.isSuccessful()) {
                Log.d("LogTag", "API call " + name + " successfull"); // TODO
            } else {
                Log.d("LogTag", "API call " + name + " failed"); // TODO
            }
            return execute;
        } catch (IOException e) {
            Log.d("LogTag", "API call " + name + " failed: " + e.getMessage()); // TODO
            throw e;
        }
    }

    protected void callAsync(Call call, Callback callback) {
        if (mApiInterface == null) setApiInterface();

        call.enqueue(callback);
    }

    protected abstract String getUrl();
}
