package com.udacity.gradle.builditbigger;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.nuhkoca.jokedisplayer.helper.Constants;
import com.nuhkoca.jokedisplayer.ui.JokeDisplayerActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by nuhkoca on 3/25/18.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityIntent_StartsAnotherTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);


    @SuppressWarnings("unchecked")
    @Test
    public void intent_OpensActivity() {
        onView(allOf(withId(R.id.btnTellJoke))).perform(click());

        intended(hasComponent(hasClassName(JokeDisplayerActivity.class.getName())));
        intended(hasExtra(equalTo(Constants.INTENT_EXTRA), notNullValue())); //check it has extra FOR THE JOKE

        onView(allOf(withId(R.id.tvJoke))).check(matches(not(withText(""))));
    }
}