package com.nuhkoca.jokedisplayer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nuhkoca.jokedisplayer.helper.Constants;

public class JokeDisplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_displayer);
        final JokeDisplayerViewModel jokeDisplayerViewModel = ViewModelProviders.of(this).get(JokeDisplayerViewModel.class);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final TextView tvJoke = findViewById(R.id.tvJoke);
        TextView tvErrorJoke = findViewById(R.id.tvErrorJoke);

        ImageView ivJoke = findViewById(R.id.ivJoke);
        Glide.with(this)
                .load(R.drawable.troll)
                .into(ivJoke);

        final String joke = getIntent().getStringExtra(Constants.INTENT_EXTRA);
        String error = getIntent().getStringExtra(Constants.INTENT_ERROR_EXTRA);

        if (joke != null) {
            jokeDisplayerViewModel.setJoke(joke);

            jokeDisplayerViewModel.jokes.observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    tvJoke.setText(s);
                }
            });
        }

        if (!TextUtils.isEmpty(error)) {
            tvErrorJoke.setVisibility(View.VISIBLE);
        } else {
            tvErrorJoke.setVisibility(View.GONE);
        }

        Log.d("Joke", error);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}