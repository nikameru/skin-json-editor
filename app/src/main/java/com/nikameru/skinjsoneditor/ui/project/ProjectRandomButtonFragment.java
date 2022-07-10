package com.nikameru.skinjsoneditor.ui.project;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.nikameru.skinjsoneditor.R;
import com.nikameru.skinjsoneditor.databinding.FragmentProjectRandomButtonBinding;

public class ProjectRandomButtonFragment extends PreferenceFragmentCompat {

    private FragmentProjectRandomButtonBinding binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.random_button_preferences, rootKey);
    }

    public static ProjectRandomButtonFragment newInstance() {
        return new ProjectRandomButtonFragment();
    }
}
