package com.example.feelslikemonday.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
/*This class is responsible for preparing and managing the data for HomeFragment*/
public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    /**
     * This a class that keeps track of home fragment
     */
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }
    /**
     * This returns a LiveData
     * @return
     *      return a LiveData initialized with String
     */
    public LiveData<String> getText() {
        return mText;
    }
}