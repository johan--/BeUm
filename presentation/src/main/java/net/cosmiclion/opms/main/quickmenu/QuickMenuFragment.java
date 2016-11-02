package net.cosmiclion.opms.main.quickmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItem;

import java.util.ArrayList;
import java.util.List;

public class QuickMenuFragment extends Fragment implements QuickMenuContract.View, SwipeRefreshLayout.OnRefreshListener, QuickMenuAdapter.ViewHolder.ClickListener {

    public static final String FRAGMENT = "QuickMenuFragment";

    private String TAG = getClass().getSimpleName();

    private QuickMenuContract.Presenter mPresenter;

    private QuickMenuAdapter mRecyclerAdaper;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static QuickMenuFragment newInstance() {
        QuickMenuFragment fragment = new QuickMenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.quick_menu_frag, container, false);
        setupRecyleView(layout);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showBooksView(List<QuickMenuItem> books) {

    }

    @Override
    public void setPresenter(QuickMenuContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onItemClicked(int position) {

    }

    /* FUNCTION */
    private void setupRecyleView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_menu);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        List<QuickMenuItem> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            books.add(new QuickMenuItem("Book ", "Book name " + i, null));
        }
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
        mRecyclerAdaper = new QuickMenuAdapter(this, books);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mRecyclerAdaper);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }


}
