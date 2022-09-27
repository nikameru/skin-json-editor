package com.nikameru.skinjsoneditor;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Objects;

public class JSONAssembler {

    private String getConvertedHexFromPreference(@NonNull SharedPreferences preferences, String preferenceKey) {
        String preferenceColor = Integer.toHexString(
                preferences.getInt(preferenceKey, 0)
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

    protected String getSkinPropertiesJSON(@NonNull final SharedPreferences preferences) {

        // instances creation

        SkinProperties skinProperties = new SkinProperties();

        SkinProperties.ColorCategory color = new SkinProperties.ColorCategory();
        SkinProperties.ComboColorCategory comboColor = new SkinProperties.ComboColorCategory();
        SkinProperties.LayoutCategory layout = new SkinProperties.LayoutCategory();
        SkinProperties.SliderCategory slider = new SkinProperties.SliderCategory();
        SkinProperties.UtilsCategory utils = new SkinProperties.UtilsCategory();

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

        String[] comboColors = { "", "", "", "" };

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
                Float.parseFloat(preferences.getString("sliderHintWidthPreference", "4"))
        );

        // utils category

        utils.setDisableKiai(
                preferences.getBoolean("disableKiaiPreference", true)
        );

        utils.setLimitComboTextLength(
                preferences.getBoolean("limitComboTextLength", true)
        );

        // sets

        skinProperties.setColorCategory(color);
        skinProperties.setComboColorCategory(comboColor);
        skinProperties.setLayoutCategory(layout);
        skinProperties.setSliderCategory(slider);
        skinProperties.setUtilsCategory(utils);

        // converting to JSON

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.toJson(skinProperties);
    }
}