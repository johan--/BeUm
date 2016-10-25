package net.cosmiclion.beum.main.mylibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;

public class MyLibraryFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    public static MyLibraryFragment newInstance(String deviceId) {
        MyLibraryFragment fragment = new MyLibraryFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.my_library_frag, container, false);

        return layout;
    }
}
