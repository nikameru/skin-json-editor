package com.nikameru.skinjsoneditor.ui.project;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.PreferenceFragmentCompat;

import com.nikameru.skinjsoneditor.R;
import com.nikameru.skinjsoneditor.databinding.FragmentProjectBinding;

public class ProjectFragment extends PreferenceFragmentCompat {

    private FragmentProjectBinding binding;

    /*@Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProjectViewModel projectViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ProjectViewModel.class);

        binding = FragmentProjectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProject;
        projectViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }*/

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