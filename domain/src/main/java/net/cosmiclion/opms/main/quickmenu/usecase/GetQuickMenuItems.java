package net.cosmiclion.opms.main.quickmenu.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseDomain;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseMapper;
import net.cosmiclion.opms.main.quickmenu.QuickMenuDataSource;
import net.cosmiclion.opms.main.quickmenu.QuickMenuRepository;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetQuickMenuItems extends
        UseCase<GetQuickMenuItems.RequestValues, GetQuickMenuItems.ResponseValue> {
    private String TAG = this.getClass().getSimpleName();
    private final QuickMenuRepository mRepository;

    public GetQuickMenuItems(@NonNull QuickMenuRepository repository) {
        this.mRepository = checkNotNull(repository, "repository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        String token = requestValues.getToken();
        mRepository.getBooksPurchaseResponse(token, new QuickMenuDataSource.LoadPurchaseCallback() {

            @Override
            public void onBooksPurchaseLoaded(ResponseData response) {
                getUseCaseCallback().onSuccess(new ResponseValue(response));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                Debug.i(TAG, "onDataNotAvailable=" + errorMessage);
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mToken;
        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate, @NonNull String token) {
            this.mForceUpdate = forceUpdate;
            mToken = checkNotNull(token, "token cannot null");
        }

        public String getToken() {
            return mToken;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private List<BookPurchaseDomain> mBooks = null;
        private BookPurchaseDomain mBook = null;
        private String jsonBooks;

        /*
            Map object tu data sang domain, tranh viec sua du lieu tren server anh huong
            den cac tang domain va present.
         */
        public ResponseValue(ResponseData books) {
            Debug.i("ResponseValue Purchase", "ResponseData books=" + books.getResponse());
            jsonBooks = books.getResponse().toString();
            Debug.i("ResponseValue Purchase", "jsonBooks=" + jsonBooks);

            List<BookPurchaseDomain> booksPurchaseDomain = BookPurchaseMapper
                    .transformList((String) books.getResponse());
            mBooks = checkNotNull(booksPurchaseDomain, "mBookInfo cannot null");

        }

        public String getJsonBooks() {
            return jsonBooks;
        }

        public List<BookPurchaseDomain> getBooksPurchaseResponse() {
            return mBooks;
        }

        public BookPurchaseDomain getBookPurchaseResponse() {
            return mBook;
        }
    }
}
