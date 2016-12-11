package net.cosmiclion.opms.main.library.usecase;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.UseCase;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.library.model.BookLibraryDomain;
import net.cosmiclion.opms.main.library.model.BookLibraryMapper;
import net.cosmiclion.opms.main.library.source.LibraryDataSource;
import net.cosmiclion.opms.main.library.source.LibraryRepository;
import net.cosmiclion.opms.utils.Debug;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetBooksLibrary extends UseCase<GetBooksLibrary.RequestValues, GetBooksLibrary.ResponseValue> {
    private String TAG = this.getClass().getSimpleName();
    private final LibraryRepository mRepository;

    public GetBooksLibrary(@NonNull LibraryRepository repository) {
        this.mRepository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        String token = requestValues.getToken();
        mRepository.getBooksLibraryResponse(token, new LibraryDataSource.LoadLibraryCallback() {
            @Override
            public void onBooksLibraryLoaded(ResponseData response) {
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
        private List<BookLibraryDomain> mBooks = null;
        private BookLibraryDomain mBook = null;

        /*
            Map object tu data sang domain, tranh viec sua du lieu tren server anh huong
            den cac tang domain va present.
         */
        public ResponseValue(ResponseData books) {
            Debug.i("ResponseValue", "ResponseData books=" + books.getResponse());
            List<BookLibraryDomain> booksPurchaseDomain = BookLibraryMapper
                    .transformList((String) books.getResponse());
            mBooks = checkNotNull(booksPurchaseDomain, "mBookInfo cannot null");
        }

        public List<BookLibraryDomain> getBooksLibraryResponse() {
            return mBooks;
        }

        public BookLibraryDomain getBookLibraryResponse() {
            return mBook;
        }
    }
}
