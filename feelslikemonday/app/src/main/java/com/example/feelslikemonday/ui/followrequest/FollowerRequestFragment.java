package com.example.feelslikemonday.ui.followrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.feelslikemonday.R;
import com.example.feelslikemonday.ui.friends.FriendsViewModel;

public class FollowerRequestFragment extends Fragment {


    private FollowerRequestViewModel followerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        followerViewModel = ViewModelProviders.of(this).get(FollowerRequestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_follower_request, container, false);
        followerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //do stuff here
            }
        });
        return root;
    }
}


