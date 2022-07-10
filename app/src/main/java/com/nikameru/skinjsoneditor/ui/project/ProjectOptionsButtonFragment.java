package com.nikameru.skinjsoneditor.ui.project;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.nikameru.skinjsoneditor.R;
import com.nikameru.skinjsoneditor.databinding.FragmentProjectOptionsButtonBinding;

public class ProjectOptionsButtonFragment extends PreferenceFragmentCompat {

    private FragmentProjectOptionsButtonBinding binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.options_button_preferences, rootKey);
    }

    public static ProjectOptionsButtonFragment newInstance() {
        return new ProjectOptionsButtonFragment();
    }
}
