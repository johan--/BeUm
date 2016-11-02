package net.cosmiclion.opms.main.config;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import net.cosmiclion.beum.R;

public class ConfigFragment extends PreferenceFragmentCompat {
    public static final String FRAGMENT = "ConfigFragment";

    private final String TAG = getClass().getSimpleName();

    public static ConfigFragment newInstance() {
        ConfigFragment fragment = new ConfigFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }


}
