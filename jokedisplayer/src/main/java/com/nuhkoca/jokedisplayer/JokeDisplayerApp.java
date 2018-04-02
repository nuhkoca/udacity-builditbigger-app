package com.nuhkoca.jokedisplayer;

import android.app.Application;

import timber.log.Timber;

public class JokeDisplayerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
