package com.example.feelslikemonday.Home;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.example.feelslikemonday.ui.moods.AddNewMoodActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for AddNewMoodActivity. All the UI tests are written here.
 * Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class AddNewMoodActivityTest {

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
     * This test ensures user was able to switch from login to main activities
     */
    @Test
    public void homePageSwitchTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.clickOnButton("LOGIN");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * This tests adding a new mood event
     */
    @Test
    public void addMoodTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "rehab");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "rehab");
        solo.clickOnButton("LOGIN");
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickOnMenuItem("New");
        solo.assertCurrentActivity("Wrong Activity", AddNewMoodActivity.class);
        solo.clickOnView(solo.getView(R.id.mood_spinner));
        solo.pressSpinnerItem(0, 0);
        solo.enterText((EditText) solo.getView(R.id.editText8), "didn't drink coffee"); // reason
        solo.clickOnView(solo.getView(R.id.social_spinner));
        solo.pressSpinnerItem(0, 0);
        solo.clickOnButton("save");
        solo.sleep(500);
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickOnMenuItem("Logout");
    }

    /**
     * This tests canceling a mood after wanting to add a new mood event
     */
    @Test
    public void cancelAddMoodTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "mockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.clickOnButton("LOGIN");
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickOnMenuItem("New");
        solo.assertCurrentActivity("Wrong Activity", AddNewMoodActivity.class);
        solo.clickOnView(solo.getView(R.id.mood_spinner));
        solo.pressSpinnerItem(0, 0);
        solo.enterText((EditText) solo.getView(R.id.editText8), "going to cancel"); // reason
        solo.clickOnView(solo.getView(R.id.social_spinner));
        solo.pressSpinnerItem(0, 0);
        solo.clickOnButton("save");
        solo.sleep(500);
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickOnMenuItem("Logout");
    }
/*
    //PLEASE DO NOT REMOVE, I AM STILL WORKING ON FIGURING OUT HOW TO SWIPE IN A TEST

    @Test
    public  void editMoodTest(){
        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "mockUser3");
        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "12345");
        solo.clickOnButton("LOGIN");
        solo.sleep(2000);
        solo.swipe(new PointF(1000, 200), new PointF(1000, 200),new PointF(10, 200), new PointF(10, 200));
        // click on edit, index =0
        //still figuring out how to use robotium to test the swiping feature
        solo.assertCurrentActivity("Wrong Activity", AddNewMoodActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText8), "edited");
        solo.clickOnButton("save");
        solo.sleep(500);
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickOnMenuItem("Logout");
    }

    @Test
    public  void viewMoodTest(){
        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "mockUser3");
        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "12345");
        solo.clickOnButton("LOGIN");
        solo.sleep(2000);
        solo.swipe(new PointF(1000, 200), new PointF(1000, 200),new PointF(10, 200), new PointF(10, 200));
        // click on view, index =1
        //still figuring out how to use robotium to test the swiping feature
        solo.assertCurrentActivity("Wrong Activity", DisplayCurrentMood.class);
        solo.clickOnButton("BACK");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.sleep(500);
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickOnMenuItem("Logout");
    }

    @Test
    public  void deleteMoodTest(){
        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "mockUser3");
        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "12345");
        solo.clickOnButton("LOGIN");
        solo.sleep(2000);
        solo.swipe(new PointF(1000, 200), new PointF(1000, 200),new PointF(10, 200), new PointF(10, 200));
        // click on delete, index =2
        //still figuring out how to use robotium to test the swiping feature
        //check that mood is no longer in user's moodhistory
        solo.sleep(500);
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.clickOnMenuItem("Logout");
    }
 */

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

