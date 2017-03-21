package com.cs442.team2.smartbar;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by thedeeb on 11/27/16.
 */

@RunWith(AndroidJUnit4.class)
public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> mActivityRule = new ActivityTestRule<>(SignupActivity.class);

    private Context c;

    @Before
    public void setup() {
        c = InstrumentationRegistry.getContext();
    }

    @Test
    public void successfulSignup(){
        onView(withId(R.id.txtfname)).perform(typeText("adeeb"), closeSoftKeyboard());
        onView(withId(R.id.txtlname)).perform(typeText("ahmed"), closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("adeebahmed@adeebahmed.com"), closeSoftKeyboard());
        onView(withId(R.id.txtPass)).perform(typeText("smartbar2"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPass)).perform(typeText("smartbar2"), closeSoftKeyboard());
        onView(withId(R.id.txtDOB)).perform(typeText("8/26/1995"), closeSoftKeyboard());
        onView(withId(R.id.txtHeight)).perform(typeText("70"), closeSoftKeyboard());
        onView(withId(R.id.txtWeight)).perform(typeText("175"), closeSoftKeyboard());

        onView(withId(R.id.btnSignUp)).perform(click());
        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("login"));
    }

    @Test
    public void emptyFields(){
        onView(withId(R.id.txtfname)).perform(typeText("adeeb"), closeSoftKeyboard());
        onView(withId(R.id.txtlname)).perform(typeText("ahmed"), closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("adeebahmed@adeebahmed.com"), closeSoftKeyboard());
        onView(withId(R.id.txtPass)).perform(typeText("smartbar2"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPass)).perform(typeText("smartbar2"), closeSoftKeyboard());

        onView(withId(R.id.btnSignUp)).perform(click());
        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("signup"));
    }

    @Test
    public void mismatchedPassword(){
        onView(withId(R.id.txtfname)).perform(typeText("adeeb"), closeSoftKeyboard());
        onView(withId(R.id.txtlname)).perform(typeText("ahmed"), closeSoftKeyboard());
        onView(withId(R.id.txtEmail)).perform(typeText("adeebahmed@adeebahmed.com"), closeSoftKeyboard());
        onView(withId(R.id.txtPass)).perform(typeText("smartbar2"), closeSoftKeyboard());
        onView(withId(R.id.txtConfirmPass)).perform(typeText("adsfjkad"), closeSoftKeyboard());
        onView(withId(R.id.txtDOB)).perform(typeText("8/26/1995"), closeSoftKeyboard());
        onView(withId(R.id.txtHeight)).perform(typeText("70"), closeSoftKeyboard());
        onView(withId(R.id.txtWeight)).perform(typeText("175"), closeSoftKeyboard());

        onView(withId(R.id.btnSignUp)).perform(click());
        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("signup"));
    }

    private String getCurrentActivity(Context context){
        try{Thread.sleep(500);}catch (Exception e){}

        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.d("Activity: ", cn.toString());

        return cn.toShortString();
    }

}