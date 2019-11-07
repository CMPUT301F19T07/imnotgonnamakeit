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

/*
*This is a class  responsible for making requests to firestore, and returning User Requests to the activities/fragments.
*/
public class FollowRequestDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final FollowRequestDAO instance = new FollowRequestDAO();

    private final String COLLECTION_NAME = "followRequests";

    private FollowRequestDAO(){}

    public static FollowRequestDAO getInstance() {
        return instance;
    }
    /**
     * This create a follow request to reply the user permission
     * @param username
     * This is a candidate username who reply the permission
     * @param followRequest
     *  This is a candidate request
     * @param onSuccess
     * This is value that determines weather the request is sent successfully
     */
    public void createOrUpdate(String username, FollowRequest followRequest, final VoidCallback onSuccess){
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
     * This delete a follow request to reply the user permission
     * @param username
     * This is a candidate username who delete the request
     * @param onSuccess
     * This is value that determines weather the request is deleted successfully
     */
    public void delete(String username, final VoidCallback onSuccess){
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
     * Queries for a FollowPermission by the user's username. A callback method will be called on success
     */
    public void get(String username, final FollowRequestCallback onSuccess){
        //Generally speaking, do not pass null values in. This is an exception, since we're overloading(?).
        get(username,onSuccess,null);
    }

    /**
     * This returns a follow request to reply the user permission
     * @return
     * Return the a request
     */
    public void get(String username, final FollowRequestCallback onSuccess, final VoidCallback onFail){
        db.collection(COLLECTION_NAME)
                .document(username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if(doc == null || !doc.exists()){
                                Log.d(TAG, "No documents found");
                                if (onFail != null){
                                    onFail.onCallback();
                                }
                                return;
                            }
                            // Assumes only one document will be returned
                            onSuccess.onCallback(doc.toObject(FollowRequest.class));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            if (onFail != null){
                                onFail.onCallback();
                            }
                        }
                    }
                });
    }
}
