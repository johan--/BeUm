package net.cosmiclion.opms.main.quickmenu;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.BasePresenter;
import net.cosmiclion.opms.BaseView;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItem;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public interface QuickMenuContract {

    interface Presenter extends BasePresenter {

        void loadQuickMenuItems(boolean forceUpdate);

        void loadQuickMenuItemDetail(@NonNull String bookId, @NonNull String bookTitle);

        void goPurchaseListScreen();
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showBooksView(List<QuickMenuItem> books);

//        void showLogoutSuccess(@NonNull LogoutResponse logoutResponse);
//
//        void showLogoutFailure(@NonNull LogoutResponse logoutResponse);
//
//        void showLogoutError(String message);
//
//        void openBookDetail(@NonNull String uid, @NonNull String bookId);
//
//        void showAlertMessage(String message);
//
//        void showProgressDialog(String title, String message);
//
//        void hideProgressDialog();
//
//        void showBookDetailView(@NonNull BookDetailResponse bookDetailResponse);

//        void showBookDetailFailure(String message);
    }
}
