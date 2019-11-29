package com.example.feelslikemonday.UI;


import android.app.Activity;
import android.widget.Button;
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

@RunWith(AndroidJUnit4.class)
public class SendRequestTest {

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
     * This testing checking if I click the send Button, but without input
     * This will give a toast message
     */
    @Test
    public void sendRequestToNoInput(){

        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockXiaoTest");
        solo.waitForText("mockXiaoTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123");
        solo.waitForText("123");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "");

        Button sendButton = (Button) solo.getView(R.id.send_request_send);
        int[] location = new int[2];
        sendButton.getLocationInWindow(location);
        solo.clickOnScreen(location[0], location[1]);

        assertTrue(solo.waitForText("Please input a username", 1, 2000));

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));
    }

    /**
     * This testing checking if I send the request to myself
     * This will give a toast message
     */
    @Test
    public void sendRequestToYourself(){

        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockXiaoTest");
        solo.waitForText("mockXiaoTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123");
        solo.waitForText("123");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "mockXiaoTest");
        solo.waitForText("mockXiaoTest");

        Button sendButton = (Button) solo.getView(R.id.send_request_send);
        int[] location = new int[2];
        sendButton.getLocationInWindow(location);
        solo.clickOnScreen(location[0], location[1]);

        assertTrue(solo.waitForText("Error: You cannot send a request to yourself", 1, 2000));

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));

    }

    /**
     * This testing checking if I send the request to the username which is a invalid user, meaning the user is not in the database
     * This will give a toast message
     */
    @Test
    public void sendRequestToInvalidUserTest(){

        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockXiaoTest");
        solo.waitForText("mockXiaoTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123");
        solo.waitForText("123");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "mockXiaoInvalidUserTest");
        solo.waitForText("mockXiaoInvalidUserTest");

        Button sendButton = (Button) solo.getView(R.id.send_request_send);
        int[] location = new int[2];
        sendButton.getLocationInWindow(location);
        solo.clickOnScreen(location[0], location[1]);

        assertTrue(solo.waitForText("This user does not exist, invite your friends", 1, 2000));

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));



    }

    /**
     * This testing checking if I send the request to the user which I am following
     * This will give a toast message
     */
    @Test
    public void sendRequestToFollowedUserTest(){

        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockXiaoTest");
        solo.waitForText("mockXiaoTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123");
        solo.waitForText("123");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "mockFollowedTest");
        solo.waitForText("mockFollowedTest");

        Button sendButton = (Button) solo.getView(R.id.send_request_send);
        int[] location = new int[2];
        sendButton.getLocationInWindow(location);
        solo.clickOnScreen(location[0], location[1]);

        assertTrue(solo.waitForText("You have already followed mockFollowedTest", 1, 2000));

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));



    }

    /**
     * This testing checking if I send the request to the user which I requested before and I am still in other's requestList
     * This will give a toast message
     */
    @Test
    public void sendRequestToRequestedUserTest(){

        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockXiaoTest");
        solo.waitForText("mockXiaoTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123");
        solo.waitForText("123");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "mockSentTest");
        solo.waitForText("mockSentTest");

        Button sendButton = (Button) solo.getView(R.id.send_request_send);
        int[] location = new int[2];
        sendButton.getLocationInWindow(location);
        solo.clickOnScreen(location[0], location[1]);

        assertTrue(solo.waitForText("Request already sent to mockSentTest", 1, 2000));

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));


    }

    /**
     * This testing checking if I send the request successfully to the user which is valid.
     * This will give a toast message and I will in other's RequestList
     * This test will click reject Button for making the user is first request in the next Test.
     */
    @Test
    public void sendRequestToValidUserTest(){
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockXiaoTest");
        solo.waitForText("mockXiaoTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123");
        solo.waitForText("123");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "mockLeTest");

        Button sendButton = (Button) solo.getView(R.id.send_request_send);
        int[] location = new int[2];
        sendButton.getLocationInWindow(location);
        solo.clickOnScreen(location[0], location[1]);
        solo.waitForText("Sent Request Successfully to mockLeTest");

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");

        solo.waitForActivity( LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockLeTest");
        solo.waitForText("mockLeTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "456");
        solo.waitForText("456");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity( MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Follower Request");
        assertTrue(solo.waitForText("mockXiaoTest", 1, 2000));
        solo.clickOnView(solo.getView(R.id.request_reject_button));

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
