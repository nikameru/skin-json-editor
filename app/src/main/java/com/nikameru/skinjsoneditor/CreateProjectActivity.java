package com.nikameru.skinjsoneditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.os.Bundle;
import android.view.Menu;

import com.nikameru.skinjsoneditor.ui.project.ProjectFragment;
import com.nikameru.skinjsoneditor.ui.project.SkinProperties;

import java.util.Objects;

public class CreateProjectActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_project);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.createProject, ProjectFragment.newInstance())
                    .commitNow();
        }

        Toolbar projectToolbar = findViewById(R.id.projectToolbar);
        setSupportActionBar(projectToolbar);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        SkinProperties skinProperties = new SkinProperties();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.project_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat caller, @NonNull Preference pref) {
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                Objects.requireNonNull(pref.getFragment())
        );

        fragment.setArguments(args);
        fragment.setTargetFragment(caller, 0);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.createProject, fragment)
                .addToBackStack(null)
                .commit();

        return true;
    }
}