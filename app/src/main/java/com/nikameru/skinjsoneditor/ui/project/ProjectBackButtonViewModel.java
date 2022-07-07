package com.nikameru.skinjsoneditor.ui.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectBackButtonViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProjectBackButtonViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is project fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}