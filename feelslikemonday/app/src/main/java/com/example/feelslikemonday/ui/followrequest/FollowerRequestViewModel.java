package com.example.feelslikemonday.ui.followrequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/*This class is responsible for preparing and managing the data for FollowerRequest*/
public class FollowerRequestViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    /**
     * This a class that keeps track of Follower Request fragment
     */
    public FollowerRequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is follower request fragment");
    }

    /**
     * This returns a LiveData.
     * @return
     *      return a LiveData initialized with String
     */
    public LiveData<String> getText() {
        return mText;
    }
}
