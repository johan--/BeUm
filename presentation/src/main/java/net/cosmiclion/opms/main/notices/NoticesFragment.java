package net.cosmiclion.opms.main.notices;

/**
 * Created by longpham on 11/9/2016.
 */

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

public class NoticesFragment extends Fragment implements NoticesAdapter.ViewHolder.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;

    private NoticesAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NoticesFragment() {
    }

    public static NoticesFragment newInstance() {
        NoticesFragment fragment = new NoticesFragment();
        return fragment;
    }

    @Override
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
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
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

    private void setupRecyclerView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_notices);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        List<String> data = new ArrayList<>();
        data.add(0,"가로에서 양면페이지 보기");
        data.add(1,"회전금지");
        data.add(2,"배경선택");
        data.add(3,"페이지 전환효과");
        data.add(4,"없음");
        data.add(5,"슬라이드");
        data.add(6,"넘기기");

        mAdapter = new NoticesAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }
}
