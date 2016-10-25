package net.cosmiclion.beum.main.mylibrary;

/**
 * Created by LONGPD on 1/18/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;

public class MyLibraryRootFragment extends Fragment {
    private static final String ROOT_DEVICEID = "ROOT_DEVICEID";
    private final String TAG = getClass().getSimpleName();
    public static MyLibraryRootFragment newInstance(String deviceId) {
        MyLibraryRootFragment fragment = new MyLibraryRootFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.my_library_root_frag, container, false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
		/*
		 * When this container fragment is created, we fill it with our first
		 * "real" fragment
		 */
        Fragment fragment = MyLibraryFragment.newInstance("");
        transaction.replace(R.id.my_library_root, fragment, "ID next fragment");
        transaction.commit();
        return view;
    }

}