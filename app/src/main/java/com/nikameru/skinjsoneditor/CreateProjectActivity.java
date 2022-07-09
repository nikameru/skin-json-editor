package com.nikameru.skinjsoneditor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.nikameru.skinjsoneditor.ui.project.ProjectFragment;
import com.nikameru.skinjsoneditor.ui.project.SkinProperties;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Objects;

public class CreateProjectActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private String getConvertedHexFromPreference(@NonNull SharedPreferences preferences, String preferenceKey) {
        String preferenceColor = Integer.toHexString(preferences.getInt(preferenceKey, 171717));
        Log.i("converted hex", preferenceColor);

        return "#" + preferenceColor.substring(2).toUpperCase();
    }

    private String getSkinPropertiesJSON(@NonNull SharedPreferences preferences) {

        // instances creation

        SkinProperties skinProperties = new SkinProperties();

        SkinProperties.Color color = new SkinProperties.Color();
        SkinProperties.ComboColor comboColor = new SkinProperties.ComboColor();
        SkinProperties.Layout layout = new SkinProperties.Layout();
        SkinProperties.Slider slider = new SkinProperties.Slider();
        SkinProperties.Utils utils = new SkinProperties.Utils();

        // configuring skinProperties

        // color category

        color.setMenuItemDefaultColor(
                getConvertedHexFromPreference(preferences, "menuItemDefaultColorPreference")
        );

        color.setMenuItemDefaultTextColor(
                getConvertedHexFromPreference(preferences, "menuItemDefaultTextColorPreference")
        );

        color.setMenuItemSelectedTextColor(
                getConvertedHexFromPreference(preferences, "menuItemSelectedTextColorPreference")
        );

        color.setMenuItemVersionsDefaultColor(
                getConvertedHexFromPreference(preferences, "menuItemVersionsDefaultColorPreference")
        );

        // combo color category

        String[] comboColors = {"", "", "", ""};

        for (int i = 0; i < 4; i++) {
            comboColors[i] = getConvertedHexFromPreference(preferences, (i + 1) + "comboColorPreference");
        }

        comboColor.setColors(comboColors);
        comboColor.setForceOverride(preferences.getBoolean("forceOverridePreference", false));

        // layout category

        HashMap<String, Object> backButtonSettings = new HashMap<String, Object>();

        backButtonSettings.put("h", preferences.getInt("backButtonHeightPreference", 200));
        backButtonSettings.put("scaleWhenHold", preferences.getBoolean("backButonScalePreference", false));
        backButtonSettings.put("w", preferences.getInt("backButtonWidthPreference", 100));

        layout.setBackButton(backButtonSettings);

        HashMap<String, Object> modsButtonSettings = new HashMap<String, Object>();

        modsButtonSettings.put("h", preferences.getInt("modsButtonHeightPreference", 200));
        modsButtonSettings.put("scale", preferences.getBoolean("modsButonScalePreference", false));
        modsButtonSettings.put("w", preferences.getInt("modsButtonWidthPreference", 100));

        layout.setModsButton(modsButtonSettings);

        HashMap<String, Object> optionsButtonSettings = new HashMap<String, Object>();

        modsButtonSettings.put("h", preferences.getInt("modsButtonHeightPreference", 200));
        modsButtonSettings.put("scale", preferences.getBoolean("modsButonScalePreference", false));
        modsButtonSettings.put("w", preferences.getInt("modsButtonWidthPreference", 100));

        layout.setModsButton(modsButtonSettings);

        // slider category

        // utils category

        // converting to JSON

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.toJson(skinProperties);
    }

    private void saveFile(String json, String filepath) {

        File directory = new File(Environment.getExternalStorageDirectory() + filepath);

        try {
            FileWriter fileWriter = new FileWriter("/storage/emulated/0/" + filepath + "/skin.json");
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivityForResult(Intent.createChooser(intent, "Choose save destination"), 1);

            return true;
        } else if (menuItemId == R.id.action_project_save_as) {
            // TODO: save as

            return true;
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            assert data != null;
            Log.i("Test", "Result URI " + data.getData());

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String configuredSkinProperties = getSkinPropertiesJSON(sharedPreferences);

            Log.i("json", configuredSkinProperties);

            saveFile(configuredSkinProperties, data.getDataString());
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