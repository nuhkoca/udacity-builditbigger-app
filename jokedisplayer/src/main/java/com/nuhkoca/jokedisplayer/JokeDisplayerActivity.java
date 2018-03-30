package com.nuhkoca.jokedisplayer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class JokeDisplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_displayer);
        final JokeDisplayerViewModel jokeDisplayerViewModel = ViewModelProviders.of(this).get(JokeDisplayerViewModel.class);

        final TextView tvJoke = findViewById(R.id.tvJoke);

        ImageView ivJoke = findViewById(R.id.ivJoke);
        Glide.with(this)
                .load(R.drawable.troll)
                .into(ivJoke);

        final String joke = getIntent().getStringExtra(Constants.INTENT_EXTRA);

        if (joke != null) {
            jokeDisplayerViewModel.setJoke(joke);

            jokeDisplayerViewModel.jokes.observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    tvJoke.setText(s);
                }
            });
        }
    }
}