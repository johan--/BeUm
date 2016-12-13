package net.cosmiclion.opms.main.library;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.MainActivity;
import net.cosmiclion.opms.main.library.adapter.BookLibraryAdapter;
import net.cosmiclion.opms.main.library.dialog.BaseDialogFragment;
import net.cosmiclion.opms.main.library.dialog.SortDialogFragment;
import net.cosmiclion.opms.main.library.dialog.model.DialogParameter;
import net.cosmiclion.opms.main.library.model.BookLibraryDomain;
import net.cosmiclion.opms.main.library.model.BookShelfDomain;
import net.cosmiclion.opms.main.library.source.LibraryRepository;
import net.cosmiclion.opms.main.library.source.local.LibraryLocalDataSource;
import net.cosmiclion.opms.main.library.source.remote.LibraryRemoteDataSource;
import net.cosmiclion.opms.main.library.usecase.GetBookShelf;
import net.cosmiclion.opms.main.library.usecase.GetBooksLibrary;
import net.cosmiclion.opms.main.purchase.source.local.PurchaseLocalDataSource;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.utils.tasks.BookItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.cosmiclion.beum.R.id.spinnerCustom;
import static net.cosmiclion.opms.utils.Constants.APP_PATH;

public class LibraryFragment extends Fragment implements
        LibraryContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        BookLibraryAdapter.ViewHolder.ClickListener,
        AdapterView.OnItemSelectedListener {
    public static final String FRAGMENT = "LibraryFragment";

    private static final String BUNDLE_MOBILE_TOKEN = "BUNDLE_MOBILE_TOKEN";
    private static final String BUNDLE_BOOKS_LIBRARY = "BUNDLE_BOOKS_LIBRARY";
    private static final String BUNDLE_BOOKSHELF = "BUNDLE_BOOKSHELF";

    private String TAG = getClass().getSimpleName();
    private int mViewType = 0;
    private LibraryContract.Presenter mPresenter;
    private BookLibraryAdapter mRecyclerAdaper; // => adapter Combobox BookLibraryDomain
    private LibraryRepository mRepository;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PurchaseLocalDataSource mDataSource;

    private CustomSpinnerAdapter mSpinAdapter; // => adapter ListView BookShelfDomain
    private Spinner mSpinnerBookShelf;
    private ProgressDialog pDialog;

    private ImageButton btnActionSearch;
    private ImageButton btnActionSort;
    private ImageButton btnActionView;
    private ImageButton btnActionSelect;
    private TextView btnNewBookShelf;
    private TextView btnMoveBook;
    private TextView btnDeleteBook;
    private TextView btnCancelSelectBook;

    private RelativeLayout actionButton;
    private LinearLayout actionButtonSelection;

    public LibraryFragment() {
    }

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(this.getActivity());
        Context mContext = getActivity();

        mDataSource = PurchaseLocalDataSource.getInstance(getActivity());

        mRepository = LibraryRepository.getInstance(
                LibraryRemoteDataSource.getInstance(mContext),
                LibraryLocalDataSource.getInstance(mContext));

        mPresenter = new LibraryPresenter(UseCaseHandler.getInstance(), this,
                new GetBooksLibrary(mRepository),
                new GetBookShelf(mRepository)
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.library_frag, container, false);
        setupActionView(layout);
//        setupListView(layout);
        setupRecyleView(layout);
        initCustomSpinner(layout);
        restoreData(savedInstanceState);

        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Debug.i(TAG, "onSaveInstanceState = ");
        //Save the fragment's state here
        outState.putParcelableArrayList(BUNDLE_BOOKSHELF, new ArrayList<BookLibraryDomain>(mSpinAdapter.getBooks()));
        outState.putString(BUNDLE_MOBILE_TOKEN, mPresenter.getMobileToken());
        outState.putParcelableArrayList(BUNDLE_BOOKS_LIBRARY, new ArrayList<BookShelfDomain>(mRecyclerAdaper.getBooks()));

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
            List<BookLibraryDomain> bookLibrary = bundle.getParcelableArrayList(BUNDLE_BOOKSHELF);
            mSpinAdapter.replaceData(bookLibrary);
            List<BookShelfDomain> bookShelf = bundle.getParcelableArrayList(BUNDLE_BOOKS_LIBRARY);
            mRecyclerAdaper.replaceData(bookShelf);
        } else {
            mPresenter.getMobileToken(this.getActivity());
            mPresenter.start();
        }
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showSpinnerView(List<BookLibraryDomain> books) {
        mSpinAdapter.replaceData(books);
    }

    @Override
    public void showListBookShelfView(List<BookShelfDomain> books) {
        mRecyclerAdaper.replaceData(books);
    }

    @Override
    public void setPresenter(LibraryContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mPresenter != null) {
            mPresenter.getMobileToken(this.getActivity());
        }
        this.mPresenter.loadCombobox(false);
    }

    @Override
    public void onItemClicked(int position, BookShelfDomain book) {
        Debug.i(TAG, "onItemClicked = " + position);
        if (actionButtonSelection.getVisibility() == View.VISIBLE) {
            Debug.i(TAG, "actionButtonSelection = " + actionButtonSelection.getVisibility());
            mRecyclerAdaper.toggleSelection(position);
        } else {
//            Toast.makeText(getActivity(),
//                    book.product_id + "-filename=" + book.filename,
//                    Toast.LENGTH_LONG).show();
            File file = new File(APP_PATH, book.filename);
            if (file.exists()) {
                mDataSource.doUpdateBookReaded(book.product_id);
//                if(mBookReadedListener==null){
//                    Debug.i(TAG, "mBookReadedListener = null");
//                }
//                mBookReadedListener.onBookClicked();
                ((MainActivity) getActivity()).openDemoDocument(
                        new BookItem(0, book.product_title, 0, book.filename, 0));
            } else {
                Toast.makeText(getActivity(),
                        "You need to purchase this ebook.",
                        Toast.LENGTH_LONG).show();
            }

//            ((MainActivity) getActivity()).openDemoDocument(
//                    new BookItem(0, "HELLOOOOO", 0, "BK0000314002.epub", 0));
        }
    }

    private void setupActionView(View layout) {
        btnActionSearch = (ImageButton) layout.findViewById(R.id.btnSearchLibrary);
        btnActionSearch.setOnClickListener(libraryActionListener);
        btnActionSort = (ImageButton) layout.findViewById(R.id.btnSortLibrary);
        btnActionSort.setOnClickListener(libraryActionListener);
        btnActionView = (ImageButton) layout.findViewById(R.id.btnViewLibrary);
        btnActionView.setOnClickListener(libraryActionListener);
        btnActionSelect = (ImageButton) layout.findViewById(R.id.btnSelectLibrary);
        btnActionSelect.setOnClickListener(libraryActionListener);
        btnNewBookShelf = (TextView) layout.findViewById(R.id.btnNewBookShelf);
        btnNewBookShelf.setOnClickListener(libraryActionListener);

        actionButton = (RelativeLayout) layout.findViewById(R.id.actionButton);
        actionButtonSelection = (LinearLayout) layout.findViewById(R.id.actionButtonSelection);

        btnMoveBook = (TextView) layout.findViewById(R.id.btnMoveBook);
        btnMoveBook.setOnClickListener(libraryActionListener);
        btnDeleteBook = (TextView) layout.findViewById(R.id.btnDeleteBook);
        btnDeleteBook.setOnClickListener(libraryActionListener);
        btnCancelSelectBook = (TextView) layout.findViewById(R.id.btnCancelSelectBook);
        btnCancelSelectBook.setOnClickListener(libraryActionListener);
    }

    private void setupRecyleView(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_library);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerAdaper = new BookLibraryAdapter(this,
                new ArrayList<BookShelfDomain>(0), BookLibraryAdapter.LAYOUT_LIST_TYPE, getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mRecyclerAdaper);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initCustomSpinner(View layout) {
        mSpinnerBookShelf = (Spinner) layout.findViewById(spinnerCustom);
        mSpinAdapter = new CustomSpinnerAdapter(getActivity(), new ArrayList<BookLibraryDomain>(0));
        mSpinnerBookShelf.setAdapter(mSpinAdapter);
        mSpinnerBookShelf.setOnItemSelectedListener(this);
    }

    private void toggleSelection(int position) {
        /* action mode
        mRecyclerAdaper.toggleSelection(position);
        int count = mRecyclerAdaper.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
        */
        mRecyclerAdaper.toggleSelection(position);
    }

    /* OnItemClick Listener */

    private void showActionNewBookSelfDialog(Resources resource) {
        String title = resource.getString(R.string.dialog_text_new_title);
        String subTitle = resource.getString(R.string.dialog_text_new_subtitle);
        DialogParameter parameter = new DialogParameter(title, subTitle, null);
        BaseDialogFragment newBookDialog = BaseDialogFragment.newInstance(parameter);
        newBookDialog.show(getActivity().getFragmentManager(), "DIALOG_BOOKSELF");
    }

    private void showActionSearchDialog(Resources resource) {
        String title = resource.getString(R.string.dialog_text_search_title);
        String subTitle = resource.getString(R.string.dialog_text_search_subtitle);
        DialogParameter parameter = new DialogParameter(title, subTitle, null);
        BaseDialogFragment searchDialog = BaseDialogFragment.newInstance(parameter);
        searchDialog.show(getActivity().getFragmentManager(), "DIALOG_SEARCH");
    }

    private void showActionSortDialog(Resources resource) {
        String title = resource.getString(R.string.dialog_text_sort_title);
        String subTitle = resource.getString(R.string.dialog_text_sort_subtitle);
        DialogParameter parameter = new DialogParameter(title, subTitle);
        SortDialogFragment sortDialog = SortDialogFragment.newInstance(parameter);
        sortDialog.show(getActivity().getFragmentManager(), "DIALOG_SORT");
    }

    private boolean isFirstTime = true;

    private void doActionView() {
        Debug.i(TAG, "doActionView");
        List<BookShelfDomain> booksInit = new ArrayList<>(0);

        if (isFirstTime) {
            mViewType = BookLibraryAdapter.LAYOUT_GRID_TYPE;
            isFirstTime = false;
        }
        if (mViewType == BookLibraryAdapter.LAYOUT_LIST_TYPE) {
            Debug.i(TAG, "doActionView mViewType=" + mViewType);
            btnActionView.setImageResource(R.drawable.top_menu3);
            mRecyclerAdaper = new BookLibraryAdapter(this, booksInit, mViewType, getActivity());
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            mViewType = BookLibraryAdapter.LAYOUT_GRID_TYPE;
        } else if (mViewType == BookLibraryAdapter.LAYOUT_GRID_TYPE) {
            Debug.i(TAG, "doActionView mViewType=" + mViewType);
            btnActionView.setImageResource(R.drawable.top_menu3);
            mRecyclerAdaper = new BookLibraryAdapter(this, booksInit, mViewType, getActivity());
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mViewType = BookLibraryAdapter.LAYOUT_LIST_TYPE;
        } else {
            Debug.i(TAG, "doActionView nothign");
        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerAdaper);
        mPresenter.loadCombobox(false);
    }

    private void doActionSelect() {
        if (actionButton.getVisibility() == View.VISIBLE) {
            actionButton.setVisibility(View.INVISIBLE);
            actionButtonSelection.setVisibility(View.VISIBLE);
        }
    }

    private void doActionCancelSelection() {
        if (actionButtonSelection.getVisibility() == View.VISIBLE) {
            actionButton.setVisibility(View.VISIBLE);
            actionButtonSelection.setVisibility(View.INVISIBLE);
            Debug.i(TAG, "Selected item = " + mRecyclerAdaper.getSelectedItemCount());
            mRecyclerAdaper.clearSelection();
        }
    }

    private void doActionDeleteBook() {
        mRecyclerAdaper.removeItems(mRecyclerAdaper.getSelectedItems());
    }

    private View.OnClickListener libraryActionListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Resources resources = getResources();
            switch (view.getId()) {
                case R.id.btnNewBookShelf:
                    showActionNewBookSelfDialog(resources);
                    break;
                case R.id.btnSearchLibrary:
                    showActionSearchDialog(resources);
                    break;
                case R.id.btnSortLibrary:
                    showActionSortDialog(resources);
                    break;
                case R.id.btnViewLibrary:
                    doActionView();
                    break;
                case R.id.btnSelectLibrary:
                    doActionSelect();
                    break;
                case R.id.btnCancelSelectBook:
                    doActionCancelSelection();
                    break;
                case R.id.btnDeleteBook:
                    doActionDeleteBook();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        BookLibraryDomain item = (BookLibraryDomain) adapterView.getItemAtPosition(i);
//        Toast.makeText(adapterView.getContext(), item.bs_name, Toast.LENGTH_LONG).show();
        String bookId = item.bs_id;
        mPresenter.getMobileToken(this.getActivity());
        mPresenter.loadBookShelf(bookId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /* End OnItemClick Listener */

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private List<BookLibraryDomain> mBooks;
        private float textSize;

        public CustomSpinnerAdapter(Context context, ArrayList<BookLibraryDomain> books) {
            this.mBooks = books;
            activity = context;
            textSize = getResources().getDimension(R.dimen.action_text_size);
        }

        public List<BookLibraryDomain> getBooks() {
            return mBooks;
        }

        public int getCount() {
            return mBooks.size();
        }

        public Object getItem(int i) {
            return mBooks.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        private void setList(List<BookLibraryDomain> books) {
            mBooks = checkNotNull(books);
        }

        public void replaceData(List<BookLibraryDomain> books) {
            setList(books);
            notifyDataSetChanged();
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.custom_spinner_dropdown_item, parent, false);
            CheckedTextView dropDownText = (CheckedTextView) row.findViewById(R.id.spinLibraryDropDown);
            dropDownText.setText(mBooks.get(position).bs_name);

            return row;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.custom_spinner_item, parent, false);
            TextView itemText = (TextView) row.findViewById(R.id.spinLibraryItem);
            itemText.setText(mBooks.get(position).bs_name);
            itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);

            return row;
        }

    }

//    public interface BookReadedListener {
//
//        void onBookClicked();
//    }
//
//    public BookReadedListener mBookReadedListener;
//
//    public void setOnBookReadedListener(BookReadedListener listener) {
//        this.mBookReadedListener = listener;
//    }

}
