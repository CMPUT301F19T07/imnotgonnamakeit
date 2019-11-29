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
    }

    /**
     * This tests the ability for a user to edit the details of a given mood event
     * For this test, we test the user viewing their most recent mood event
     */
    @Test
    public void editMoodTest(){
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "myMockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));

        SwipeMenuListView listView = (SwipeMenuListView)solo.getView(R.id.listView);
        int[] location = new int[2];
        listView.getLocationInWindow(location);
        solo.drag(location[0]+500,location[0],location[1],location[1],10);

        solo.clickOnText("Edit");
        solo.assertCurrentActivity("Wrong Activity", AddNewMoodActivity.class);

         /*
        solo.clearEditText(R.id.reason_edit_text);
        solo.enterText((EditText) solo.getView(R.id.reason_edit_text), "edited");
        solo.clickOnView(solo.getView(R.id.button11));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.drag(location[0]+500,location[0],location[1],location[1],10);
        solo.clickOnText("View");
        assertTrue(solo.searchText("edited"));
         */
    }

    /**
     * This tests the ability for a user to edit the details of a given mood event
     * For this test, we test the user viewing their most recent mood event
     */
    @Test
    public void deleteMoodTest() {
        solo.enterText((EditText) solo.getView(R.id.login_username_edit), "myMockUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit), "12345");
        solo.clickOnView(solo.getView(R.id.login_confirm_button));

        SwipeMenuListView listView = (SwipeMenuListView) solo.getView(R.id.listView);
        int[] location = new int[2];
        listView.getLocationInWindow(location);
        solo.drag(location[0] + 500, location[0], location[1], location[1], 10);

        solo.clickOnMenuItem("deleteItem");
        //solo.clickOnImageButton(2);

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
