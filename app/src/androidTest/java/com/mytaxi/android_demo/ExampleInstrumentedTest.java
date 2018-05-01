package com.mytaxi.android_demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.*;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String USERNAME = "whiteelephant261";
    private static final String PASSWORD = "video1";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private void clickWithIdAndTypeText(int id, String txt ){
       ViewInteraction typeField = onView(withId(id)).perform(click());
       typeField.perform(typeText(txt), closeSoftKeyboard());
    }

    private void clickButtonWithId(int id) {
        ViewInteraction loginBtn = onView(withId(id));
        this.clickAction(loginBtn);
    }

    private void selectDriverByName(String driverName){
        ViewInteraction fieldTaxiName = onView(withText(driverName)).inRoot(isPlatformPopup()).check(matches(isDisplayed()));
        this.clickAction(fieldTaxiName);
    }

    private void clickAction(ViewInteraction view){
       view.perform(click());
    }

    @Test
    private void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());
    }

    @Test
    public void testLoginSearchAndCall() throws InterruptedException {

        Thread.sleep(5000);

        //Username field and type text
        this.clickWithIdAndTypeText(R.id.edt_username,USERNAME);

        //Password field and type text
        this.clickWithIdAndTypeText(R.id.edt_password,PASSWORD);

        //Find login button and click it
        this.clickButtonWithId(R.id.btn_login);

        Thread.sleep(5000);

        //Taxi part
        //Find search field and text "sa"
        this.clickWithIdAndTypeText(R.id.textSearch,"sa");

        Thread.sleep(3000);

        this.selectDriverByName("Sarah Friedrich");

        //Click call button
        this.clickButtonWithId(R.id.fab);

        Thread.sleep(3000);

        //Validation of test
        Context appContext = InstrumentationRegistry.getContext();
        assertNotEquals("com.mytaxi.android_demo", appContext.getPackageName());
    }
}