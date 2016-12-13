package net.cosmiclion.opms.main.library;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;
import net.cosmiclion.opms.main.library.model.BookLibraryDomain;
import net.cosmiclion.opms.main.library.model.BookShelfDomain;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public interface LibraryContract {

    interface Presenter extends BasePresenter {

        void getMobileToken(@NonNull Context context);

        String getMobileToken();

        void loadCombobox(@NonNull boolean forceUpdate);

        List<BookLibraryDomain> getBooks();

        void loadBookShelf(@NonNull String bookId);
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showSpinnerView(List<BookLibraryDomain> books);

        void showListBookShelfView(List<BookShelfDomain> books);
    }
}
