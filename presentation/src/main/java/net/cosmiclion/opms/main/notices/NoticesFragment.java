package net.cosmiclion.opms.main.notices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.notice.NoticeFragment;
import net.cosmiclion.opms.main.notices.adapter.NoticesAdapter;
import net.cosmiclion.opms.main.notices.model.NoticeDomain;
import net.cosmiclion.opms.main.notices.source.NoticesRepository;
import net.cosmiclion.opms.main.notices.source.local.NoticesLocalDataSource;
import net.cosmiclion.opms.main.notices.source.remote.NoticesRemoteDataSource;
import net.cosmiclion.opms.main.notices.usecase.DoGetNotices;
import net.cosmiclion.opms.utils.Debug;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.stetho.inspector.network.ResponseHandlingInputStream.TAG;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 11/9/2016.
 */
public class NoticesFragment extends Fragment implements
        NoticesContract.View,
        NoticesAdapter.ViewHolder.ClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String FRAGMENT = "NoticesFragment";
    public static final String BUNDLE_KEY_NOTICE_TITLE = "BUNDLE_KEY_NOTICE_TITLE";
    public static final String BUNDLE_KEY_NOTICE_CONTENT = "BUNDLE_KEY_NOTICE_CONTENT";
    private static final String BUNDLE_NOTICES = "BUNDLE_NOTICES";


    private RecyclerView mRecyclerView;
    private NoticesAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NoticesContract.Presenter mPresenter;
    private ProgressDialog pDialog;

    public NoticesFragment() {
    }

    public static NoticesFragment newInstance() {
        NoticesFragment fragment = new NoticesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pDialog = new ProgressDialog(getActivity());
        Context context = getActivity();
        mPresenter = new NoticesPresenter(UseCaseHandler.getInstance(), this,
                new DoGetNotices(NoticesRepository.getInstance(
                        NoticesRemoteDataSource.getInstance(context),
                        NoticesLocalDataSource.getInstance(context))));
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_item_notify).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.notices_frag, container, false);
        setupRecyclerView(layout);
        restoreData(savedInstanceState);
        setRetainInstance(true);
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Debug.i(TAG, "onSaveInstanceState = ");
        //Save the fragment's state here
        outState.putParcelableArrayList(BUNDLE_NOTICES, new ArrayList<>(mAdapter.getNotices()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Debug.i(TAG, "onActivityCreated = ");
        if (savedInstanceState != null) {
            //Restore the fragment's state here
            restoreData(savedInstanceState);
        }
    }

    private void restoreData(Bundle bundle) {
        if (bundle != null) {
            List<NoticeDomain> books = bundle.getParcelableArrayList(BUNDLE_NOTICES);
            mAdapter.replaceData(books);
        } else {
            mPresenter.start();
        }
    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mPresenter.loadNotices(false);
    }

    @Override
    public void onItemClicked(NoticeDomain noticeDomain) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        NoticeFragment noticeFragment = NoticeFragment.newInstance();
        Bundle bundles = new Bundle();
        if (noticeDomain != null) {
            bundles.putString(BUNDLE_KEY_NOTICE_TITLE, noticeDomain.board_title);
            bundles.putString(BUNDLE_KEY_NOTICE_CONTENT, noticeDomain.board_content);
            Debug.i(TAG, " noticeDomain is valid");
        } else {
            Debug.i(TAG, "noticeDomain is null");
        }
        noticeFragment.setArguments(bundles);
        transaction.add(R.id.childNoticesFrame, noticeFragment, NoticeFragment.FRAGMENT);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setupRecyclerView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_notices);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        List<String> data = new ArrayList<>();
//        data.add(0, "가로에서 양면페이지 보기");
//        data.add(1, "회전금지");
//        data.add(2, "배경선택");
//        data.add(3, "페이지 전환효과");
//        data.add(4, "없음");
//        data.add(5, "슬라이드");
//        data.add(6, "넘기기");

        mAdapter = new NoticesAdapter(this, new ArrayList<NoticeDomain>(0));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showNotices(@NonNull List<NoticeDomain> notices) {
        mAdapter.replaceData(notices);
    }


    @Override
    public void setPresenter(NoticesContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }

    private void showProgressDialog(String title, String message) {
        if (!pDialog.isShowing()) {
            pDialog.setTitle(title);
            pDialog.setMessage(message);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
