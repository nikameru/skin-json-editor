package com.nikameru.skinjsoneditor.ui.project;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.nikameru.skinjsoneditor.R;
import com.nikameru.skinjsoneditor.databinding.FragmentProjectBinding;

public class ProjectFragment extends PreferenceFragmentCompat {

    private FragmentProjectBinding binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.skin_preferences, rootKey);
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }
}