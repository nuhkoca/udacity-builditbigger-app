package com.nuhkoca.jokedisplayer;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TextView_IsNull_OnFirstRunTest {

    @Rule
    public ActivityTestRule<JokeDisplayerActivity> activityTestRule = new ActivityTestRule<>(JokeDisplayerActivity.class);

    @Test
    public void jokeText_IsNull_OnFirstRun() {
        onView(withId(R.id.tvJoke)).check((matches(withText(""))));
    }
}