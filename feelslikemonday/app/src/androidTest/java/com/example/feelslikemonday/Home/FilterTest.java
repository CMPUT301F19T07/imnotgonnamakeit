package com.example.feelslikemonday.Home;

import android.app.Activity;
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
public class FilterTest {

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
     * This test ensures user was able to filter moods. It logs in the test user, checks the anger mood exists, filters it out, then checks that it's no longer visible.
     */
    @Test
    public void filterTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "agtest1");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123456");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);

        assertTrue(solo.searchText(Pattern.quote("11:31")));

        Button filterButton = (Button) solo.getView(R.id.filter_button);
        int[] location = new int[2];
        filterButton.getLocationInWindow(location);
        solo.clickOnScreen(location[0], location[1]);

        Switch filterSwitch = (Switch) solo.getView(R.id.switch1);
        int[] location1 = new int[2];
        filterSwitch.getLocationInWindow(location1);
        solo.clickOnScreen(location1[0], location1[1]);

        Button okButton = (Button) solo.getView(R.id.ok_button);
        int[] location2 = new int[2];
        okButton.getLocationInWindow(location2);
        solo.clickOnScreen(location2[0], location2[1]);

        assertFalse(solo.searchText(Pattern.quote("11:31")));

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

