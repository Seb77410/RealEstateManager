package com.openclassrooms.realestatemanager;


import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.openclassrooms.realestatemanager.ui.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {

        // Test main Activity
        onView(withId(R.id.main_activity_toolbar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(withId(R.id.main_activity_list_frame_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void addHouseSellerActivityTest() {
        // Test AddHouseSellerActivity
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.add_house_seller)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.add_house_seller_activity_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.add_house_seller_activity_name)).check(matches(isDisplayed()));
        onView(withId(R.id.add_house_seller_activity_mail)).check(matches(isDisplayed()));
        onView(withId(R.id.add_house_seller_save_floating_button)).check(matches(isDisplayed()));
        onView(isRoot()).perform(pressBack());
    }
}
