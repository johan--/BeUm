package net.cosmiclion.opms.main.quickmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.MainActivity;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseData;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseDomain;
import net.cosmiclion.opms.main.purchase.source.local.PurchaseLocalDataSource;
import net.cosmiclion.opms.main.quickmenu.source.local.QuickMenuLocalDataSource;
import net.cosmiclion.opms.main.quickmenu.source.remote.QuickMenuRemoteDataSource;
import net.cosmiclion.opms.main.quickmenu.usecase.GetQuickMenuItems;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.utils.tasks.BookItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.cosmiclion.opms.main.purchase.PurchaseFragment.BUNDLE_BOOKS_PURCHASE;
import static net.cosmiclion.opms.main.purchase.PurchaseFragment.BUNDLE_MOBILE_TOKEN;
import static net.cosmiclion.opms.main.purchase.source.local.PurchaseLocalDataSource.GET_RECENT_BOOK_READ;
import static net.cosmiclion.opms.utils.Constants.APP_PATH;

public class QuickMenuFragment extends Fragment implements
        QuickMenuContract.View,
        SwipeRefreshLayout.OnRefreshListener
        , QuickMenuAdapter.ViewHolder.ClickListener

//        ,LibraryFragment.BookReadedListener
{

    public static final String FRAGMENT = "QuickMenuFragment";
    public static final String BUNDLE_BOOKS_RECENT = "BUNDLE_BOOKS_RECENT";

    private String TAG = getClass().getSimpleName();

    private Activity activity;
    private ViewPurchaseListListener mViewPurchaseListener;
    private TextView tvViewAllPurchase;
    private TextView tvNodata;

    private QuickMenuContract.Presenter mPresenter;

    private QuickMenuAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<BookPurchaseDomain> mBooksPurchase;
    private List<BookPurchaseData> mBooksReaded;
    private MyAdapter mBookRecentAdapter;
    private PurchaseLocalDataSource mDataSource;
    private QuickMenuRepository mRepository;

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

        Context context = getActivity();
        mRepository = QuickMenuRepository.getInstance(
                QuickMenuRemoteDataSource.getInstance(context),
                QuickMenuLocalDataSource.getInstance(context));

        mPresenter = new QuickMenuPresenter(UseCaseHandler.getInstance(), this,
                new GetQuickMenuItems(mRepository)
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.quick_menu_frag, container, false);
        setupRecyleView(layout);
        setViewPager(layout);
        restoreData(savedInstanceState);
        setBookClickedListener(layout);
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Debug.i(TAG, "onSaveInstanceState = ");
        //Save the fragment's state here
        outState.putString(BUNDLE_MOBILE_TOKEN, mPresenter.getMobileToken());
        outState.putParcelableArrayList(BUNDLE_BOOKS_PURCHASE, new ArrayList<>(mRecyclerAdapter.getBooks()));
//        outState.putParcelableArrayList(BUNDLE_BOOKS_RECENT, new ArrayList<>(mBookRecentAdapter.getBooks()));
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
        mDataSource = PurchaseLocalDataSource.getInstance(getActivity());
        mBooksReaded = mDataSource.getBooks(GET_RECENT_BOOK_READ);
        mBookRecentAdapter.replaceData(mBooksReaded);
        showNodata();
        Debug.i(TAG, mBooksReaded.size() + " size");

        if (bundle != null) {
            String token = bundle.getString(BUNDLE_MOBILE_TOKEN);
            List<BookPurchaseDomain> books = bundle.getParcelableArrayList(BUNDLE_BOOKS_PURCHASE);
            mRecyclerAdapter.replaceData(books);

        } else {
            mPresenter.getMobileToken(this.getActivity());
            mPresenter.start();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    public void showBooksView(List<BookPurchaseDomain> books) {
        mRecyclerAdapter.replaceData(books);
        Debug.i(TAG, "size quickview=" + mRecyclerAdapter.getItemCount());
    }

    @Override
    public void setPresenter(QuickMenuContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mPresenter != null) {
            mPresenter.getMobileToken(this.getActivity());
        }
        this.mPresenter.loadBooks(false);
    }

    private void setupRecyleView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_purchase_recent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerAdapter = new QuickMenuAdapter(
                this, new ArrayList<BookPurchaseDomain>(0), getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mRecyclerAdapter);


        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void setViewPager(View view) {
        ImageButton btnPrev = (ImageButton) view.findViewById(R.id.btnPrev);
        ImageButton btnNext = (ImageButton) view.findViewById(R.id.btnNext);
        tvNodata = (TextView) view.findViewById(R.id.tvNodata);

        final CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(true);

        mBookRecentAdapter = new MyAdapter(getChildFragmentManager(), mBooksReaded);
        viewPager.setAdapter(mBookRecentAdapter);
        showNodata();
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem();
                int totalPages = viewPager.getAdapter().getCount();
//                Debug.i(TAG, "total="+viewPager.getn);
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

    private void showNodata() {

        if (mBookRecentAdapter.getBooks() == null) {
            tvNodata.setVisibility(View.VISIBLE);
        } else if (mBookRecentAdapter.getBooks().size() == 0) {
            tvNodata.setVisibility(View.VISIBLE);
        } else {
            tvNodata.setVisibility(View.INVISIBLE);
        }
    }

    private void setBookClickedListener(View view) {


        tvViewAllPurchase = (TextView) view.findViewById(R.id.tvViewAllPurchase);
        tvViewAllPurchase.setOnClickListener(bookClickListener);
    }


    public void updateBooksReaded() {
        Debug.i(TAG, "updateBooksReaded 222");
        mBooksReaded.clear();
        mBooksReaded = mDataSource.getBooks(GET_RECENT_BOOK_READ);
        mBookRecentAdapter.replaceData(mBooksReaded);
        showNodata();
    }

    public void updateBooksPurchase(List<BookPurchaseDomain> list) {
        Debug.i(TAG, "sssssss");
    }

    @Override
    public void onItemClicked(int position, BookPurchaseDomain book) {
        File file = new File(APP_PATH, book.filename);
        if (file.exists()) {

            ((MainActivity) getActivity()).openDemoDocument(
                    new BookItem(0, book.product_title, 0, book.filename, 0));
        } else {
            Toast.makeText(getActivity(),
                    "You need to purchase this ebook.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        private List<BookPurchaseData> mBooks;

        public MyAdapter(FragmentManager fm, List<BookPurchaseData> books) {
            super(fm);
            this.mBooks = books;
        }

        private void setList(List<BookPurchaseData> books) {
            mBooks = checkNotNull(books);
        }

        public void replaceData(List<BookPurchaseData> books) {
            setList(books);
            notifyDataSetChanged();
        }

        public List<BookPurchaseData> getBooks() {
            return mBooks;
        }

        @Override
        public int getCount() {
            return (mBooks != null ? mBooks.size() : 0);
        }

        @Override
        public Fragment getItem(int position) {
            Debug.i("MyAdapter", "mBooks.get(position)   =" + mBooks.get(position).cover_image1);
//            RecentReadFragment recentReadFragment = RecentReadFragment.newInstance(mBooks.get(position));
            return RecentReadFragment.newInstance(mBooks.get(position));
        }
    }

    private View.OnClickListener bookClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

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
