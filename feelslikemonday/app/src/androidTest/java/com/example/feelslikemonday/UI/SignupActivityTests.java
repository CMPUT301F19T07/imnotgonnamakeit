package com.example.feelslikemonday.ui;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.example.feelslikemonday.ui.login.SignupActivity;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for LoginActivity class. All the UI tests are written here.
 * Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class SignupActivityTests {

    private Solo solo;
    private UserDAO userDAO = UserDAO.getInstance();

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
     * This tests user going to sign in page
     */
    @Test
    public  void signUpActivityTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        TextView tv = (TextView)solo.getView(R.id.signupLink);
        solo.clickOnView(tv);
        solo.waitForActivity(SignupActivity.class);
    }

    /**
     * This tests user going to sign in page then clicking cancel
     */
    @Test
    public  void signUpCancelTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        TextView tv = (TextView)solo.getView(R.id.signupLink);
        solo.clickOnView(tv);
        solo.waitForActivity(SignupActivity.class);
        solo.clickOnButton("Cancel");
        solo.waitForActivity(LoginMainActivity.class);
    }

    /**
     * This tests user signing in
     */
    @Test
    public  void signUpTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        TextView tv = (TextView)solo.getView(R.id.signupLink);
        solo.clickOnView(tv);
        solo.waitForActivity(SignupActivity.class);
        String username = "mockNewUser2";
        String password = "12345";
        solo.enterText((EditText) solo.getView(R.id.signupUsernameEdit), username); // this test will only work once
        solo.enterText((EditText) solo.getView(R.id.signupPasswordEdit), password);
        solo.clickOnButton("Confirm");
        solo.waitForActivity(MainActivity.class);

        User newUser = new User(username,password);
        userDAO.delete(newUser, new VoidCallback() {
            @Override
            public void onCallback() {
            }
        });
        //TO-DO: ADD LATCH
    }

    /**
     * This tests existing user
     */
    @Test
    public  void existingUserSignUpTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        TextView tv = (TextView)solo.getView(R.id.signupLink);
        solo.clickOnView(tv);
        solo.waitForActivity(SignupActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signupUsernameEdit), "user");
        solo.enterText((EditText) solo.getView(R.id.signupPasswordEdit), "12345");
        solo.clickOnButton("Confirm");
        solo.waitForText("Error: This user already exists");
    }

    /**
     * This tests empty username
     */
    @Test
    public  void emptyUsernameSignUpTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        TextView tv = (TextView)solo.getView(R.id.signupLink);
        solo.clickOnView(tv);
        solo.waitForActivity(SignupActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signupPasswordEdit), "12345");
        solo.clickOnButton("Confirm");
        solo.waitForText("Error: Empty Field");
    }

    /**
     * This tests empty password
     */
    @Test
    public void emptyPasswordSignUpTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        TextView tv = (TextView)solo.getView(R.id.signupLink);
        solo.clickOnView(tv);
        solo.waitForActivity(SignupActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signupUsernameEdit), "user");
        solo.clickOnButton("Confirm");
        solo.waitForText("Error: Empty Field");
    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}


