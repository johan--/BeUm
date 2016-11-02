package net.cosmiclion.opms.main.library;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.library.model.BookDomain;
import net.cosmiclion.opms.main.library.usecase.GetBooks;
import net.cosmiclion.opms.main.mylibrary.model.BooksRequest;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public class LibraryPresenter implements LibraryContract.Presenter {

    private String TAG = getClass().getSimpleName();

    private final UseCaseHandler mUseCaseHandler;

    private final LibraryContract.View mItemView;

    private final GetBooks mGetBooks;

    private boolean mFirstLoad = true;

    public LibraryPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull LibraryContract.View itemView,
                            @NonNull GetBooks getBooks
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

    private void loadQuickMenuItems(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mItemView.setLoadingIndicator(true);
        }
        GetBooks.RequestValues requestValue = new GetBooks.RequestValues(forceUpdate, new BooksRequest());
        mUseCaseHandler.execute(mGetBooks, requestValue,
                new UseCase.UseCaseCallback<GetBooks.ResponseValues>() {
                    @Override
                    public void onSuccess(GetBooks.ResponseValues response) {
                        Debug.i(TAG, "loadQuickMenuItems onSuccess");
                        List<BookDomain> books = response.getResponse();
                        if (!mItemView.isActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mItemView.setLoadingIndicator(false);
                        }
                        processBooks(books);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private void processBooks(List<BookDomain> books) {
        if (books.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
//            processEmptyTasks();
        } else {
            // Show the list of tasks
            mItemView.showBooksView(books);
        }
    }

    @Override
    public void loadBooks(boolean forceUpdate) {
        Debug.i(TAG, "loadBooks = " + forceUpdate);
        loadQuickMenuItems(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }
}
