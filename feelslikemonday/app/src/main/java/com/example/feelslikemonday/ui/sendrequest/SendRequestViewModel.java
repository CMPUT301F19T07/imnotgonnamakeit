package com.example.feelslikemonday.ui.sendrequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/*This class is responsible for preparing and managing the data for SendRequestFragment*/
public class SendRequestViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    /**
     * This a class that keeps track of send request fragment
     */
    public SendRequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send request fragment");
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


