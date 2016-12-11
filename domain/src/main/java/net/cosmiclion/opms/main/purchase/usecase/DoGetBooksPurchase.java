package net.cosmiclion.opms.main.purchase.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseDomain;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseMapper;
import net.cosmiclion.opms.main.purchase.source.PurchaseDataSource;
import net.cosmiclion.opms.main.purchase.source.PurchaseRepository;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by longpham on 9/14/2016.
 */
public class DoGetBooksPurchase extends
        UseCase<DoGetBooksPurchase.RequestValues, DoGetBooksPurchase.ResponseValue> {
    private final String TAG = getClass().getSimpleName();
    private final PurchaseRepository mRepository;

    public DoGetBooksPurchase(@NonNull PurchaseRepository repository) {
        mRepository = checkNotNull(repository, "repository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        String token = requestValues.getToken();
        mRepository.getBooksPurchaseResponse(token, new PurchaseDataSource.LoadPurchaseCallback() {

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
        /*
            Map object tu data sang domain, tranh viec sua du lieu tren server anh huong
            den cac tang domain va present.
         */
        public ResponseValue(ResponseData books) {
            Debug.i("ResponseValue", "ResponseData books=" + books.getResponse());
            List<BookPurchaseDomain> booksPurchaseDomain = BookPurchaseMapper.transformList((String) books.getResponse());
            mBooks = checkNotNull(booksPurchaseDomain, "mBookInfo cannot null");
        }

        public List<BookPurchaseDomain>  getBooksPurchaseResponse() {
            return mBooks;
        }
        public BookPurchaseDomain  getBookPurchaseResponse() {
            return mBook;
        }
    }
}
