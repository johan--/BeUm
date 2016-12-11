package net.cosmiclion.opms.main.config;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.utils.Debug;


public class ConfigFragment extends PreferenceFragmentCompat implements
        SwitchPreferenceCompat.SwitchListener,
        ThemeSelectPreference.ThemeListener,
        PageSlidePreference.PageSlideListener {

    public static final String FRAGMENT = "ConfigFragment";

    public static final String CONFIG_DOUBLE_PAGE_KEY = "DOUBLE_PAGE_KEY";
    public static final String CONFIG_ROTATE_KEY = "ROTATE_KEY";
    public static final String CONFIG_THEME_KEY = "THEME_KEY";
    public static final String CONFIG_PAGE_ANIMATION_KEY = "PAGE_ANIMATION_KEY";


    public static final boolean CONFIG_DOUBLE_PAGE = false;
    public static final boolean CONFIG_ROTATE = false;

    public static final int CONFIG_THEME_WHITE = 0;
    public static final int CONFIG_THEME_BROWN = 1;
    public static final int CONFIG_THEME_BLACK = 2;

    public static final int CONFIG_PAGE_NONE = 0;
    public static final int CONFIG_PAGE_SLIDE = 1;
    public static final int CONFIG_PAGE_FLIPPING = 2;

    private final String TAG = getClass().getSimpleName();

    private SharedPreferences mPrefs;

    private SharedPreferences.Editor mPrefsEditor;

    private SwitchPreferenceCompat switchDoublePage, switchRotate;

    private ThemeSelectPreference themeViewer;

    private PageSlidePreference pagerAnimation;

    public ConfigFragment() {
        Debug.i(TAG, "Hello");
    }

    public static ConfigFragment newInstance() {
        ConfigFragment fragment = new ConfigFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
//        setPreferencesFromResource(R.xml.preferences, s);
        addPreferencesFromResource(R.xml.preferences);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mPrefsEditor = mPrefs.edit();

        switchDoublePage = (SwitchPreferenceCompat) findPreference(getString(R.string.config_double_page));
        switchDoublePage.setOnSwitchListener(this);

        switchRotate = (SwitchPreferenceCompat) findPreference(getString(R.string.config_rotate));
        switchRotate.setOnSwitchListener(this);

        themeViewer = (ThemeSelectPreference) findPreference(getString(R.string.config_theme));
        themeViewer.setOnThemeChangeListener(this);

        pagerAnimation = (PageSlidePreference) findPreference(getString(R.string.config_page_slide));
        pagerAnimation.setOnPageSlideChangeListener(this);

        initConfig();
        setupConfig();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSwitchChange(String buttonKey, boolean isChecked) {
        Debug.i(TAG, buttonKey + " onSwitchChange " + isChecked);
        if (buttonKey.equals(getResources().getString(R.string.config_double_page))) {
            mPrefsEditor.putBoolean(CONFIG_DOUBLE_PAGE_KEY, isChecked);
        } else if (buttonKey.equals(getResources().getString(R.string.config_rotate))) {
            mPrefsEditor.putBoolean(CONFIG_ROTATE_KEY, isChecked);
        }
        mPrefsEditor.commit();
    }

    @Override
    public void onThemeChange(int theme) {
        Debug.i(TAG, " onThemeChange " + theme);
        mPrefsEditor.putInt(CONFIG_THEME_KEY, theme);
        mPrefsEditor.commit();
    }

    @Override
    public void onPageSlideChange(int slide) {
        Debug.i(TAG, " onPageSlideChange " + slide);
        mPrefsEditor.putInt(CONFIG_PAGE_ANIMATION_KEY, slide);
        mPrefsEditor.commit();
    }

    private boolean doublePageValue;
    private boolean rotateValue;
    private int themeValue;
    private int pageAnimationValue;

    private void initConfig() {
        doublePageValue = false;
        rotateValue = false;
        themeValue = CONFIG_THEME_WHITE;
        pageAnimationValue = CONFIG_PAGE_NONE;
    }

    private void setupConfig() {

        if (!mPrefs.contains(CONFIG_DOUBLE_PAGE_KEY)) {
            mPrefsEditor.putBoolean(CONFIG_DOUBLE_PAGE_KEY, CONFIG_DOUBLE_PAGE);
        } else {
            doublePageValue = mPrefs.getBoolean(CONFIG_DOUBLE_PAGE_KEY, false);
        }
        if (!mPrefs.contains(CONFIG_ROTATE_KEY)) {
            mPrefsEditor.putBoolean(CONFIG_ROTATE_KEY, CONFIG_ROTATE);
        } else {
            rotateValue = mPrefs.getBoolean(CONFIG_ROTATE_KEY, false);
        }
        if (!mPrefs.contains(CONFIG_THEME_KEY)) {
            mPrefsEditor.putInt(CONFIG_THEME_KEY, CONFIG_THEME_WHITE);
        } else {
            themeValue = mPrefs.getInt(CONFIG_THEME_KEY, CONFIG_THEME_WHITE);
        }
        if (!mPrefs.contains(CONFIG_PAGE_ANIMATION_KEY)) {
            mPrefsEditor.putInt(CONFIG_PAGE_ANIMATION_KEY, CONFIG_PAGE_NONE);
        } else {
            pageAnimationValue = mPrefs.getInt(CONFIG_PAGE_ANIMATION_KEY, CONFIG_PAGE_NONE);
        }
        mPrefsEditor.commit();


        switchDoublePage.setChecked(doublePageValue);
        switchRotate.setChecked(rotateValue);
        themeViewer.getSelectedTheme(themeValue);
        pagerAnimation.getPageSlideSelected(pageAnimationValue);

    }
}
