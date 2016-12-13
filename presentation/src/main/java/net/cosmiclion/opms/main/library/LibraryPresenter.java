package net.cosmiclion.opms.main.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.login.LoginPresenter;
import net.cosmiclion.opms.main.library.model.BookLibraryDomain;
import net.cosmiclion.opms.main.library.model.BookShelfDomain;
import net.cosmiclion.opms.main.library.usecase.GetBookShelf;
import net.cosmiclion.opms.main.library.usecase.GetBooksLibrary;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public class LibraryPresenter implements LibraryContract.Presenter {

    private String TAG = getClass().getSimpleName();

    private final UseCaseHandler mUseCaseHandler;
    private final LibraryContract.View mItemView;
    private final GetBooksLibrary mGetBooks;
    private final GetBookShelf mGetBookShelf;

    private String mMobileToken;
    private String mImageUrl;
    private boolean mFirstLoad = true;
    private List<BookLibraryDomain> mBooks; // => du lieu combobox (danh sach cac loai bookshelf
    private List<BookShelfDomain> mBookShelf; // => du lieu listview phu thuoc vao book_id lay tu combobox

    public LibraryPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull LibraryContract.View itemView,
                            @NonNull GetBooksLibrary getBooks,
                            @NonNull GetBookShelf getBookShelf
    ) {
        this.mUseCaseHandler = useCaseHandler;
        this.mItemView = itemView;
        this.mGetBooks = getBooks;
        this.mGetBookShelf = getBookShelf;

        mItemView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCombobox(false);
    }

    @Override
    public void getMobileToken(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mMobileToken = sharedPreferences.getString(LoginPresenter.PARAMS_MOBILE_TOKEN, "");
        mImageUrl = sharedPreferences.getString(LoginPresenter.PREF_KEY_COVER_IMAGE_URL, "");
        Debug.i(TAG, "mMobileToken = " + mMobileToken);
        Debug.i(TAG, "mImageUrl = " + mImageUrl);
    }

    @Override
    public void loadCombobox(boolean forceUpdate) {
        Debug.i(TAG, "loadCombobox = " + forceUpdate);
        loadBooksLibrary(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadBookShelf(@NonNull String bookId) {
        mUseCaseHandler.execute(mGetBookShelf, new GetBookShelf.RequestValues(mMobileToken, bookId),
                new UseCase.UseCaseCallback<GetBookShelf.ResponseValue>() {
                    @Override
                    public void onSuccess(GetBookShelf.ResponseValue response) {
                        mBookShelf = response.getListBookShelfResponse();
                        String ch = "/";
                        for (BookShelfDomain item : mBookShelf) {
                            item.cover_image1 = mImageUrl + ch + item.product_id + ch + item.cover_image1;
                            item.cover_image2 = mImageUrl + ch + item.product_id + ch + item.cover_image2;
                            item.cover_image3 = mImageUrl + ch + item.product_id + ch + item.cover_image3;
                            item.cover_image4 = mImageUrl + ch + item.product_id + ch + item.cover_image4;
//                            Debug.i(TAG, "loadBookShelf image = " + item.cover_image3);
                        }

                        Debug.i(TAG, "mBookShelf size = " + mBookShelf.size());
                        mItemView.showListBookShelfView(mBookShelf);
                    }

                    @Override
                    public void onError() {
                        Debug.i(TAG, "Error");
//                mItemView.showLoginError("");
                    }
                });
    }

    @Override
    public List<BookLibraryDomain> getBooks() {
        return mBooks;
    }

    @Override
    public String getMobileToken() {
        return mMobileToken;
    }

    private void loadBooksLibrary(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mItemView.setLoadingIndicator(true);
        }
        mUseCaseHandler.execute(mGetBooks, new GetBooksLibrary.RequestValues(forceUpdate, mMobileToken),
                new UseCase.UseCaseCallback<GetBooksLibrary.ResponseValue>() {
                    @Override
                    public void onSuccess(GetBooksLibrary.ResponseValue response) {
                        mBooks = response.getBooksLibraryResponse();
                        Debug.i(TAG, "books size = " + mBooks.size());
                        if (mBooks.isEmpty()) {
                            // Show a message indicating there are no tasks for that filter type.
//                             processEmptyTasks();
                        } else {
                            // Show the list of tasks
                            mItemView.showSpinnerView(mBooks);
                        }
                    }

                    @Override
                    public void onError() {
                        Debug.i(TAG, "Error");
//                mItemView.showLoginError("");
                    }
                });
    }

}
