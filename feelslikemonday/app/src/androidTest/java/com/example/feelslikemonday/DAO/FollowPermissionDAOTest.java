package com.example.feelslikemonday.DAO;

import android.util.Log;

import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.model.FollowRequest;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FollowPermissionDAOTest {
    private static FollowPermissionDAO DAO = FollowPermissionDAO.getInstance();

    @Test
    public void createObject() throws InterruptedException{
        /* Signal uses a lock to prevent the test from finishing until the test is done.
         * Don't use this technique to force things to be synchronous.
         */
        final CountDownLatch signal = new CountDownLatch(1);

        DAO.createOrUpdate("uTEST-sill",new FollowPermission("uTEST-sill"),new VoidCallback(){
            @Override
            public void onCallback() {
                signal.countDown();
            }
        });
        signal.await();
    }

    @Test
    public void getObject() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        DAO.get("uTEST-sill", new FollowPermissionCallback() {
            @Override
            public void onCallback(FollowPermission followPermission) {
                assertEquals(followPermission.getFollowerUsername(),"uTEST-sill");
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
