package com.example.clubfam.ui.tinder;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TinderViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TinderViewModel() {
        Log.d("IN TINDER VIEW MODEL", "String");
        mText = new MutableLiveData<>();
        mText.setValue("This is my tinder fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
