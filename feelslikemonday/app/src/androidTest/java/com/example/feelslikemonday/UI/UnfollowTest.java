package com.example.feelslikemonday.UI;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Test class for AddNewMoodActivity. All the UI tests are written here.
 * Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class UnfollowTest {

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
     * This test ensures user was able to follow, then un-follow a user. It logs in as agtest1, sends a request to agtest2, logs in to agtest2, accepts the request, logs into agtest1, then unfollows
     */
    @Test
    public void unfollowTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "agtest1");
        solo.waitForText("agtest1");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123456");
        solo.waitForText("123456");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);

        solo.clickOnImageButton(0);
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "agtest2");

        solo.clickOnButton("Send");
        solo.clickOnMenuItem("");

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");

        solo.waitForActivity( LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "agtest2");
        solo.waitForText("agtest2");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123456");
        solo.waitForText("123456");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Follower Request");


        solo.clickOnButton("Accept");

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");

        solo.waitForActivity( LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "agtest1");
        solo.waitForText("agtest1");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123456");
        solo.waitForText("123456");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.waitForText("Following");
        solo.clickOnText("Following");

        assertTrue(solo.searchText(Pattern.quote("agtest2")));

        solo.clickOnImageButton(0);
        solo.waitForText("My Friends");
        solo.clickOnText("My Friends");
        assertTrue(solo.searchText(Pattern.quote("agtest2")));
        solo.waitForText("Unfollow");
        solo.clickOnText("Unfollow");
        assertFalse(solo.searchText(Pattern.quote("agtest2")));

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));

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

