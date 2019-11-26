package com.example.feelslikemonday.ui.friends;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.feelslikemonday.DAO.FollowRequestCallback;
import com.example.feelslikemonday.DAO.FollowRequestDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowRequest;
import com.example.feelslikemonday.ui.followrequest.ArraryAdapter.RequestList;
import com.example.feelslikemonday.ui.followrequest.FollowerRequestViewModel;
import com.example.feelslikemonday.ui.login.SignupActivity;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsFragment extends Fragment {
    private MyFriendsViewModel friendsViewModel;
    private ListView userList;
    private ArrayAdapter<String> userAdapter;
    private List<String> requesterUsernames;
    private static FollowRequestDAO DAO;
    private SharedPreferences pref;
    private String myUserID;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        friendsViewModel = ViewModelProviders.of(this).get(MyFriendsViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_my_friends, container, false);
        userList = (ListView) root.findViewById(R.id.friends_username_list);
        pref = getActivity().getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);

        friendsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //do stuff here
            }
        });

        return root;
    }
}
