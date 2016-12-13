package net.cosmiclion.opms.main.purchase;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import net.cosmiclion.beum.R;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.purchase.adapter.BooksPurchaseAdapter;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseDomain;
import net.cosmiclion.opms.main.purchase.source.PurchaseRepository;
import net.cosmiclion.opms.main.purchase.source.local.PurchaseLocalDataSource;
import net.cosmiclion.opms.main.purchase.source.remote.PurchaseRemoteDataSource;
import net.cosmiclion.opms.main.purchase.usecase.DoGetBooksPurchase;
import net.cosmiclion.opms.utils.Debug;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.cosmiclion.opms.login.LoginPresenter.PARAMS_MOBILE_TOKEN;
import static net.cosmiclion.opms.utils.Constants.APP_PATH;

/**
 * Created by longpham on 11/9/2016.
 */
public class PurchaseFragment extends Fragment implements
        PurchaseContract.View,
        BooksPurchaseAdapter.ViewHolder.ClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        BooksPurchaseAdapter.DownloadClickListener {

    public static final String FRAGMENT = "PurchaseFragment";
    public static final String BUNDLE_MOBILE_TOKEN = "BUNDLE_MOBILE_TOKEN";
    public static final String BUNDLE_BOOKS_PURCHASE = "BUNDLE_BOOKS_PURCHASE";
    private String TAG = getClass().getSimpleName();

    private ImageButton btnActionSearch;
    private BooksPurchaseAdapter mRecyclerAdaper;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PurchaseContract.Presenter mPresenter;
    private PurchaseRepository mRepository;
    private ProgressDialog pDialog;

    public PurchaseFragment() {
    }

    public static PurchaseFragment newInstance() {
        PurchaseFragment fragment = new PurchaseFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext = getActivity();
        pDialog = new ProgressDialog(this.getActivity());

        mRepository = PurchaseRepository.getInstance(
                PurchaseRemoteDataSource.getInstance(mContext),
                PurchaseLocalDataSource.getInstance(mContext));

        mPresenter = new PurchasePresenter(UseCaseHandler.getInstance(), this,
                new DoGetBooksPurchase(mRepository)
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.purchase_frag, container, false);

        setupActionView(layout);
        setupRecyleView(layout);
        restoreData(savedInstanceState);

        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Debug.i(TAG, "onSaveInstanceState = ");
        //Save the fragment's state here
        outState.putString(BUNDLE_MOBILE_TOKEN, mPresenter.getMobileToken());
        outState.putParcelableArrayList(BUNDLE_BOOKS_PURCHASE, new ArrayList<>(mRecyclerAdaper.getBooks()));
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
            String token = bundle.getString(PARAMS_MOBILE_TOKEN);
            Debug.i(TAG, "restoreData token=" + token);
            List<BookPurchaseDomain> books = bundle.getParcelableArrayList(BUNDLE_BOOKS_PURCHASE);
            mRecyclerAdaper.replaceData(books);
        } else {
            mPresenter.getMobileToken(this.getActivity());
            mPresenter.start();
        }
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showBooksView(List<BookPurchaseDomain> books) {
//        mRepository.doSaveBooks(books);
        mRecyclerAdaper.replaceData(books);
        if (mBookReadedListener == null) {
            Debug.i(TAG, "mBookReadedListener is null");
        } else {
            mBookReadedListener.onBookClicked(books);
        }

    }

    @Override
    public void setPresenter(PurchaseContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onItemClicked(int position) {
//        ((MainActivity) getActivity()).openDemoDocument(
//                new BookItem(0, "BK0000314002", 0, "BK0000314002.epub", 0));
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

    /* FUNCTION */
    private void setupActionView(View layout) {
        btnActionSearch = (ImageButton) layout.findViewById(R.id.btnPurchaseSearch);
        btnActionSearch.setOnClickListener(actionListener);
    }

    private void setupRecyleView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_purchase);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerAdaper = new BooksPurchaseAdapter(
                this, new ArrayList<BookPurchaseDomain>(0), getActivity());
        mRecyclerAdaper.setOnDownloadClickListener(this);

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

    @Override
    public void onDownloadClicked(String state, BookPurchaseDomain book) {
        if (state.contains("Success")) {
            String urlCoverBook = book.cover_image2;
            Debug.i("DownloadImage", "url=" + urlCoverBook);
            Picasso.with(getActivity())
                    .load(urlCoverBook)
                    .error(R.drawable.book_cover)
                    .into(getTarget(book.cover_image2, getActivity()));

            Toast.makeText(getActivity(), state, Toast.LENGTH_LONG).show();
            mRecyclerAdaper.notifyDataSetChanged();
            mPresenter.markBookDownloaded(book.product_id);
            //download image

        }
    }

    /* INNER CLASS */

    private static Target getTarget(final String url, final Context context) {
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String imageName = url.substring(url.lastIndexOf("/"), url.length());
                        String exName = url.substring(url.lastIndexOf("."), url.length()).toLowerCase();
                        Debug.i("DownloadImage", "imageName=" + imageName);

                        File folderParent = new File(APP_PATH);
                        if (!folderParent.exists()) {
                            folderParent.mkdirs();
                        }
                        File file = new File(folderParent, imageName);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            if(exName.equals(".jpg")){
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            }else if(exName.equals(".png")){
                                bitmap.compress(Bitmap.CompressFormat.PNG, 80, ostream);
                            }
                            ostream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
        return target;
    }

    public interface BookReadedListener {

        void onBookClicked(List<BookPurchaseDomain> books);
    }

    public BookReadedListener mBookReadedListener;

    public void setOnBookReadedListener(BookReadedListener listener) {
        this.mBookReadedListener = listener;
    }
}
