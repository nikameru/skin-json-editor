package com.nikameru.skinjsoneditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

import com.nikameru.skinjsoneditor.ui.project.ProjectFragment;

public class CreateProjectActivity extends AppCompatActivity {

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

        Toolbar projectToolbar = (Toolbar) findViewById(R.id.projectToolbar);
        setSupportActionBar(projectToolbar);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.project_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}