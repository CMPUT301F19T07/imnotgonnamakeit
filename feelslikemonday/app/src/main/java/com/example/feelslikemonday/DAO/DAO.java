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

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DAO<T,U> {
//    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private String COLLECTION_NAME = "users";
//
//    public void createOrUpdate(String username, T inputObject, final VoidCallback onSuccess){
//        db.collection(COLLECTION_NAME).document(username)
//                .set(inputObject)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully written!");
//                        onSuccess.onCallback();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error writing document", e);
//                    }
//                });
//    }
//
//    public void delete(String username,T inputObject, final VoidCallback onSuccess){
//        db.collection(COLLECTION_NAME).document(username)
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                        onSuccess.onCallback();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error writing document", e);
//                    }
//                });
//    }
//
//    /**
//     * Queries for an object by the user's username. A callback method will be called on success
//     */
//    public void get(String username, final U onSuccess){
//        //Generally speaking, do not pass null values in. This is an exception, since we're overloading(?).
//        get(username,onSuccess,null);
//    }
//    public void get(String username, final U onSuccess, final VoidCallback onFail){
//        db.collection(COLLECTION_NAME)
//                .document(username)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot doc = task.getResult();
//                            if(doc == null || !doc.exists()){
//                                Log.d(TAG, "No documents found");
//                                if (onFail != null){
//                                    onFail.onCallback();
//                                }
//                                return;
//                            }
//                            // Assumes only one document will be returned
//                            onSuccess.onCallback(doc.toObject(User.class));
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                            if (onFail != null){
//                                onFail.onCallback();
//                            }
//                        }
//                    }
//                });
//    }
}
