package net.cosmiclion.opms.main.library.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.library.model.BookShelfDomain;
import net.cosmiclion.opms.main.library.model.BookShelfMapper;
import net.cosmiclion.opms.main.library.source.LibraryDataSource;
import net.cosmiclion.opms.main.library.source.LibraryRepository;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetBookShelf extends UseCase<GetBookShelf.RequestValues, GetBookShelf.ResponseValue> {
    private String TAG = this.getClass().getSimpleName();
    private final LibraryRepository mRepository;

    public GetBookShelf(@NonNull LibraryRepository repository) {
        this.mRepository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        String token = requestValues.getToken();
        String bookId = requestValues.getBookId();
        mRepository.getBookShelfResponse(token, bookId, new LibraryDataSource.LoadBookShelfCallback() {
            @Override
            public void onBookShelfLoaded(ResponseData response) {
                getUseCaseCallback().onSuccess(new ResponseValue(response));
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String mToken;
        private final String mBookId;
//        private final boolean mForceUpdate;

        public RequestValues(@NonNull String token, @NonNull String bookId) {
//            this.mForceUpdate = forceUpdate;
            this.mToken = checkNotNull(token, "token cannot null");
            this.mBookId = checkNotNull(bookId, "bookId cannot null");
        }

        public String getToken() {
            return mToken;
        }

        public String getBookId() {
            return mBookId;
        }

//        public boolean isForceUpdate() {
//            return mForceUpdate;
//        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private List<BookShelfDomain> mBooks = null;

        /*
            Map object tu data sang domain, tranh viec sua du lieu tren server anh huong
            den cac tang domain va present.
         */
        public ResponseValue(ResponseData books) {
            Debug.i("ResponseValue", "ResponseData books=" + books.getResponse());
            List<BookShelfDomain> domain = BookShelfMapper
                    .transformList((String) books.getResponse());
            mBooks = checkNotNull(domain, "mBookInfo cannot null");
        }

        public List<BookShelfDomain> getListBookShelfResponse() {
            return mBooks;
        }

    }
}
