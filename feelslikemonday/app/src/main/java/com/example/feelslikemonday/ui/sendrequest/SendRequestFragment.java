package com.example.feelslikemonday.ui.sendrequest;

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
import com.example.feelslikemonday.ui.followrequest.FollowerRequestViewModel;

public class SendRequestFragment extends Fragment {

    private SendRequestViewModel sendRequestViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendRequestViewModel = ViewModelProviders.of(this).get(SendRequestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send_request, container, false);
        sendRequestViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //do stuff here
            }
        });
        return root;
    }
}
