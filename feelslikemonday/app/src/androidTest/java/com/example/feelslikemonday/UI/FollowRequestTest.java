package com.example.feelslikemonday.UI;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.feelslikemonday.R;
import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

/**
 * Test class for AddNewMoodActivity. All the UI tests are written here.
 * Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class FollowRequestTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<LoginMainActivity> rule =
            new ActivityTestRule<>(LoginMainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Gets the activity
     *
     * @throws Exception
     */

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * This tests checking if follow requests are appearing
     */

    @Test
    public void followRequestAppearing() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "mockyuningtest");
        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "123456");
        solo.clickOnButton("LOGIN");
        solo.clickOnImageButton(0);
        solo.clickOnText("Follower Request");

        assertTrue(solo.waitForText("ag01", 1, 2000));

//       TESTING FORMAT MAY BE USEFUL LATER, KEEP FOR NOW


//        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
//        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "ag01");
//        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "123456");
//        solo.clickOnButton("LOGIN");
//        solo.clickOnImageButton(0);
//        solo.clickOnText("Send Request");
//
//        solo.enterText((EditText) solo.getView(R.id.send_request_username), "mockyuningtest");
//
//        solo.clickOnText("SEND");
//        solo.sleep(500);
//        solo.clickOnActionBarItem(R.id.action_settings);
//        solo.clickOnMenuItem("Logout");
//
//        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "mockyuningtest");
//        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "123456");
//        solo.clickOnImageButton(0);
//
//        solo.clickOnText("Follower Request");
//        assertTrue(solo.waitForText("ag01", 1, 2000));
//        solo.sleep(500);
//
//        solo.clickOnText("REJECT");
//
//        solo.sleep(500);
//        solo.clickOnActionBarItem(R.id.action_settings);
//        solo.clickOnMenuItem("Logout");

    }

    /**
     * Closes the activity after each test
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

