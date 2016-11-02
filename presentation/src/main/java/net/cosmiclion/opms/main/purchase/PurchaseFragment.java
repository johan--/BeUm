package net.cosmiclion.opms.main.purchase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.library.model.BookDomain;
import net.cosmiclion.opms.main.purchase.adapter.BooksPurchaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class PurchaseFragment extends Fragment implements PurchaseContract.View, BooksPurchaseAdapter.ViewHolder.ClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String FRAGMENT = "PurchaseFragment";

    private String TAG = getClass().getSimpleName();

    private ImageButton btnActionSearch;
    private BooksPurchaseAdapter mRecyclerAdaper;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public PurchaseFragment() {
    }

    public static PurchaseFragment newInstance() {
        PurchaseFragment fragment = new PurchaseFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.purchase_frag, container, false);
        setupActionView(layout);
        setupRecyleView(layout);
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
    public void showBooksView(List<BookDomain> books) {

    }

    @Override
    public void setPresenter(PurchaseContract.Presenter presenter) {

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /* FUNCTION */
    private void setupActionView(View layout) {
        btnActionSearch = (ImageButton) layout.findViewById(R.id.btnPurchaseSearch);
        btnActionSearch.setOnClickListener(actionListener);
    }

    private void setupRecyleView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_purchase);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        List<BookDomain> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            books.add(new BookDomain("Book ", "Book name " + i, null));
        }

        mRecyclerAdaper = new BooksPurchaseAdapter(this, books);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mRecyclerAdaper);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

    /* LISTENER */
    private View.OnClickListener actionListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnPurchaseSearch:

                    break;

                default:
                    break;
            }
        }
    };


    /* INNER CLASS */



}
