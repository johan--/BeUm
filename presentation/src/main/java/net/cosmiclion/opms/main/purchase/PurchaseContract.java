package net.cosmiclion.opms.main.purchase;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;
import net.cosmiclion.opms.main.library.model.BookDomain;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public interface PurchaseContract {

    interface Presenter extends BasePresenter {

        void loadBooks(boolean forceUpdate);

    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showBooksView(List<BookDomain> books);

    }
}
