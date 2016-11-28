package net.cosmiclion.opms.main.quickmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.main.MainActivity;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItem;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.utils.tasks.BookItem;

import java.util.ArrayList;
import java.util.List;

public class QuickMenuFragment extends Fragment implements QuickMenuContract.View, SwipeRefreshLayout.OnRefreshListener, QuickMenuAdapter.ViewHolder.ClickListener {

    public static final String FRAGMENT = "QuickMenuFragment";

    private String TAG = getClass().getSimpleName();

    private Activity activity;
    private ViewPurchaseListListener mViewPurchaseListener;
    private TextView tvViewAllPurchase;

    private QuickMenuContract.Presenter mPresenter;

    private QuickMenuAdapter mRecyclerAdaper;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public QuickMenuFragment() {
    }

    public static QuickMenuFragment newInstance() {
        QuickMenuFragment fragment = new QuickMenuFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mViewPurchaseListener = (ViewPurchaseListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemClickedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.quick_menu_frag, container, false);
//        setupRecyleView(layout);
        setBookClickedListener(layout);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewPager(view);
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.start();
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
        Debug.i(TAG, "item =" + position);
    }

    /* FUNCTION */
//    private void setupRecyleView(View view) {
//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_menu);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        List<QuickMenuItem> books = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            books.add(new QuickMenuItem("Book ", "Book name " + i, null));
//        }
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
//                DividerItemDecoration.VERTICAL);
//        mRecyclerAdaper = new QuickMenuAdapter(this, books);
//
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        mRecyclerView.setAdapter(mRecyclerAdaper);
//        mRecyclerView.addItemDecoration(dividerItemDecoration);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setNestedScrollingEnabled(true);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
//    }

    private void setViewPager(View view) {
        ImageButton btnPrev = (ImageButton) view.findViewById(R.id.btnPrev);
        ImageButton btnNext = (ImageButton) view.findViewById(R.id.btnNext);
        final CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(true);
        List<String> bookTitle = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bookTitle.add(i, "Book " + i);
        }
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager(), bookTitle));

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem();
                int totalPages = viewPager.getAdapter().getCount();

                int previousPage = currentPage - 1;
                if (previousPage < 0) {
                    // We can't go back anymore.
                    // Loop to the last page. If you don't want looping just
                    // return here.
                    previousPage = totalPages - 1;
                }

                viewPager.setCurrentItem(previousPage, true);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem();
                int totalPages = viewPager.getAdapter().getCount();

                int nextPage = currentPage + 1;
                if (nextPage >= totalPages) {
                    // We can't go forward anymore.
                    // Loop to the first page. If you don't want looping just
                    // return here.
                    nextPage = 0;
                }

                viewPager.setCurrentItem(nextPage, true);
            }
        });
    }

    private void setBookClickedListener(View view) {
        ImageView ivBook1 = (ImageView) view.findViewById(R.id.ivBook1);
        ivBook1.setOnClickListener(bookClickListener);
        ImageView ivBook2 = (ImageView) view.findViewById(R.id.ivBook2);
        ivBook2.setOnClickListener(bookClickListener);

        tvViewAllPurchase = (TextView) view.findViewById(R.id.tvViewAllPurchase);
        tvViewAllPurchase.setOnClickListener(bookClickListener);
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        private List<String> mBookTitle;

        public MyAdapter(FragmentManager fm, List<String> bookTitle) {
            super(fm);
            this.mBookTitle = bookTitle;
        }

        @Override
        public int getCount() {
            return (mBookTitle != null ? mBookTitle.size() : 0);
        }

        @Override
        public Fragment getItem(int position) {
            return RecentReadFragment.newInstance(mBookTitle.get(position));
        }
    }

    private View.OnClickListener bookClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivBook1:
                    ((MainActivity) getActivity()).openDemoDocument(
                            new BookItem(0, "BK0000314002", 0, "BK0000314002.epub", 0));
                    break;
                case R.id.ivBook2:
                    ((MainActivity) getActivity()).openDemoDocument(
                            new BookItem(0, "BK0000314002", 0, "BK0000314002.epub", 0));
                    break;
                case R.id.tvViewAllPurchase:
                    mViewPurchaseListener.viewAllPurchaseList();
                    break;
                default:
                    break;
            }
        }
    };

    public interface ViewPurchaseListListener {
        void viewAllPurchaseList();
    }
}
