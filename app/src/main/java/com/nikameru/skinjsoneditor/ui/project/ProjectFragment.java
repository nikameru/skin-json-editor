package com.nikameru.skinjsoneditor.ui.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nikameru.skinjsoneditor.databinding.FragmentProjectBinding;

public class ProjectFragment extends Fragment {

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    private FragmentProjectBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProjectViewModel projectViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ProjectViewModel.class);

        binding = FragmentProjectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProject;
        projectViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}