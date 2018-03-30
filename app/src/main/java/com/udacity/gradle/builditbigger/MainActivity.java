package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nuhkoca.jokedisplayer.Constants;
import com.nuhkoca.jokedisplayer.JokeDisplayerActivity;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener {

    private String mData;
    private static ProgressBar mPbJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPbJoke = findViewById(R.id.pbJoke);

        Button btnTellJoke = findViewById(R.id.btnTellJoke);
        btnTellJoke.setOnClickListener(this);

        getSupportLoaderManager().initLoader(Contants.LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(Contants.LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getSupportLoaderManager().destroyLoader(Contants.LOADER_ID);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new EndpointsAsyncTask(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        mData = data;
        mPbJoke.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {}

    @Override
    public void onClick(View v) {
        mPbJoke.setVisibility(View.VISIBLE);

        if (v.getId() == R.id.btnTellJoke) {
            Intent jokeIntent = new Intent(MainActivity.this, JokeDisplayerActivity.class);
            jokeIntent.putExtra(Constants.INTENT_EXTRA, mData);
            startActivity(jokeIntent);
        }
    }

    private static class EndpointsAsyncTask extends AsyncTaskLoader<String> {
        private JokeApi myApiService = null;
        private String mData = null;

        EndpointsAsyncTask(@NonNull Context context) {
            super(context);
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
                return myApiService.getJoke().execute().getJoke();
            } catch (IOException e) {
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
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
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
}