package net.cosmiclion.opms.main.quickmenu;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItemsRequest;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.UseCaseHandler;
import net.cosmiclion.opms.main.quickmenu.model.QuickMenuItem;
import net.cosmiclion.opms.main.quickmenu.usecase.GetQuickMenuItemDetail;
import net.cosmiclion.opms.main.quickmenu.usecase.GetQuickMenuItems;

import java.util.List;

/**
 * Created by longpham on 9/18/2016.
 */
public class QuickMenuPresenter implements QuickMenuContract.Presenter {

    private String TAG = getClass().getSimpleName();

    private final UseCaseHandler mUseCaseHandler;

    private final QuickMenuContract.View mQuickMenuItemView;

    private final GetQuickMenuItems mGetQuickMenuItems;

    private final GetQuickMenuItemDetail mGetQuickMenuItemDetail;

    private boolean mFirstLoad = true;

    public QuickMenuPresenter(@NonNull UseCaseHandler useCaseHandler,
                              @NonNull QuickMenuContract.View quickMenuItemView,
                              @NonNull GetQuickMenuItems getQuickMenuItems,
                              @NonNull GetQuickMenuItemDetail getQuickMenuItemDetail
    ) {
        this.mUseCaseHandler = useCaseHandler;
        this.mQuickMenuItemView = quickMenuItemView;
        this.mGetQuickMenuItems = getQuickMenuItems;
        this.mGetQuickMenuItemDetail = getQuickMenuItemDetail;

        mQuickMenuItemView.setPresenter(this);
    }

    @Override
    public void start() {
        loadQuickMenuItems(false);
    }

//    @Override
//    public void loadBookDetail(@NonNull String bookId, @NonNull String bookTitle) {
//        Debug.i(TAG, "------loadBookDetail--------");
//        mBooksView.showProgressDialog("", "Loading book...");
//        final BookDetailRequest bookDetailRequest = new BookDetailRequest(UID, bookId, bookTitle);
//        GetBookDetail.RequestValues requestValues = new GetBookDetail.RequestValues(bookDetailRequest);
//        mUseCaseHandler.execute(mBookDetail, requestValues, new UseCase.UseCaseCallback<GetBookDetail.ResponseValues>() {
//            @Override
//            public void onSuccess(GetBookDetail.ResponseValues response) {
//                mBooksView.hideProgressDialog();
//                if(bookDetailRequest != null){
//                    BookDetailResponse bookDetailResponse = response.getBookDetailResponse();
//                    mBooksView.showBookDetailView(bookDetailResponse);
//                }else{
//                    mBooksView.showBookDetailFailure("");
//                }
//            }
//
//            @Override
//            public void onError() {
//                mBooksView.hideProgressDialog();
//                mBooksView.showBookDetailFailure("");
//                Debug.i(TAG, "onError");
//            }
//        });
//    }


    private void loadQuickMenuItems(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mQuickMenuItemView.setLoadingIndicator(true);
        }
        GetQuickMenuItems.RequestValues requestValue = new GetQuickMenuItems.RequestValues(forceUpdate, new QuickMenuItemsRequest());
        mUseCaseHandler.execute(mGetQuickMenuItems, requestValue,
                new UseCase.UseCaseCallback<GetQuickMenuItems.ResponseValues>() {
                    @Override
                    public void onSuccess(GetQuickMenuItems.ResponseValues response) {
                        Debug.i(TAG, "loadQuickMenuItems onSuccess");
                        List<QuickMenuItem> books = response.getResponse();
                        if (!mQuickMenuItemView.isActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mQuickMenuItemView.setLoadingIndicator(false);
                        }
                        processBooks(books);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private void processBooks(List<QuickMenuItem> books) {
        if (books.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
//            processEmptyTasks();
        } else {
            // Show the list of tasks
            mQuickMenuItemView.showBooksView(books);
        }
    }

    @Override
    public void loadQuickMenuItems(boolean forceUpdate) {
        Debug.i(TAG, "loadQuickMenuItems = " + forceUpdate);
        loadQuickMenuItems(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadQuickMenuItemDetail(@NonNull String bookId, @NonNull String bookTitle) {

    }

    @Override
    public void goPurchaseListScreen() {

    }
}
