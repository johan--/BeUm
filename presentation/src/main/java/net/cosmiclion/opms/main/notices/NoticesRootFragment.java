package net.cosmiclion.opms.main.notices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;

public class NoticesRootFragment extends Fragment {

    public static NoticesRootFragment newInstance() {
        NoticesRootFragment fragment = new NoticesRootFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notices_root_frag, container, false);
    }


}
