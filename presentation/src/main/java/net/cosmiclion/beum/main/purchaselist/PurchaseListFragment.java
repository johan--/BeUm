package net.cosmiclion.beum.main.purchaselist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;

public class PurchaseListFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    public static PurchaseListFragment newInstance(String deviceId) {
        PurchaseListFragment fragment = new PurchaseListFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.purchase_list_frag, container, false);

        return layout;
    }
}
