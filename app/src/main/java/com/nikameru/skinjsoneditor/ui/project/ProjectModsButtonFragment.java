package com.nikameru.skinjsoneditor.ui.project;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.nikameru.skinjsoneditor.R;
import com.nikameru.skinjsoneditor.databinding.FragmentProjectModsButtonBinding;

public class ProjectModsButtonFragment extends PreferenceFragmentCompat {

    private FragmentProjectModsButtonBinding binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.mods_button_preferences, rootKey);
    }

    public static ProjectModsButtonFragment newInstance() {
        return new ProjectModsButtonFragment();
    }
}
