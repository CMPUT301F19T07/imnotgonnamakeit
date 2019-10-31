package com.example.feelslikemonday.ui.followrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.feelslikemonday.R;
import com.example.feelslikemonday.ui.followrequest.ArraryAdapter.RequestList;
import com.example.feelslikemonday.model.User;

import java.util.ArrayList;

public class FollowerRequestFragment extends Fragment {
    private ListView userList;
    private ArrayAdapter<User> userAdapter;
    private ArrayList<User> userDataList;
    private RequestList requestList;

    private FollowerRequestViewModel followerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        followerViewModel = ViewModelProviders.of(this).get(FollowerRequestViewModel.class);

        View root = inflater.inflate(R.layout.fragment_follower_request, container, false);

        userList = root.findViewById(R.id.request_username_list);
        userDataList = new ArrayList<>();
        //test
        String username = "your lovely follower";
        String pass = "test";
        String email = "test";
        User userA = new User(username,pass);

        userDataList.add(userA);
        userAdapter = new RequestList(getActivity(), userDataList);
        userList.setAdapter(userAdapter);
        followerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //do stuff here


            }
        });


        return root;
    }
}


