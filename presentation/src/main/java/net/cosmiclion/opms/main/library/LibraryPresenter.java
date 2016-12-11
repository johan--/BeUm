package net.cosmiclion.opms.main.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.login.LoginPresenter;
import net.cosmiclion.opms.main.library.model.BookLibraryDomain;
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
    private String mMobileToken;
    private boolean mFirstLoad = true;
    private List<BookLibraryDomain> mBooks;

    public LibraryPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull LibraryContract.View itemView,
                            @NonNull GetBooksLibrary getBooks
    ) {
        this.mUseCaseHandler = useCaseHandler;
        this.mItemView = itemView;
        this.mGetBooks = getBooks;

        mItemView.setPresenter(this);
    }

    @Override
    public void start() {
        loadBooks(false);
    }

    @Override
    public void getMobileToken(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mMobileToken = sharedPreferences.getString(LoginPresenter.PARAMS_MOBILE_TOKEN, "");
        Debug.i(TAG, "mMobileToken = " + mMobileToken);
    }

    @Override
    public void loadBooks(boolean forceUpdate) {
        Debug.i(TAG, "loadBooks = " + forceUpdate);
        loadBooksLibrary(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
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
                            mItemView.showBooksView(mBooks);
                        }
                    }

                    @Override
                    public void onError() {
                        Debug.i(TAG, "Error");
//                mItemView.showLoginError("");
                    }
                });
    }

    private void processBooks(List<BookLibraryDomain> books) {
        if (books.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
//            processEmptyTasks();
        } else {
            // Show the list of tasks
            mItemView.showBooksView(books);
        }
    }

    @Override
    public List<BookLibraryDomain> getBooks(){
        return mBooks;
    }

    @Override
    public String getMobileToken() {
        return mMobileToken;
    }
}
