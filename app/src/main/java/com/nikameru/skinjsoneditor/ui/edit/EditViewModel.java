package com.nikameru.skinjsoneditor.ui.edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EditViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Nothing here...");
    }

    public LiveData<String> getText() {
        return mText;
    }
}