package com.example.feelslikemonday.DAO;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.feelslikemonday.model.User;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

/**
 * Countdown latch idea: https://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
 */
@RunWith(AndroidJUnit4.class)
public class UserDAOTest {
    private static UserDAO userDAO;

    @BeforeClass
    static public void start() {
        userDAO = UserDAO.getInstance();
    }

    @Test
    public void createUserObject() throws InterruptedException{
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        assertEquals("com.example.feelslikemonday", appContext.getPackageName());
        final CountDownLatch signal = new CountDownLatch(1);

        userDAO.createOrUpdate(new User("uTEST-sill","password"),new VoidCallback(){
            @Override
            public void onCallback() {
                signal.countDown();
            }
        });
        signal.await();
    }
    @Test
    public void getUserObject() throws InterruptedException {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        assertEquals("com.example.feelslikemonday", appContext.getPackageName());
        final CountDownLatch signal = new CountDownLatch(1);
        userDAO.get("uTEST-sill", new UserCallback() {
            @Override
            public void onCallback(User user) {
                Log.d(TAG, user.getUsername() + " " + user.getPassword());
                signal.countDown();
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
                Log.d(TAG,"An error has occurred with the DAO");
                signal.countDown();
            }
        });

        signal.await();
    }
}