package net.cosmiclion.beum.main.quickmenu;

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

public class QuickMenuRootFragment extends Fragment {
    private static final String ROOT_DEVICEID = "ROOT_DEVICEID";
    private final String TAG = getClass().getSimpleName();

    public static QuickMenuRootFragment newInstance(String deviceId) {
        QuickMenuRootFragment fragment = new QuickMenuRootFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ROOT_DEVICEID, deviceId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.quick_menu_root_frag, container, false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        /*
		 * When this container fragment is created, we fill it with our first
		 * "real" fragment
		 */
        String deviceId = getArguments().getString(ROOT_DEVICEID);
        Fragment fragment = QuickMenuFragment.newInstance(deviceId);
        transaction.replace(R.id.quick_menu_root, fragment);
        transaction.commit();
        return view;
    }

}