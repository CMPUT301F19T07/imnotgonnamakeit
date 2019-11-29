package com.example.feelslikemonday.ui.following;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FollowingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    /**
     * This a class that keeps track of following fragment
     */
    public FollowingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is following fragment");
    }

    /**
     * This returns a LiveData
     *
     * @return return a LiveData initialized with String
     */
    public LiveData<String> getText() {
        return mText;
    }
}