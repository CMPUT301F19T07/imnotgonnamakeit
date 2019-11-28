package com.example.feelslikemonday.DAO;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.feelslikemonday.model.User;

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

    /**
     * Create a User object
     * @throws InterruptedException
     */
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

    /**
     * Retrive an existing User object
     * @throws InterruptedException
     */
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

    /**
     * Create a new user, change it's password, and check the db if the password has been updated.
     * @throws InterruptedException
     */
    @Test
    public void updateUserObject() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        userDAO.createOrUpdate(new User("uTEST-bill","password"),new VoidCallback(){
            @Override
            public void onCallback() {
                userDAO.createOrUpdate(new User("uTEST-bill", "newPassword"), new VoidCallback() {
                    @Override
                    public void onCallback() {
                        userDAO.get("uTEST-bill", new UserCallback() {
                            @Override
                            public void onCallback(User user) {
                                assertEquals(user.getPassword(),"newPassword");
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
     * Create a new user, delete the user, and check if the user exists. Test will pass if not user is found
     * @throws InterruptedException
     */
    @Test
    public void deleteUserObject() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        userDAO.createOrUpdate(new User("uTEST-will", "password"), new VoidCallback() {
            @Override
            public void onCallback() {
                userDAO.delete(new User("uTEST-will","password"), new VoidCallback() {
                    @Override
                    public void onCallback() {
                        userDAO.get("uTEST-will", new UserCallback() {
                            @Override
                            public void onCallback(User user) {
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

    /**
     * Create a user, check if it exists, delete the user, check if it exists again
     * @throws InterruptedException
     */
    @Test
    public void checkIfUserExists() throws InterruptedException{
        final CountDownLatch signal = new CountDownLatch(1);
        userDAO.createOrUpdate(new User("uTEST-nill", "password"), new VoidCallback() {
            @Override
            public void onCallback() {
                userDAO.checkIfExists("uTEST-nill", new BooleanCallback() {
                    @Override
                    public void onCallback(Boolean bool) {
                        assertEquals(bool, Boolean.TRUE);
                        userDAO.delete(new User("uTEST-nill", "password"), new VoidCallback() {
                            @Override
                            public void onCallback() {
                                userDAO.checkIfExists("uTEST-nill", new BooleanCallback() {
                                    @Override
                                    public void onCallback(Boolean bool) {
                                        assertEquals(bool, Boolean.FALSE);
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
                }, new VoidCallback() {
                    @Override
                    public void onCallback() {
                        fail();
                        signal.countDown();
                    }
                });
            }
        });
        signal.await();
    }
}