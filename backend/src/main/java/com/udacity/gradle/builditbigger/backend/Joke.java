package com.udacity.gradle.builditbigger.backend;

/** The object model for the data we are sending through endpoints */
public class Joke {

    private String joke;

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}