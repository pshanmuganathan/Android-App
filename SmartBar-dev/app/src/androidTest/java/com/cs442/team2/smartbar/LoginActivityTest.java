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
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by thedeeb on 11/27/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    private Context c;

    @Before
    public void setup() {
        c = InstrumentationRegistry.getContext();
    }



    @Test
    public void successfulLogin(){
        onView(withId(R.id.input_email)).perform(typeText("adeebahmed"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("smartbar2"), closeSoftKeyboard());
        onView(withText("Login")).perform(click());
        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("profile"));
    }

    @Test
    public void invalidLogin(){
        onView(withId(R.id.input_email)).perform(typeText("adeeb"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("wrongpassword"), closeSoftKeyboard());
        onView(withText("Login")).perform(click());
        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("login"));
    }

    @Test
    public void blankLogin(){
        onView(withId(R.id.input_email)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText(""), closeSoftKeyboard());
        onView(withText("Login")).perform(click());
        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("login"));
    }

    @Test
    public void invalidCharLogin() throws InterruptedException{
        String invalidChars[] = {".","#","$","[","]"};

        for(int i = 0; i < invalidChars.length; i++){
            onView(withId(R.id.input_email)).perform(typeText(invalidChars[i]), closeSoftKeyboard());
            onView(withId(R.id.input_password)).perform(typeText(invalidChars[i]), closeSoftKeyboard());
        }

        try{
            onView(withText("Login")).perform(click());
        }catch(Exception e){

        }
        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("login"));
    }

    @Test
    public void signupLinkTest() throws InterruptedException{
        try{
            onView(withId(R.id.link_signup)).perform(click());
        }catch(Exception e){

        }

        Assert.assertEquals(true, getCurrentActivity(c).toLowerCase().contains("signup"));
    }

    private String getCurrentActivity(Context context){
        try{Thread.sleep(600);}catch (Exception e){}

        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.d("Activity: ", cn.toString());

        return cn.toShortString();
    }

}