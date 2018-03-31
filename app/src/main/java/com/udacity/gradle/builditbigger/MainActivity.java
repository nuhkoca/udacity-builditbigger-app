package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.nuhkoca.jokedisplayer.JokeDisplayerActivity;
import com.udacity.gradle.builditbigger.callback.IConnectionCallbackListener;
import com.udacity.gradle.builditbigger.helper.Constants;
import com.udacity.gradle.builditbigger.helper.EndpointsAsyncTask;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener, IConnectionCallbackListener {

    private String mData;
    private ProgressBar mPbJoke;
    private String mErrorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection ConstantConditions
        if (BuildConfig.FLAVOR.equals("free")) {
            setTitle(getString(R.string.app_name) + " - Free");
        } else {
            setTitle(getString(R.string.app_name) + " - Paid");
        }

        mPbJoke = findViewById(R.id.pbJoke);

        Button btnTellJoke = findViewById(R.id.btnTellJoke);
        btnTellJoke.setOnClickListener(this);

        getSupportLoaderManager().initLoader(Constants.LOADER_ID, null, this);
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

        getSupportLoaderManager().restartLoader(Constants.LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getSupportLoaderManager().destroyLoader(Constants.LOADER_ID);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new EndpointsAsyncTask(this, this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        mData = data;
        mPbJoke.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }

    @Override
    public void onClick(View v) {
        mPbJoke.setVisibility(View.VISIBLE);

        if (v.getId() == R.id.btnTellJoke) {
            Intent jokeIntent = new Intent(MainActivity.this, JokeDisplayerActivity.class);
            jokeIntent.putExtra(com.nuhkoca.jokedisplayer.helper.Constants.INTENT_EXTRA, mData);
            jokeIntent.putExtra(com.nuhkoca.jokedisplayer.helper.Constants.INTENT_ERROR_EXTRA, mErrorData);
            startActivity(jokeIntent);
        }
    }

    @Override
    public void onError(String cause) {
        mErrorData = cause;
    }
}