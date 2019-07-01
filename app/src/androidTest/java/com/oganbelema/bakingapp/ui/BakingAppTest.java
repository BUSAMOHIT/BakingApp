package com.oganbelema.bakingapp.ui;


import android.widget.TextView;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.oganbelema.bakingapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void bakingAppTest() {

        MyIdlingResource myIdlingResource = new MyIdlingResource();

        IdlingRegistry.getInstance().register(myIdlingResource);

        onView(withId(R.id.recipesRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(2))
                .perform(click());

        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText("Brownies")));


        onView(withId(R.id.stepsRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(2))
                .perform(click());

        onView(withId(R.id.full_screen_video))
                .check(matches(isDisplayed()));

        IdlingRegistry.getInstance().unregister(myIdlingResource);


    }

}
