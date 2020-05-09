package com.openclassrooms.realestatemanager;

import android.view.View;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.openclassrooms.realestatemanager.ui.activities.MainActivity;

import org.hamcrest.Matcher;
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
import static org.hamcrest.Matchers.not;

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

    @Test
    public void addPropertyActivityTest() {
        // Test AddPropertyActivity
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.add_property)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.add_property_activity_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.add_property_activity_scrollView)).check(matches(isDisplayed()));
        onView(withId(R.id.add_property_type_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.add_property_activity_house_seller_container)).check(matches(isDisplayed()));
        onView(withId(R.id.add_property_activity_property_sold)).check(matches(not(isDisplayed())));
        onView(withId(R.id.add_property_activity_price_filledTextField)).check(matches(isDisplayed()));
        onView(withId(R.id.add_property_activity_surface_filledTextField)).check(matches(isDisplayed()));
        onView(withId(R.id.add_property_activity_room_number_filledTextField)).check(matches(isDisplayed()));
        onView(withId(R.id.add_property_activity_address_filledTextField)).check(matches(isDisplayed()));
        onView(withId(R.id.add_property_checkbox_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.add_house_seller_save_floating_button)).check(matches(isDisplayed()));
        onView(isRoot()).perform(pressBack());
    }
/*
    @Test
    public void MapActivityTest() {
        // Test MapActivity
        onView(isRoot()).perform(waitFor());
        onView(withId(R.id.main_menu_map)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.map_activity_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.map_activity_map)).check(matches(isDisplayed()));
        onView(isRoot()).perform(pressBack());
    }


    @Test
    public void SearchActivityTest() {
        // Test SearchPropertyActivity
        onView(isRoot()).perform(waitFor());
        onView(withId(R.id.main_menu_search)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.search_property_activity_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.search_property_activity_property_sold)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.search_property_activity_create_date_container)).check(matches(isDisplayed()));
        onView(withId(R.id.search_property_activity_min_price)).check(matches(isDisplayed()));
        onView(withId(R.id.search_property_activity_max_price)).check(matches(isDisplayed()));
        onView(withId(R.id.search_property_activity_location)).check(matches(isDisplayed()));
        onView(withId(R.id.search_property_activity_nearby_textView)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.search_property_activity_nearby_content_layout)).check(matches(not(isDisplayed())));
        onView(withId(R.id.search_property_activity_min_media)).check(matches(isDisplayed()));
        onView(withId(R.id.search_property_activity_button)).check(matches(isDisplayed()));
        onView(isRoot()).perform(pressBack());
    }
 */

    /**
     * Perform action of waiting for a specific time.
     */
    private static ViewAction waitFor() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + (long) 3000 + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast( 3000);
            }
        };
    }

}
