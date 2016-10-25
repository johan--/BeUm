package net.cosmiclion.beum.main.config;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;

public class ConfigFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    public static ConfigFragment newInstance(String deviceId) {
        ConfigFragment fragment = new ConfigFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.config_frag, container, false);

        return layout;
    }
}
