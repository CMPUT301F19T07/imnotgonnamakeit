package com.example.feelslikemonday.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.feelslikemonday.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

//To get the DAO, simply call UserDao.getInstance()
//Uses the user email as the primary key
//Uses documentation snippets from https://firebase.google.com/docs/firestore/manage-data/delete-data
public class UserDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final UserDAO instance = new UserDAO();

    private UserDAO(){}

    public static UserDAO getInstance() {
        return instance;
    }

    public void createOrUpdate(User user){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("object",user);

        db.collection("users").document(user.getEmail())
                .set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void delete(User user){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(user.getEmail(),user);

        db.collection("users").document(user.getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}
