package com.nuhkoca.jokedisplayer.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class JokeDisplayerViewModel extends ViewModel {

    public MutableLiveData<String> jokes = new MutableLiveData<>();

    public void setJoke(String joke) {
        jokes.setValue(joke);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        jokes = null;
    }
}