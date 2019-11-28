package com.example.feelslikemonday.DAO;

import android.util.Log;

import com.example.feelslikemonday.model.FollowPermission;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
/**
 * Test class for FollowPermissionDAO
 */
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

    @Test
    public void updateObject() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        final FollowPermission followPermission = new FollowPermission("uTEST-bill");

        DAO.createOrUpdate("uTEST-bill",followPermission,new VoidCallback(){
            @Override
            public void onCallback() {
                //add a new follower
                followPermission.getFolloweeUsernames().add("uTEST-bill-0");
                DAO.createOrUpdate("uTEST-bill",followPermission, new VoidCallback() {
                    @Override
                    public void onCallback() {
                        DAO.get("uTEST-bill", new FollowPermissionCallback() {
                            @Override
                            public void onCallback(FollowPermission followPermission) {
                                assertEquals(followPermission.getFollowerUsername(),"uTEST-bill");
                                assertEquals(followPermission.getFolloweeUsernames().size(),1);
                                assertEquals(followPermission.getFolloweeUsernames().get(0),"uTEST-bill-0");
                                signal.countDown();
                            }
                        }, new VoidCallback() {
                            @Override
                            public void onCallback() {
                                fail();
                                signal.countDown();
                            }
                        });
                    }
                });
            }
        });
        signal.await();
    }

    @Test
    public void deleteObject() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        DAO.createOrUpdate("uTEST-will", new FollowPermission("uTEST-will"), new VoidCallback() {
            @Override
            public void onCallback() {
                DAO.delete("uTEST-will", new VoidCallback() {
                    @Override
                    public void onCallback() {
                        DAO.get("uTEST-will", new FollowPermissionCallback(){
                            @Override
                            public void onCallback(FollowPermission user) {
                                fail();
                                signal.countDown();
                            }
                        }, new VoidCallback() {
                            @Override
                            public void onCallback() {
                                //No user was found
                                signal.countDown();
                            }
                        });
                    }
                });
            }
        });
        signal.await();
    }
}
