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

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.MainActivity;
import net.cosmiclion.opms.main.library.adapter.BookLibraryAdapter;
import net.cosmiclion.opms.main.library.dialog.BaseDialogFragment;
import net.cosmiclion.opms.main.library.dialog.SortDialogFragment;
import net.cosmiclion.opms.main.library.dialog.model.DialogParameter;
import net.cosmiclion.opms.main.library.model.BookLibraryDomain;
import net.cosmiclion.opms.main.library.source.LibraryRepository;
import net.cosmiclion.opms.main.library.source.local.LibraryLocalDataSource;
import net.cosmiclion.opms.main.library.source.remote.LibraryRemoteDataSource;
import net.cosmiclion.opms.main.library.usecase.GetBooksLibrary;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.utils.tasks.BookItem;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment implements LibraryContract.View, SwipeRefreshLayout.OnRefreshListener, BookLibraryAdapter.ViewHolder.ClickListener {
    public static final String FRAGMENT = "LibraryFragment";

    private static final String BUNDLE_MOBILE_TOKEN = "BUNDLE_MOBILE_TOKEN";
    private static final String BUNDLE_BOOKS_LIBRARY = "BUNDLE_BOOKS_LIBRARY";

    private String TAG = getClass().getSimpleName();
    private int mViewType = 0;
    private LibraryContract.Presenter mPresenter;
    private BookLibraryAdapter mRecyclerAdaper;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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
        mPresenter = new LibraryPresenter(UseCaseHandler.getInstance(), this,
                new GetBooksLibrary(LibraryRepository.getInstance(
                        LibraryRemoteDataSource.getInstance(mContext),
                        LibraryLocalDataSource.getInstance(mContext)))
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
        outState.putString(BUNDLE_MOBILE_TOKEN, mPresenter.getMobileToken());
        outState.putParcelableArrayList(BUNDLE_BOOKS_LIBRARY, new ArrayList<>(mRecyclerAdaper.getBooks()));
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
            List<BookLibraryDomain> books = bundle.getParcelableArrayList(BUNDLE_BOOKS_LIBRARY);
            mRecyclerAdaper.replaceData(books);
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
    public void showBooksView(List<BookLibraryDomain> books) {
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
        this.mPresenter.loadBooks(false);
    }

    @Override
    public void onItemClicked(int position) {
        Debug.i(TAG, "onItemClicked = " + position);
        if (actionButtonSelection.getVisibility() == View.VISIBLE) {
            Debug.i(TAG, "actionButtonSelection = " + actionButtonSelection.getVisibility());
            mRecyclerAdaper.toggleSelection(position);
        } else {
//            Toast.makeText(getContext(), "Hello select", Toast.LENGTH_LONG).show();
            ((MainActivity) getActivity()).openDemoDocument(
                    new BookItem(0, "BK0000314002", 0, "BK0000314002.epub", 0));
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
//        List<BookLibraryDomain> books = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            books.add(new BookLibraryDomain("Book ", "Book name " + i, null));
//        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_library);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerAdaper = new BookLibraryAdapter(this,
                new ArrayList<BookLibraryDomain>(0), BookLibraryAdapter.LAYOUT_LIST_TYPE, getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mRecyclerAdaper);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initCustomSpinner(View layout) {
        Spinner spinnerCustom = (Spinner) layout.findViewById(R.id.spinnerCustom);
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("전체보기");
        languages.add("책장1");
        languages.add("책장2");
        languages.add("책장3");
        languages.add("책장4");

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), languages);
        spinnerCustom.setAdapter(customSpinnerAdapter);
        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        List<BookLibraryDomain> booksInit = new ArrayList<BookLibraryDomain>(0);
//        for (int i = 0; i < 10; i++) {
//            books.add(new BookLibraryDomain("Book ", "Book name " + i, null));
//        }

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
        mPresenter.loadBooks(false);
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

    /* End OnItemClick Listener */

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;
        private float textSize;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
            textSize = getResources().getDimension(R.dimen.action_text_size);
        }

        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.custom_spinner_dropdown_item, parent, false);
            CheckedTextView dropDownText = (CheckedTextView) row.findViewById(R.id.spinLibraryDropDown);
            dropDownText.setText(asr.get(position));

            return row;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.custom_spinner_item, parent, false);
            TextView itemText = (TextView) row.findViewById(R.id.spinLibraryItem);
            itemText.setText(asr.get(position));
            itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);

            return row;
        }

    }

    public interface BookListener {

        void onBookClicked(BookLibraryDomain item);

        void onBookTitleClicked(BookLibraryDomain item);
    }

    public BookListener mItemListener = new BookListener() {

        @Override
        public void onBookClicked(BookLibraryDomain item) {
//            Debug.i(TAG, "onBookClicked " + item.getTitle());
        }

        @Override
        public void onBookTitleClicked(BookLibraryDomain item) {
//            Debug.i(TAG, "onBookTitleClicked " + item.getTitle());
        }
    };
}
