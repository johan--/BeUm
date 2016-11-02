package net.cosmiclion.opms.main.notices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.notice.NoticeFragment;
import net.cosmiclion.opms.main.notices.adapter.NoticesAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoticesFragment extends Fragment implements NoticesContract.View, NoticesAdapter.ViewHolder.ClickListener, SwipeRefreshLayout.OnRefreshListener{
    public static final String FRAGMENT = "NoticesFragment";

    public String TAG = getClass().getSimpleName();

    private RecyclerView mRecyclerView;

    private NoticesAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NoticesFragment() {
    }

    public static NoticesFragment newInstance() {
        NoticesFragment fragment = new NoticesFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.notices_frag, container, false);
        setupRecyclerView(layout);
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
    public void setPresenter(NoticesContract.Presenter presenter) {

    }

    @Override
    public void onItemClicked(int position) {
        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        NoticeFragment helpFragment = NoticeFragment.newInstance();
        transaction.replace(R.id.contentFrame, helpFragment, NoticeFragment.FRAGMENT);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /* FUNCTION */
    private void setupRecyclerView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_notices);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(i, "Notice " + i);
        }
        mAdapter = new NoticesAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    /* INNER CLASS */

}
