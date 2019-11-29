package com.example.feelslikemonday.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.feelslikemonday.model.FollowPermission;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * This is a class that acts as an intermediary for the app and Firestore. Used to query FollowPermissions.
 * Uses the singleton pattern. Uses a User's username as the primary key.
 */
public class FollowPermissionDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final FollowPermissionDAO instance = new FollowPermissionDAO();

    private final String COLLECTION_NAME = "followPermissions";


    public FollowPermissionDAO() {
    }

    /**
     * This returns a instance of the DAO. This instance can be used to perform CRUD operations
     * on FollowPermission objects.
     * @return return an instance of follow permission dao
     */
    public static FollowPermissionDAO getInstance() {
        return instance;
    }

    /**
     * This creates or updates a follow permission sent by a user. Uses the username as a primary key.
     * Creates a new object if the primary key is not found. Updates the existing object otherwise
     * Invokes a callback method for objects to use once done successfully.
     *
     * @param username         This is a username of the user who's follow permission we want to update
     * @param followPermission This is the new FollowPermission object we want to create/update
     * @param onSuccess        This is the void function invoked when the permission is created or updated successfully
     */
    public void createOrUpdate(String username, FollowPermission followPermission, final VoidCallback onSuccess) {
        db.collection(COLLECTION_NAME).document(username)
                .set(followPermission)
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
     * This deletes a follow permission. Uses the username as a primary key.
     * Invokes a void callback method on success
     *
     * @param username  This is a candidate username who want to delete the permission
     * @param onSuccess This is the function returned when the permission is deleted successfully
     */
    public void delete(String username, final VoidCallback onSuccess) {
        db.collection(COLLECTION_NAME).document(username)
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
     * This queries for a FollowPermission by the user's username. A callback method will be called on success.
     *
     * @param username  This is a candidate username
     * @param onSuccess This is the function returned when the permission is obtained successfully
     */
    public void get(String username, final FollowPermissionCallback onSuccess) {
        //Generally speaking, do not pass null values in. This is an exception, since we're overloading(?).
        get(username, onSuccess, null);
    }

    /**
     * This queries for a FollowPermission by the user's username. A callback method will be called on success.
     *
     * @param username  This is a candidate username
     * @param onSuccess This is the function returned when the permission is obtained successfully
     * @param onFail    This is the function returned when firebase fails to perform the operation
     */
    public void get(String username, final FollowPermissionCallback onSuccess, final VoidCallback onFail) {
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
                            onSuccess.onCallback(doc.toObject(FollowPermission.class));
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
