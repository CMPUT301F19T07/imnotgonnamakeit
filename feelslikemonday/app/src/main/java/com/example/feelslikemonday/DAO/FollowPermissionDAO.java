package com.example.feelslikemonday.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FollowPermissionDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final FollowPermissionDAO instance = new FollowPermissionDAO();

    private final String COLLECTION_NAME = "followPermissions";

    private FollowPermissionDAO(){}

    public static FollowPermissionDAO getInstance() {
        return instance;
    }

//    public void createOrUpdate(FollowPermission followPermission , final VoidCallback onSuccess){
//        Map<String, Object> userMap = new HashMap<>();
//        userMap.put("object",followPermission);
//
//        db.collection(COLLECTION_NAME).document(user.getUsername())
//                .set(userMap)
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
//    public void delete(User user, final VoidCallback onSuccess){
//        Map<String, Object> userMap = new HashMap<>();
//        userMap.put(user.getUsername(),user);
//
//        db.collection(COLLECTION_NAME).document(user.getUsername())
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
//    public void get(String username, final UserCallback onSuccess){
//        db.collection(COLLECTION_NAME)
//                .whereEqualTo(username, true)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            // Assumes only one document will be returned
//                            onSuccess.onCallback((User)task.getResult().getDocuments().get(0).getData());
////                                Log.d(TAG, document.getId() + " => " + document.getData());
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }
}
