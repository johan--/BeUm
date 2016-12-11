package net.cosmiclion.opms.main.library;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;
import net.cosmiclion.opms.main.library.model.BookLibraryDomain;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public interface LibraryContract {

    interface Presenter extends BasePresenter {

        void getMobileToken(@NonNull Context context);

        String getMobileToken();

        void loadBooks(@NonNull boolean forceUpdate);

        List<BookLibraryDomain> getBooks();
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showBooksView(List<BookLibraryDomain> books);
    }
}
