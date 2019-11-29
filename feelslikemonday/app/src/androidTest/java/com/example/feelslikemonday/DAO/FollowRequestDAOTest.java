package com.example.feelslikemonday.DAO;

import android.util.Log;

import com.example.feelslikemonday.model.FollowRequest;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for FollowRequestDAO
 */
public class FollowRequestDAOTest {
    private static FollowRequestDAO DAO = FollowRequestDAO.getInstance();

    /**
     * Create an instance of a FollowRequest object in the DB.
     *
     * @throws InterruptedException
     */
    @Test
    public void createObject() throws InterruptedException {
        /* Signal uses a lock to prevent the test from finishing until the test is done.
         * Don't use this technique to force things to be synchronous.
         */
        final CountDownLatch signal = new CountDownLatch(1);

        DAO.createOrUpdate("uTEST-sill", new FollowRequest("uTEST-sill"), new VoidCallback() {
            @Override
            public void onCallback() {
                signal.countDown();
            }
        });

        signal.await();
    }

    /**
     * Get an instance of a FollowRequest object from the DB. Passes if a user was retrieved
     *
     * @throws InterruptedException
     */
    @Test
    public void getObject() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        DAO.get("uTEST-sill", new FollowRequestCallback() {
            @Override
            public void onCallback(FollowRequest followRequest) {
                assertEquals(followRequest.getRecipientUsername(), "uTEST-sill");
                signal.countDown();
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
                Log.d(TAG, "An error has occurred with the DAO");
                fail();
                signal.countDown();
            }
        });

        signal.await();
    }

    /**
     * Create a new object, update it's requester usernames, and check if those changes are
     * reflected in the DB.
     *
     * @throws InterruptedException
     */
    @Test
    public void updateObject() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        final FollowRequest followRequest = new FollowRequest("uTEST-bill");

        DAO.createOrUpdate("uTEST-bill", followRequest, new VoidCallback() {
            @Override
            public void onCallback() {
                //add a new follower
                followRequest.getRequesterUsernames().add("uTEST-bill-0");
                DAO.createOrUpdate("uTEST-bill", followRequest, new VoidCallback() {
                    @Override
                    public void onCallback() {
                        DAO.get("uTEST-bill", new FollowRequestCallback() {
                            @Override
                            public void onCallback(FollowRequest followPermission) {
                                assertEquals(followPermission.getRecipientUsername(), "uTEST-bill");
                                assertEquals(followPermission.getRequesterUsernames().size(), 1);
                                assertEquals(followPermission.getRequesterUsernames().get(0), "uTEST-bill-0");
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

    /**
     * Delete an object, and check if it exists in the DB. passes if no object was found.
     *
     * @throws InterruptedException
     */
    @Test
    public void deleteObject() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        DAO.createOrUpdate("uTEST-will", new FollowRequest("uTEST-will"), new VoidCallback() {
            @Override
            public void onCallback() {
                DAO.delete("uTEST-will", new VoidCallback() {
                    @Override
                    public void onCallback() {
                        DAO.get("uTEST-will", new FollowRequestCallback() {
                            @Override
                            public void onCallback(FollowRequest user) {
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
