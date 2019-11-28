package com.example.feelslikemonday.Home;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.ui.home.DisplayCurrentMood;
import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SwipeMoodEventTests {

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
     * This tests the ability for a user to view the details of a given mood event
     * For this test, we test the user viewing their most recent mood event
     */
    @Test
    public void viewMoodTest(){
        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "myMockUser");
        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "12345");
        solo.clickOnView(solo.getView(R.id.loginConfirmButton));

        SwipeMenuListView listView = (SwipeMenuListView)solo.getView(R.id.listView);
        int[] location = new int[2];
        listView.getLocationInWindow(location);
        solo.drag(location[0]+500,location[0],location[1],location[1],10);
        solo.clickOnText("View");
        solo.assertCurrentActivity("Wrong Activity", DisplayCurrentMood.class);

        solo.clickOnView(solo.getView(R.id.buttonBack));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }


    /**
     * This tests the ability for a user to edit the details of a given mood event
     * For this test, we test the user viewing their most recent mood event
     * The test changed the reason attribute and then checks the view to ensure the reason has changed
     */
    @Test
    public void editMoodTest(){
        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "myMockUser");
        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "12345");
        solo.clickOnView(solo.getView(R.id.loginConfirmButton));
        SwipeMenuListView listView = (SwipeMenuListView)solo.getView(R.id.listView);
        int[] location = new int[2];
        listView.getLocationInWindow(location);
        solo.drag(location[0]+500,location[0],location[1],location[1],10);

        solo.clickOnText("Edit");
        solo.sleep(1000);
        solo.clearEditText(R.id.editText8);
        solo.enterText((EditText) solo.getView(R.id.editText8), "changed reason");

        solo.clickOnView(solo.getView(R.id.saveMoodButton));
        solo.assertCurrentActivity("Wrong Activity", DisplayCurrentMood.class);

    }

/*
    //PLEASE DO NOT REMOVE, I AM STILL WORKING ON FIGURING OUT HOW TO SWIPE IN A TEST

    @Test
    public  void editMoodTest(){

            solo.assertCurrentActivity("Wrong Activity", AddNewMoodActivity.class);

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

        @Test
    protected void swipeLeftOnText(String text) {
        int fromX, toX, fromY, toY;
        int[] location = new int[2];

        View row = solo.getText(text);
        row.getLocationInWindow(location);

        // fail if the view with text cannot be located in the window
        if (location.length == 0) {
            fail("Could not find text: " + text);
        }

        fromX = location[0] + 100;
        fromY = location[1];

        toX = location[0];
        toY = fromY;

        solo.drag(fromX, toX, fromY, toY, 10);
    }

 */

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
