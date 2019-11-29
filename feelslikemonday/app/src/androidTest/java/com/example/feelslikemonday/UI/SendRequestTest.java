package com.example.feelslikemonday.UI;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SendRequestTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<LoginMainActivity> rule =
            new ActivityTestRule<>(LoginMainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }



    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
