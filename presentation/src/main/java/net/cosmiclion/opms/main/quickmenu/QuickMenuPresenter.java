package net.cosmiclion.opms.main.quickmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.login.LoginPresenter;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseDomain;
import net.cosmiclion.opms.main.quickmenu.usecase.GetQuickMenuItems;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public class QuickMenuPresenter implements QuickMenuContract.Presenter {

    private String TAG = getClass().getSimpleName();

    private final UseCaseHandler mUseCaseHandler;
    private final QuickMenuContract.View mItemView;
    private final GetQuickMenuItems mGetPurchaseRecent;

    private boolean mFirstLoad = true;
    private String mMobileToken;
    private String mImageUrl;
    private List<BookPurchaseDomain> mBooks;

    public QuickMenuPresenter(@NonNull UseCaseHandler useCaseHandler,
                              @NonNull QuickMenuContract.View quickMenuItemView,
                              @NonNull GetQuickMenuItems getQuickMenuItems
    ) {
        this.mUseCaseHandler = useCaseHandler;
        this.mItemView = quickMenuItemView;
        this.mGetPurchaseRecent = getQuickMenuItems;

        mItemView.setPresenter(this);
    }

    @Override
    public void start() {
        loadBooks(false);
    }

    @Override
    public void loadBooks(boolean forceUpdate) {
        Debug.i(TAG, "loadBooks Purchase= " + forceUpdate);
        getBooks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void getBooks(boolean forceUpdate, final boolean showLoadingUI) {
        Debug.i(TAG, "===getBooks Purchase=== " + forceUpdate);
        if (showLoadingUI) {
            mItemView.setLoadingIndicator(true);
        }
        mUseCaseHandler.execute(mGetPurchaseRecent, new GetQuickMenuItems.RequestValues(forceUpdate, mMobileToken),
                new UseCase.UseCaseCallback<GetQuickMenuItems.ResponseValue>() {
                    @Override
                    public void onSuccess(GetQuickMenuItems.ResponseValue response) {
                        List<BookPurchaseDomain> books = response.getBooksPurchaseResponse();
                        String ch = "/";
                        for (BookPurchaseDomain item : books) {
                            item.cover_image1 = mImageUrl + ch + item.product_id + ch + item.cover_image1;
                            item.cover_image2 = mImageUrl + ch + item.product_id + ch + item.cover_image2;
                            item.cover_image3 = mImageUrl + ch + item.product_id + ch + item.cover_image3;
                            item.cover_image4 = mImageUrl + ch + item.product_id + ch + item.cover_image4;
                        }
                        Debug.i(TAG, "books size = " + books.size());
                        mItemView.showBooksView(books);
                    }

                    @Override
                    public void onError() {
                        Debug.i(TAG, "Error");
                    }
                });
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
    public List<BookPurchaseDomain> getBooks() {
        return mBooks;
    }


    @Override
    public String getMobileToken() {
        return mMobileToken;
    }

    @Override
    public void goPurchaseListScreen() {

    }
}
