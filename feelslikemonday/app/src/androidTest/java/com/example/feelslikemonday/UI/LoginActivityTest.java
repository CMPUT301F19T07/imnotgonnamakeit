package com.example.feelslikemonday.UI;

import android.app.Activity;
import android.widget.EditText;

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

import static junit.framework.TestCase.assertTrue;

/**
 * Test class for LoginActivity class. All the UI tests are written here.
 * Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<LoginMainActivity> rule =
            new ActivityTestRule<>(LoginMainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp()throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the activity
     * @throws Exception
     */
    @Test
    public void start()throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * This tests user logging in
     */
    @Test
    public  void loginTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "myMockUser");
        solo.waitForText("myMockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.waitForText("12345");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity(MainActivity.class);

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));
    }

    /**
     * This tests if user does not enter password
     */
    @Test
    public  void noPasswordTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockUser");
        solo.waitForText("mockUser");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));

        assertTrue(solo.waitForText("Missing username or password", 1, 2000));

        assertTrue( solo.waitForActivity( LoginMainActivity.class));
    }

    /**
     * This tests if user does not enter  username
     */
    @Test
    public  void noUsernameTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.waitForText("12345");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));

        assertTrue(solo.waitForText("Missing username or password", 1, 2000));

        assertTrue( solo.waitForActivity( LoginMainActivity.class));
    }

    /**
     * This tests if user enters incorrect password
     */
    @Test
    public  void incorrectPasswordTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "myMockUser");
        solo.waitForText("myMockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "1234056");
        solo.waitForText("1234056");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));

        assertTrue(solo.waitForText("Incorrect Password", 1, 2000));

        assertTrue( solo.waitForActivity( LoginMainActivity.class));
    }

    /**
     * This tests if user enters an account that does not exists (wrong username)
     */
    @Test
    public  void UserNotExistTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockBanana");
        solo.waitForText("mockBanana");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123405");
        solo.waitForText("123405");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));

        assertTrue(solo.waitForText("User does not exist", 1, 2000));

        assertTrue( solo.waitForActivity( LoginMainActivity.class));
    }

    /**
     * Tears down the activity
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}