package net.cosmiclion.beum.main.quickmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;

public class QuickMenuFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = getClass().getSimpleName();

    public static QuickMenuFragment newInstance(String uid) {
        QuickMenuFragment fragment = new QuickMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.quick_menu_frag, container, false);

        return layout;
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "On refresh");
    }

}
