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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.nikameru.skinjsoneditor.ui.project.ProjectFragment;
import com.nikameru.skinjsoneditor.ui.project.SkinProperties;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Objects;

public class CreateProjectActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private String getConvertedHexFromPreference(@NonNull SharedPreferences preferences, String preferenceKey) {
        String preferenceColor = Integer.toHexString(
                preferences.getInt(preferenceKey, 171717)
        );

        return "#" + preferenceColor.substring(2).toUpperCase();
    }

    private HashMap<String, Object> getButtonLayout
            (@NonNull SharedPreferences preferences, HashMap<String, Object> buttonSettings, String buttonName) {

        buttonSettings.put("h", Integer.parseInt(
                preferences.getString(buttonName + "ButtonHeightPreference", "200"))
        );

        if (Objects.equals(buttonName, "back")) {
            buttonSettings.put(
                    "scaleWhenHold", preferences.getBoolean("backButtonScalePreference", false)
            );
        } else {
            buttonSettings.put(
                    "scale", Integer.parseInt(preferences.getString
                            (buttonName + "ButtonScalePreference", "1"))
            );
        }

        buttonSettings.put("w", Integer.parseInt(
                preferences.getString(buttonName + "ButtonWidthPreference", "100"))
        );

        return buttonSettings;
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
            comboColors[i] = getConvertedHexFromPreference(preferences, (i + 1) + "ComboColorPreference");
        }

        comboColor.setColors(comboColors);

        comboColor.setForceOverride(preferences.getBoolean("forceOverridePreference", false));

        // layout category

        HashMap<String, Object> backButtonSettings = new HashMap<>();
        layout.setBackButton(getButtonLayout(preferences, backButtonSettings, "back"));

        HashMap<String, Object> modsButtonSettings = new HashMap<>();
        layout.setModsButton(getButtonLayout(preferences, modsButtonSettings, "mods"));

        HashMap<String, Object> optionsButtonSettings = new HashMap<>();
        layout.setOptionsButton(getButtonLayout(preferences, optionsButtonSettings, "options"));

        HashMap<String, Object> randomButtonSettings = new HashMap<>();
        layout.setRandomButton(getButtonLayout(preferences, randomButtonSettings, "random"));

        layout.setUseNewLayout(preferences.getBoolean("useNewLayoutPreference", true));

        // slider category

        slider.setSliderBodyBaseAlpha(
                (float) preferences.getInt("sliderBodyBaseAlphaPreference", 100) / 100
        );

        slider.setSliderBodyColor(
                getConvertedHexFromPreference(preferences, "sliderBodyColorPreference")
        );

        slider.setSliderBorderColor(
                getConvertedHexFromPreference(preferences, "sliderBorderColorPreference")
        );

        slider.setSliderFollowComboColor(
                preferences.getBoolean("sliderFollowComboColor", false)
        );

        slider.setSliderHintAlpha(
                (float) preferences.getInt("sliderHintAlphaPreference", 100) / 100
        );

        slider.setSliderHintColor(
                getConvertedHexFromPreference(preferences, "sliderHintColorPreference")
        );

        slider.setSliderHintEnable(
                preferences.getBoolean("sliderHintEnablePreference", false)
        );

        slider.setSliderHintShowMinLength(
                Integer.parseInt(preferences.getString("sliderHintShowMinLengthPreference", "200"))
        );

        slider.setSliderHintWidth(
                Integer.parseInt(preferences.getString("sliderHintWidthPreference", "4"))
        );

        // utils category

        utils.setDisableKiai(
                preferences.getBoolean("disableKiaiPreference", true)
        );

        utils.setLimitComboTextLength(
                preferences.getBoolean("limitComboTextLength", true)
        );

        // sets

        skinProperties.setColor(color);
        skinProperties.setComboColor(comboColor);
        skinProperties.setLayout(layout);
        skinProperties.setSlider(slider);
        skinProperties.setUtils(utils);

        // converting to JSON

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.toJson(skinProperties);
    }

    private String getFilepath(String filepath) {

        return "/storage/emulated/0/" + String.valueOf(filepath)
                     .substring(String.valueOf(filepath).indexOf("primary%3A") + 10)
                     .replace("%2F", "/") + "/skin.json";
    }

    private void saveFile(String json, Uri filepath) {

        try {
            String stringFilepath = getFilepath(String.valueOf(filepath));

            FileWriter fileWriter = new FileWriter(stringFilepath);

            fileWriter.write(json);
            fileWriter.close();

            Toast.makeText(this, "Saved to " + stringFilepath + "!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Error! Please grant me an access to the storage!", Toast.LENGTH_LONG).show();
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

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            try {
                String configuredSkinProperties = getSkinPropertiesJSON(sharedPreferences);

                saveFile(configuredSkinProperties, data.getData());
            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(
                        this, "Error! You've inserted parameters with wrong data types!", Toast.LENGTH_LONG
                ).show();
            }
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