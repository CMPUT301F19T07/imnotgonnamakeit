package com.example.feelslikemonday.Home;


import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.PointF;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.example.feelslikemonday.ui.moods.addNewMoodActivity;
import com.robotium.solo.Solo;
import com.example.feelslikemonday.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;

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
    /*
    @Test
    public void start()throws Exception{
        Activity activity = rule.getActivity();
    }

     */

    @Test
    public  void homePageSwitchTest(){
        solo.assertCurrentActivity("Wrong Activity", LoginMainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "mockUser");
        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "12345");
        solo.clickOnButton("LOGIN");
        solo.clickOnActionBarItem(R.id.action_settings);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //solo.enterText((EditText) solo.getView(R.id.editText8), "didnt drink coffee");
        //solo.clickOnButton("cancel");
        //solo.clickOnView(solo.getView(R.id.mood_spinner));
        //solo.pressSpinnerItem(0, 0);
        //solo.getCurrentSpinners().get(0).performClick();
        //solo.clickOnView(solo.getView(R.id.mood_spinner));
        /*
        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
        solo.scrollToTop(); // I put this in here so that it always keeps the list at start
        solo.clickOnView(solo.getView(TextView.class, 0)); //anger
        solo.sleep(2000);
         */

    }

    @Test
    public  void deleteTest(){
        solo.enterText((EditText) solo.getView(R.id.loginUsernameEdit), "mockUser");
        solo.enterText((EditText) solo.getView(R.id.loginPasswordEdit), "12345");
        solo.clickOnButton("LOGIN");
/*
        solo.swipe();

        final RecyclerView employeesList = (RecyclerView) solo.getView(R.id.employee_list);
        final int numEmployees = employeesList.getAdapter().getItemCount();

        // Swipe from left to right in the first item
        solo.swipe(new PointF(10, 200), new PointF(10, 200),
                new PointF(400, 200), new PointF(400, 200));
        solo.clickOnText("TaxSystem");
        
 */

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

