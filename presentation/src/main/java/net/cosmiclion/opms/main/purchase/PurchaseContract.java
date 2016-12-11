package net.cosmiclion.opms.main.purchase;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseDomain;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public interface PurchaseContract {

    interface Presenter extends BasePresenter {

        void getMobileToken(@NonNull Context context);

        String getMobileToken();

        void loadBooks(boolean forceUpdate);

        List<BookPurchaseDomain> getBooks();
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showBooksView(List<BookPurchaseDomain> books);
    }
}
