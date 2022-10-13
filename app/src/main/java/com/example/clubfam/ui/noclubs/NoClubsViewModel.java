package com.example.clubfam.ui.noclubs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoClubsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public NoClubsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is noclubs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
