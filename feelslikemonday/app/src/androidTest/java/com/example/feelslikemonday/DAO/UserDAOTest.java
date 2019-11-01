package com.example.feelslikemonday.DAO;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.feelslikemonday.model.User;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Tests for the User DAO.
 * Countdown latch idea: https://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
 */
@RunWith(AndroidJUnit4.class)
public class UserDAOTest {
    private static UserDAO userDAO = UserDAO.getInstance();

    @Test
    public void createUserObject() throws InterruptedException{
        /* Signal uses a lock to prevent the test from finishing until the test is done.
         * Don't use this technique to force things to be synchronous.
         */
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
        final CountDownLatch signal = new CountDownLatch(1);
        userDAO.get("uTEST-sill", new UserCallback() {
            @Override
            public void onCallback(User user) {
                Log.d(TAG, user.getUsername() + " " + user.getPassword());
                assertEquals(user.getUsername(),"uTEST-sill");
                signal.countDown();
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
                Log.d(TAG,"An error has occurred with the DAO");
                fail();
                signal.countDown();
            }
        });
        signal.await();
    }
}