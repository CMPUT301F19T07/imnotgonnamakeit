package com.example.feelslikemonday.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.feelslikemonday.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;

import static androidx.constraintlayout.widget.Constraints.TAG;
/**
 * To get the DAO, simply call UserDao.getInstance()
 * Uses the user email as the primary key
 * Uses documentation snippets from https://firebase.google.com/docs/firestore/manage-data/delete-data
 */

public class UserDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final UserDAO instance = new UserDAO();

    private UserDAO(){}

    public static UserDAO getInstance() {
        return instance;
    }

    public void createOrUpdate(User user , final VoidCallback onSuccess){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("object",user);

        db.collection("users").document(user.getUsername())
                .set(userMap)
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

    public void delete(User user, final VoidCallback onSuccess){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(user.getUsername(),user);

        db.collection("users").document(user.getUsername())
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
        db.collection("users")
                .whereEqualTo(username, true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Assumes only one document will be returned
                            onSuccess.onCallback((User)task.getResult().getDocuments().get(0).getData());
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
