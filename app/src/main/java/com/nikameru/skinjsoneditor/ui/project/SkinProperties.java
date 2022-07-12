package com.nikameru.skinjsoneditor.ui.project;

import java.util.HashMap;

public class SkinProperties {

    private ColorCategory Color = new ColorCategory();
    private ComboColorCategory ComboColor = new ComboColorCategory();
    private LayoutCategory Layout = new LayoutCategory();
    private SliderCategory Slider = new SliderCategory();
    private UtilsCategory Utils = new UtilsCategory();

    // Color category

    public static class ColorCategory {
        private String MenuItemDefaultColor = "#F8D59D";
        private String MenuItemDefaultTextColor = "#FFFFFF";
        private String MenuItemSelectedTextColor = "F4EADA";
        private String MenuItemVersionsDefaultColor = "#F4EADA";

        public String getMenuItemDefaultColor() {
            return MenuItemDefaultColor;
        }

        public void setMenuItemDefaultColor(String menuItemDefaultColor) {
            MenuItemDefaultColor = menuItemDefaultColor;
        }

        public String getMenuItemDefaultTextColor() {
            return MenuItemDefaultTextColor;
        }

        public void setMenuItemDefaultTextColor(String menuItemDefaultTextColor) {
            MenuItemDefaultTextColor = menuItemDefaultTextColor;
        }

        public String getMenuItemSelectedTextColor() {
            return MenuItemSelectedTextColor;
        }

        public void setMenuItemSelectedTextColor(String menuItemSelectedTextColor) {
            MenuItemSelectedTextColor = menuItemSelectedTextColor;
        }

        public String getMenuItemVersionsDefaultColor() {
            return MenuItemVersionsDefaultColor;
        }

        public void setMenuItemVersionsDefaultColor(String menuItemVersionsDefaultColor) {
            MenuItemVersionsDefaultColor = menuItemVersionsDefaultColor;
        }

        public ColorCategory() {

        }
    }

    // ComboColor category

    public static class ComboColorCategory {
        private String[] colors = {"#9A1537", "#50954C", "#FBDB92", "#2E9DF6"};
        private boolean forceOverride = true;

        public String[] getColors() {
            return colors;
        }

        public void setColors(String[] colors) {
            this.colors = colors;
        }

        public boolean isForceOverride() {
            return forceOverride;
        }

        public void setForceOverride(boolean forceOverride) {
            this.forceOverride = forceOverride;
        }

        public ComboColorCategory() {

        }
    }

    // Layout category

    public static class LayoutCategory {
        private HashMap<String, Object> BackButton = new HashMap<>();
        private HashMap<String, Object> ModsButton = new HashMap<>();
        private HashMap<String, Object> OptionsButton = new HashMap<>();
        private HashMap<String, Object> RandomButton = new HashMap<>();

        private boolean useNewLayout = true;

        public HashMap<String, Object> getBackButton() {
            return BackButton;
        }

        public void setBackButton(HashMap<String, Object> backButton) {
            BackButton = backButton;
        }

        public HashMap<String, Object> getModsButton() {
            return ModsButton;
        }

        public void setModsButton(HashMap<String, Object> modsButton) {
            ModsButton = modsButton;
        }

        public HashMap<String, Object> getOptionsButton() {
            return OptionsButton;
        }

        public void setOptionsButton(HashMap<String, Object> optionsButton) {
            OptionsButton = optionsButton;
        }

        public HashMap<String, Object> getRandomButton() {
            return RandomButton;
        }

        public void setRandomButton(HashMap<String, Object> randomButton) {
            RandomButton = randomButton;
        }

        public boolean isUseNewLayout() {
            return useNewLayout;
        }

        public void setUseNewLayout(boolean useNewLayout) {
            this.useNewLayout = useNewLayout;
        }

        public LayoutCategory() {

        }
    }

    // Slider category

    public static class SliderCategory {
        private float sliderBodyBaseAlpha = 0.4f;
        private String sliderBodyColor = "#444444";
        private String sliderBorderColor = "#E4E4E4";
        private boolean sliderFollowComboColor = false;
        private float sliderHintAlpha = 0.8f;
        private String sliderHintColor = "#E4E4E4";
        private boolean sliderHintEnable = false;
        private int sliderHintShowMinLength = 200;
        private float sliderHintWidth = 3.5f;

        public float getSliderBodyBaseAlpha() {
            return sliderBodyBaseAlpha;
        }

        public void setSliderBodyBaseAlpha(float sliderBodyBaseAlpha) {
            this.sliderBodyBaseAlpha = sliderBodyBaseAlpha;
        }

        public String getSliderBodyColor() {
            return sliderBodyColor;
        }

        public void setSliderBodyColor(String sliderBodyColor) {
            this.sliderBodyColor = sliderBodyColor;
        }

        public String getSliderBorderColor() {
            return sliderBorderColor;
        }

        public void setSliderBorderColor(String sliderBorderColor) {
            this.sliderBorderColor = sliderBorderColor;
        }

        public boolean isSliderFollowComboColor() {
            return sliderFollowComboColor;
        }

        public void setSliderFollowComboColor(boolean sliderFollowComboColor) {
            this.sliderFollowComboColor = sliderFollowComboColor;
        }

        public float getSliderHintAlpha() {
            return sliderHintAlpha;
        }

        public void setSliderHintAlpha(float sliderHintAlpha) {
            this.sliderHintAlpha = sliderHintAlpha;
        }

        public String getSliderHintColor() {
            return sliderHintColor;
        }

        public void setSliderHintColor(String sliderHintColor) {
            this.sliderHintColor = sliderHintColor;
        }

        public boolean isSliderHintEnable() {
            return sliderHintEnable;
        }

        public void setSliderHintEnable(boolean sliderHintEnable) {
            this.sliderHintEnable = sliderHintEnable;
        }

        public int getSliderHintShowMinLength() {
            return sliderHintShowMinLength;
        }

        public void setSliderHintShowMinLength(int sliderHintShowMinLength) {
            this.sliderHintShowMinLength = sliderHintShowMinLength;
        }

        public float getSliderHintWidth() {
            return sliderHintWidth;
        }

        public void setSliderHintWidth(float sliderHintWidth) {
            this.sliderHintWidth = sliderHintWidth;
        }

        public SliderCategory() {

        }
    }

    // Utils category

    public static class UtilsCategory {
        private boolean disableKiai = true;
        private boolean limitComboTextLength = true;

        public boolean isDisableKiai() {
            return disableKiai;
        }

        public void setDisableKiai(boolean disableKiai) {
            this.disableKiai = disableKiai;
        }

        public boolean isLimitComboTextLength() {
            return limitComboTextLength;
        }

        public void setLimitComboTextLength(boolean limitComboTextLength) {
            this.limitComboTextLength = limitComboTextLength;
        }

        public UtilsCategory() {

        }
    }

    public ColorCategory getColorCategory() {
        return Color;
    }

    public void setColorCategory(ColorCategory color) {
        this.Color = color;
    }

    public ComboColorCategory getComboColorCategory() {
        return ComboColor;
    }

    public void setComboColorCategory(ComboColorCategory comboColor) {
        this.ComboColor = comboColor;
    }

    public LayoutCategory getLayoutCategory() {
        return Layout;
    }

    public void setLayoutCategory(LayoutCategory layout) {
        this.Layout = layout;
    }

    public SliderCategory getSliderCategory() {
        return Slider;
    }

    public void setSliderCategory(SliderCategory slider) {
        this.Slider = slider;
    }

    public UtilsCategory getUtilsCategory() {
        return Utils;
    }

    public void setUtilsCategory(UtilsCategory utils) {
        this.Utils = utils;
    }

    // Constructor

    public SkinProperties() {

    }
}