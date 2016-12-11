package net.cosmiclion.opms.main.help;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;

public class HelpFragment extends Fragment implements HelpContract.View {
    public static final String FRAGMENT = "HelpFragment";

    private String TAG = getClass().getSimpleName();

    public HelpFragment() {
    }

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_item_notify).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.help_frag, container, false);

        return layout;
    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showContent() {

    }

    @Override
    public void setPresenter(HelpContract.Presenter presenter) {

    }
}
