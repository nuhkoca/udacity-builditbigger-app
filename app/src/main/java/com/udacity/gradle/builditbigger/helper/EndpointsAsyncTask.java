package com.udacity.gradle.builditbigger.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;
import com.udacity.gradle.builditbigger.callback.IConnectionCallbackListener;
import com.udacity.gradle.builditbigger.util.AddressUtils;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTaskLoader<String> {
    private JokeApi myApiService = null;
    private String mData = null;
    private IConnectionCallbackListener iConnectionCallbackListener;

    public EndpointsAsyncTask(@NonNull Context context, IConnectionCallbackListener iConnectionCallbackListener) {
        super(context);
        this.iConnectionCallbackListener = iConnectionCallbackListener;
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        if (myApiService == null) {
            myApiService = buildApi();
        }

        try {
            iConnectionCallbackListener.onError("");
            return myApiService.getJoke().execute().getJoke();
        } catch (IOException e) {
            iConnectionCallbackListener.onError(String.valueOf(e.getClass()));
            return e.getMessage();
        }
    }

    @Override
    public void deliverResult(String data) {
        if (mData == null) mData = data;

        super.deliverResult(data);
    }

    private static JokeApi buildApi() { // Only do this once
        JokeApi myApiService;
        JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl(AddressUtils.getIPAddress())
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        myApiService = builder.build();

        return myApiService;
    }
}