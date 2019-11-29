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
import com.example.feelslikemonday.ui.moods.AddNewMoodActivity;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class SwipeMoodTests {

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
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "myMockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        SwipeMenuListView listView = (SwipeMenuListView)solo.getView(R.id.listView);
        int[] location = new int[2];
        listView.getLocationInWindow(location);
        solo.drag(location[0]+500,location[0],location[1],location[1],10);
        solo.clickOnText("View");
        solo.assertCurrentActivity("Wrong Activity", DisplayCurrentMood.class);
        solo.clickOnView(solo.getView(R.id.button_back));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));
    }

    /**
     * This tests the ability for a user to edit the details of a given mood event
     * For this test, we test the user clicking the edit option on their most recent mood event
     * The test ensures that the switch between activities is correct using the edit swipe option
     */
    @Test
    public void editMoodTest(){
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "myMockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        solo.waitForActivity(MainActivity.class);
        SwipeMenuListView listView = (SwipeMenuListView)solo.getView(R.id.listView);
        int[] location = new int[2];
        listView.getLocationInWindow(location);
        solo.drag(location[0]+500,location[0],location[1],location[1],10);

        solo.clickOnText("Edit");

        solo.waitForActivity(AddNewMoodActivity.class);
        solo.clickOnView(solo.getView(R.id.mood_cancel));

        solo.waitForActivity(MainActivity.class);
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));


    }

    /**
     * This tests the ability for a user to delete a given mood event
     * For this test, we test the user deleting their most recent mood event
     */
    @Test
    public void deleteMoodTest() {
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "myMockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));
        SwipeMenuListView listView = (SwipeMenuListView) solo.getView(R.id.listView);
        int[] location = new int[2];
        listView.getLocationInWindow(location);
        solo.drag(location[0] + 500, location[0], location[1], location[1], 10);

        solo.clickOnActionBarItem(R.id.action_settings);
        solo.waitForText("Logout");
        solo.clickOnMenuItem("Logout");
        assertTrue( solo.waitForActivity( LoginMainActivity.class));
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
