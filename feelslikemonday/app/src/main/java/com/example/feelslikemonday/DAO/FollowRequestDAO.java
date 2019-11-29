package com.example.feelslikemonday.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.feelslikemonday.model.FollowRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * This is a class  responsible for making requests to firestore, and performing CRUD operations
 * on FollowRequests from other objects/activities. Uses the singleton pattern. Uses the User's
 * username as the 'primary key'
 */
public class FollowRequestDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final FollowRequestDAO instance = new FollowRequestDAO();

    //Name of the data "table" (collection)
    private final String COLLECTION_NAME = "followRequests";

    private FollowRequestDAO() {
    }

    /**
     * This returns a instance of follow request dao. This instance can be used by other objects to
     * perform CRUD operations on the follow request.
     *
     * @return Return the a instance of follow request dao
     */
    public static FollowRequestDAO getInstance() {
        return instance;
    }

    /**
     * This creates or updates a FollowRequest based on the username (pk) passed in.
     * Creates a new FollowRequest if the pk wasn't found, updates the FollowRequestObject otherwise.
     *
     * @param username      This is a candidate username who reply the permission (primary key)
     * @param followRequest This is the object we wish to create/update and assign to the pk
     * @param onSuccess     This is function invoked when the request is created or updated successfully
     */
    public void createOrUpdate(String username, FollowRequest followRequest, final VoidCallback onSuccess) {
        db.collection(COLLECTION_NAME).document(username)
                .set(followRequest)
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
     * This deletes the document with the given username provided.
     *
     * @param username  This is a candidate username that we use to query the document
     * @param onSuccess This is the function invoked when the request is deleted successfully
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
     * This queries for a FollowPermission object using the User's username. A callback method will be called on success
     *
     * @param username  This is a candidate username
     * @param onSuccess This is the function invoked when the request is obtained successfully
     */
    public void get(String username, final FollowRequestCallback onSuccess) {
        //Generally speaking, do not pass null values in. This is an exception, since we're overloading(?).
        get(username, onSuccess, null);
    }

    /**
     * This queries for a FollowPermission object using the User's username. A callback method will be called on success.
     * This method contains an onFail callback method in the case that the firebase request fails.
     *
     * @param username  This is a candidate username
     * @param onSuccess This is the function invoked when the request is obtained successfully
     * @param onFail    This is the function invoked when the request fails
     */
    public void get(String username, final FollowRequestCallback onSuccess, final VoidCallback onFail) {
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
                            onSuccess.onCallback(doc.toObject(FollowRequest.class));
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
