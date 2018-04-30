package com.mytaxi.android_demo;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.*;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mytaxi.android_demo.activities.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());
    }

    @Test
    public void testLoginSearchAndCall() throws InterruptedException {
        String username = "whiteelephant261";

            Thread.sleep(5000);

        //Username field and type text
        ViewInteraction fieldUsername = onView(allOf(withId(R.id.edt_username), childAtPosition(childAtPosition(
                withClassName(is("android.support.design.widget.TextInputLayout")),
                0), 0), isDisplayed()));
        fieldUsername.perform(click());
        fieldUsername.perform(typeText(username), closeSoftKeyboard());

        //Password field and type text
        ViewInteraction fieldPswd = onView(allOf(withId(R.id.edt_password), childAtPosition(childAtPosition(
                withClassName(is("android.support.design.widget.TextInputLayout")),
                0), 0), isDisplayed()));
        fieldPswd.perform(typeText("video1"), closeSoftKeyboard());

        //Find login button and click it
        ViewInteraction loginBtn = onView(allOf(withId(R.id.btn_login), withText("Login"),
                childAtPosition(
                        childAtPosition(withId(android.R.id.content), 0), 2), isDisplayed()));
        loginBtn.perform(click());

        Thread.sleep(5000);

        //Taxi part
        //Find search field and text "sa"
        ViewInteraction fieldSearch = onView(
                allOf(withId(R.id.textSearch),
                        childAtPosition(allOf(withId(R.id.searchContainer),childAtPosition(withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                                1)),0),isDisplayed()));
        fieldSearch.perform(typeText("sa"), closeSoftKeyboard());

        Thread.sleep(3000);

        //Find in the screen and click the taxi with name "Sarah Friedrich"
        ViewInteraction fieldTaxiName = onView(withText("Sarah Friedrich")).inRoot(isPlatformPopup()).check(matches(isDisplayed()));
                        fieldTaxiName.perform(click());
        //Click call button
        ViewInteraction btnCall = onView(allOf(withId(R.id.fab),childAtPosition(childAtPosition(withId(android.R.id.content),0),2),isDisplayed()));
        btnCall.perform(click());

        Thread.sleep(3000);
        //Validation of test
        Context appContext = InstrumentationRegistry.getContext();
        assertNotEquals("com.mytaxi.android_demo", appContext.getPackageName());
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}