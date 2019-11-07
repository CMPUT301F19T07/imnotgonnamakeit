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

/*
*This is a class that acts as an intermediary for the app and Firestore. Used to query FollowPermissions
*/
public class FollowPermissionDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final FollowPermissionDAO instance = new FollowPermissionDAO();

    private final String COLLECTION_NAME = "followPermissions";

    private FollowPermissionDAO(){}

    public static FollowPermissionDAO getInstance() {
        return instance;
    }


    /**
     * This create a follow permission sent by a user
     * @param username
     * This is a candidate username who want to send the permission
     * @param followPermission
     *  This is a candidate permission
     * @param onSuccess
     * This is value that determines weather the permission is sent successfully
     */
    public void createOrUpdate(String username, FollowPermission followPermission, final VoidCallback onSuccess){
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
     * This delete a follow permission sent by a user
     * @param username
     * This is a candidate username who want to delete the permission
     * @param onSuccess
     * This is value that determines weather the permission is deleted successfully
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
    public void get(String username, final FollowPermissionCallback onSuccess){
        //Generally speaking, do not pass null values in. This is an exception, since we're overloading(?).
        get(username,onSuccess,null);
    }
    /**
     * This returns a permission sent by a user
     * @return
     * Return the a permission
     */
    public void get(String username, final FollowPermissionCallback onSuccess, final VoidCallback onFail){
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
                            onSuccess.onCallback(doc.toObject(FollowPermission.class));
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
