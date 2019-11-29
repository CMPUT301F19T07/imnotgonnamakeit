package com.example.feelslikemonday.UI;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.ui.friends.FriendList;
import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class FriendsTest {

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

    @Test
    public void showFriendList(){
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signup_username_edit), "uTEST-sill");
        solo.enterText((EditText) solo.getView(R.id.signup_password_edit), "password");
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "mockLeTest");
        solo.clickOnButton("SEND");
        solo.waitForText("Sent Request Successfully to mockLeTest",1, 2000);
        solo.clickOnText("Logout");

        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockLeTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "456");
        solo.clickOnButton("LOGIN");
        solo.clickOnText("Follower Request");
        TextView textView = (TextView) solo.getView(R.id.following_friend);
        String requester = textView.getText().toString();
        assertEquals("mockXiaoTest", requester);

        //assertTrue(solo.waitForText("mockLeTest", 1, 2000));

    }

    @Test
    public void unfollowFriend(){
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockTest");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "123");
        solo.clickOnButton("LOGIN");
        solo.clickOnText("Send Request");
        solo.enterText((EditText) solo.getView(R.id.send_request_username), "xiaole2");
        solo.clickOnButton("SEND");
        solo.waitForText("Sent Request Successfully to xiaole2",1, 2000);
        solo.clickOnText("Logout");

        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "xiaole2");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "456");
        solo.clickOnButton("LOGIN");
        solo.clickOnText("Follower Request");
        TextView textView = (TextView) solo.getView(R.id.following_friend);
        String requester = textView.getText().toString();
        assertEquals("mockTest", requester);
        //solo.waitForText("mockTest", 1, 2000);


        solo.clickOnText("Friends");
        solo.clickOnButton("UNFOLLOW");
        MainActivity activity = (MainActivity) solo.getCurrentActivity();
        //final FriendList friendList = activity.

    }


    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }




}
