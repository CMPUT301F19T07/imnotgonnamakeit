package com.example.feelslikemonday.ui.followrequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FollowerRequestViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FollowerRequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is follower request fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
