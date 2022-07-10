package com.nikameru.skinjsoneditor.ui.project;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.nikameru.skinjsoneditor.R;
import com.nikameru.skinjsoneditor.databinding.FragmentProjectBackButtonBinding;

public class ProjectBackButtonFragment extends PreferenceFragmentCompat {

    private FragmentProjectBackButtonBinding binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.back_button_preferences, rootKey);
    }

    public static ProjectBackButtonFragment newInstance() {
        return new ProjectBackButtonFragment();
    }
}
