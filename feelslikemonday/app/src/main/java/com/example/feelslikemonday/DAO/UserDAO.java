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

 * To get the DAO, simply call UserDao.getInstance()

 * Uses the username as the primary key

 * Uses documentation snippets from https://firebase.google.com/docs/firestore/manage-data/delete-data

 */

/*
*This class acts as an intermediary for the app and firestore. Used to query Users
*/
public class UserDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final UserDAO instance = new UserDAO();
    private final String COLLECTION_NAME = "users";

    public UserDAO(){}
    public static UserDAO getInstance() {
        return instance;
    }
    /**
     * This create a user
     * @param user
     * This is a candidate username that needs to be created
     * @param onSuccess
     * This is value that determines weather the user is created successfully
     */
    public void createOrUpdate(User user , final VoidCallback onSuccess){

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
     * This delete a follow request to reply the user permission
     * @param user
     * This is the user that needs to be deleted
     * @param onSuccess
     * This is value that determines weather the user is deleted successfully
     */

    public void delete(User user, final VoidCallback onSuccess){
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
     * Queries for a user by the user's username. A callback method will be called on success
     */
    public void get(String username, final UserCallback onSuccess){
        //Generally speaking, do not pass null values in. This is an exception, since we're overloading(?).
        get(username,onSuccess,null);
    }
    /**
     * This returns a user
     * @return
     * Return the a user
     */

    public void get(String username, final UserCallback onSuccess, final VoidCallback onFail){
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
                             User curent = doc.toObject(User.class);
                            onSuccess.onCallback(doc.toObject(User.class));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            if (onFail != null){
                                onFail.onCallback();
                            }
                        }
                    }
                });
    }

    public void checkIfExists(String username, final BooleanCallback onSuccess, final VoidCallback onFail){
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
                            if (onFail != null){
                                onFail.onCallback();
                            }
                        }
                    }
                });
    }
}