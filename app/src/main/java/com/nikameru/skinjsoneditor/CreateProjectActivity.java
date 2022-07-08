package com.nikameru.skinjsoneditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.GsonBuilder;
import com.nikameru.skinjsoneditor.ui.project.ProjectFragment;
import com.nikameru.skinjsoneditor.ui.project.SkinProperties;

import java.util.Locale;
import java.util.Objects;
import com.google.gson.Gson;

public class CreateProjectActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    protected String getConvertedHexFromPreference(@NonNull SharedPreferences preferences, String preferenceKey) {
        String preferenceColor = Integer.toHexString(preferences.getInt(preferenceKey, 0));
        Log.i("converted hex", preferenceColor);

        return "#" + preferenceColor.substring(2).toUpperCase();
    }

    protected String getSkinPropertiesJSON(@NonNull SharedPreferences preferences) {
        SkinProperties skinProperties = new SkinProperties();

        skinProperties.setMenuItemDefaultColor(
                getConvertedHexFromPreference(preferences, "menuItemDefaultColorPreference")
        );

        skinProperties.setMenuItemDefaultTextColor(
                getConvertedHexFromPreference(preferences, "menuItemDefaultTextColorPreference")
        );

        skinProperties.setMenuItemSelectedTextColor(
                getConvertedHexFromPreference(preferences, "menuItemSelectedTextColorPreference")
        );

        skinProperties.setMenuItemVersionsDefaultColor(
                getConvertedHexFromPreference(preferences, "menuItemVersionsDefaultColorPreference")
        );

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.toJson(skinProperties);
    }

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
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.project_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();

        if (menuItemId == R.id.action_save_project) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String configuredSkinProperties = getSkinPropertiesJSON(sharedPreferences);

            Log.i("json", configuredSkinProperties);

            return true;
        } else if (menuItemId == R.id.action_project_save_as) {
            // TODO: save as

            return true;
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
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