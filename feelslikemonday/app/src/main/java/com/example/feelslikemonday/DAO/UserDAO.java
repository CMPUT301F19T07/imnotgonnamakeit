package com.example.feelslikemonday.DAO;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.feelslikemonday.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * To get the DAO, call UserDao.getInstance() to get an instance of the DAO.
 * The instance can be used to call queries to perform CRUD operations on Users in Firestore.
 * Uses the username as the primary key
 * Uses documentation snippets from https://firebase.google.com/docs/firestore/manage-data/delete-data
 * This class acts as an intermediary for the app and firestore. Used to query Users
 */

public class UserDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final UserDAO instance = new UserDAO();
    private final String COLLECTION_NAME = "users";

    public UserDAO() {
    }

    /**
     * This returns a instance of the UserDAO. This instance should be called on to
     * retrieve items and objects
     * @return Return a instance of user dao
     */
    public static UserDAO getInstance() {
        return instance;
    }

    /**
     * This creates or updates a user. Searches for users given the user's username.
     * Creates if username is not found, updated otherwise
     *
     * @param user      This is a candidate user that needs to be created or updated. Has information searched on by her/his username
     * @param onSuccess This is the function invoked when the user is created or updated successfully
     */
    public void createOrUpdate(User user, final VoidCallback onSuccess) {

        db.collection(COLLECTION_NAME).document(user.getUsername())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        onSuccess.onCallback();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    /**
     * This deletes a user if needed, and searches using the user's username.
     * Invokes a void callback method on success.
     *
     * @param user      This is the user that needs to be deleted
     * @param onSuccess This is the function invoked when returned when the user is deleted successfully
     */
    public void delete(User user, final VoidCallback onSuccess) {
        db.collection(COLLECTION_NAME).document(user.getUsername())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        onSuccess.onCallback();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    /**
     * This queries for a user by the user's username. A callback method will be called on success.
     * This callback contains a user that can be worked with
     *
     * @param username  This is the name of user that we want to get data from
     * @param onSuccess This is the function invoked when the user's data is obtained successfully
     */
    public void get(String username, final UserCallback onSuccess) {
        //Generally speaking, do not pass null values in. This is an exception, since we're overloading(?).
        get(username, onSuccess, null);
    }

    /**
     * This returns a user object when given a username of the user. Throws an error if no user is found.
     * Performs a user callback if the user is found.
     *
     * @param username  This is the name of user that we want to get data from
     * @param onSuccess This is the function invoked when the user's data is obtained successfully
     * @param onFail    This is the function that is called when an error occurs in firestore
     */
    public void get(String username, final UserCallback onSuccess, final VoidCallback onFail) {
        db.collection(COLLECTION_NAME)
                .document(username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc == null || !doc.exists()) {
                                Log.d(TAG, "No documents found");
                                if (onFail != null) {
                                    onFail.onCallback();
                                }
                                return;
                            }

                            // Assumes only one document will be returned
                            User current = doc.toObject(User.class);
                            onSuccess.onCallback(doc.toObject(User.class));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            if (onFail != null) {
                                onFail.onCallback();
                            }
                        }
                    }
                });
    }

    /**
     * This check if a user data exists in firebase. Returns a boolean on the conditional if users exist or not
     *
     * @param username  Name of the user that we want to check if they exist or not
     * @param onSuccess This is the callback method that returns under normal conditions. Has a boolean
     *                  on which logic can be operated on. True means the user exists, false means the
     *                  user does not exist.
     * @param onFail    This is the callback method invoked if an error occurs
     */
    public void checkIfExists(String username, final BooleanCallback onSuccess, final VoidCallback onFail) {
        db.collection(COLLECTION_NAME)
                .document(username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            //Return if the queried document is non-null and exists
                            onSuccess.onCallback(doc != null && doc.exists());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            if (onFail != null) {
                                onFail.onCallback();
                            }
                        }
                    }
                });
    }
}